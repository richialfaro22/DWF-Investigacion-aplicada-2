package com.tecnowlapi.tecnowlapi.service;

import com.tecnowlapi.tecnowlapi.event.SolicitudAprobadaEvent;
import com.tecnowlapi.tecnowlapi.event.SolicitudCreadaEvent;
import com.tecnowlapi.tecnowlapi.exception.RecursoNoEncontradoException;
import com.tecnowlapi.tecnowlapi.model.Solicitud;
import com.tecnowlapi.tecnowlapi.repository.SolicitudRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import com.tecnowlapi.tecnowlapi.model.EstadoSolicitud;
import com.tecnowlapi.tecnowlapi.model.Equipo;
import com.tecnowlapi.tecnowlapi.model.EstadoEquipo;
import com.tecnowlapi.tecnowlapi.event.SolicitudRechazadaEvent;
import com.tecnowlapi.tecnowlapi.event.EquipoEntregadoEvent;
import com.tecnowlapi.tecnowlapi.event.EquipoDevueltoEvent;

import java.util.List;

@Service
public class SolicitudService {

    private final SolicitudRepository solicitudRepository;
    private final ApplicationEventPublisher eventPublisher;

    public SolicitudService(
            SolicitudRepository solicitudRepository,
            ApplicationEventPublisher eventPublisher) {

        this.solicitudRepository = solicitudRepository;
        this.eventPublisher = eventPublisher;
    }

    public List<Solicitud> obtenerTodas() {
        return solicitudRepository.findAll();
    }

    public Solicitud obtenerPorId(Long id) {
        return solicitudRepository.findById(id)
                .orElseThrow(() ->
                        new RecursoNoEncontradoException(
                                "Solicitud no encontrada con ID: " + id
                        )
                );
    }

    public Solicitud guardar(Solicitud solicitud) {

        Solicitud solicitudGuardada =
                solicitudRepository.save(solicitud);

        SolicitudCreadaEvent evento =
                new SolicitudCreadaEvent(
                        solicitudGuardada.getId(),
                        solicitudGuardada.getUsuario().getId(),
                        solicitudGuardada.getEquipo().getId()
                );

        eventPublisher.publishEvent(evento);

        return solicitudGuardada;
    }

    public void eliminar(Long id) {
        Solicitud solicitud = obtenerPorId(id);
        solicitudRepository.delete(solicitud);
    }

    public Solicitud aprobar(Long id) {

        Solicitud solicitud = obtenerPorId(id);

        if (solicitud.getEstado() != EstadoSolicitud.PENDIENTE) {
            throw new IllegalStateException(
                    "Solo se pueden aprobar solicitudes pendientes."
            );
        }

        Equipo equipo = solicitud.getEquipo();

        if (equipo.getEstado() != EstadoEquipo.DISPONIBLE) {
            throw new IllegalStateException(
                    "El equipo no está disponible para ser reservado."
            );
        }

        solicitud.setEstado(EstadoSolicitud.APROBADA);

        Solicitud solicitudActualizada =
                solicitudRepository.save(solicitud);

        SolicitudAprobadaEvent evento =
                new SolicitudAprobadaEvent(
                        solicitudActualizada.getId(),
                        equipo.getId()
                );

        eventPublisher.publishEvent(evento);

        return solicitudActualizada;
    }

    public Solicitud rechazar(Long id) {

        Solicitud solicitud = obtenerPorId(id);

        if (solicitud.getEstado() != EstadoSolicitud.PENDIENTE) {
            throw new IllegalStateException(
                    "Solo se pueden rechazar solicitudes pendientes."
            );
        }

        solicitud.setEstado(EstadoSolicitud.RECHAZADA);

        Solicitud solicitudActualizada =
                solicitudRepository.save(solicitud);

        SolicitudRechazadaEvent evento =
                new SolicitudRechazadaEvent(
                        solicitudActualizada.getId()
                );

        eventPublisher.publishEvent(evento);

        return solicitudActualizada;
    }

    public Solicitud entregar(Long id) {

        Solicitud solicitud = obtenerPorId(id);

        if (solicitud.getEstado() != EstadoSolicitud.APROBADA) {
            throw new IllegalStateException(
                    "Solo se pueden entregar solicitudes aprobadas."
            );
        }

        Equipo equipo = solicitud.getEquipo();

        if (equipo.getEstado() != EstadoEquipo.RESERVADO) {
            throw new IllegalStateException(
                    "El equipo no está reservado para esta solicitud."
            );
        }

        Solicitud solicitudActualizada = solicitudRepository.save(solicitud);

        EquipoEntregadoEvent evento =
                new EquipoEntregadoEvent(
                        solicitudActualizada.getId(),
                        equipo.getId()
                );

        eventPublisher.publishEvent(evento);

        return solicitudActualizada;
    }

    public Solicitud devolver(Long id) {

        Solicitud solicitud = obtenerPorId(id);

        if (solicitud.getEstado() != EstadoSolicitud.APROBADA) {
            throw new IllegalStateException(
                    "Solo se pueden devolver solicitudes que hayan sido aprobadas."
            );
        }

        Equipo equipo = solicitud.getEquipo();

        if (equipo.getEstado() != EstadoEquipo.PRESTADO) {
            throw new IllegalStateException(
                    "El equipo no está actualmente prestado."
            );
        }

        solicitud.setEstado(EstadoSolicitud.COMPLETADA);

        Solicitud solicitudActualizada =
                solicitudRepository.save(solicitud);

        EquipoDevueltoEvent evento =
                new EquipoDevueltoEvent(
                        solicitudActualizada.getId(),
                        equipo.getId()
                );

        eventPublisher.publishEvent(evento);

        return solicitudActualizada;
    }
}