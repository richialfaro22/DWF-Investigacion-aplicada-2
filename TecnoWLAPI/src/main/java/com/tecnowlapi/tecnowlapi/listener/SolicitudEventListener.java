package com.tecnowlapi.tecnowlapi.listener;

import com.tecnowlapi.tecnowlapi.event.SolicitudAprobadaEvent;
import com.tecnowlapi.tecnowlapi.event.SolicitudCreadaEvent;
import com.tecnowlapi.tecnowlapi.model.Equipo;
import com.tecnowlapi.tecnowlapi.model.EstadoEquipo;
import com.tecnowlapi.tecnowlapi.repository.EquipoRepository;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.scheduling.annotation.Async;
import com.tecnowlapi.tecnowlapi.model.EventLog;
import com.tecnowlapi.tecnowlapi.repository.EventLogRepository;
import com.tecnowlapi.tecnowlapi.event.SolicitudRechazadaEvent;
import com.tecnowlapi.tecnowlapi.event.EquipoEntregadoEvent;
import com.tecnowlapi.tecnowlapi.event.EquipoDevueltoEvent;

@Component
public class SolicitudEventListener {

    private final EquipoRepository equipoRepository;
    private final EventLogRepository eventLogRepository;

    public SolicitudEventListener(
            EquipoRepository equipoRepository,
            EventLogRepository eventLogRepository) {

        this.equipoRepository = equipoRepository;
        this.eventLogRepository = eventLogRepository;
    }

    @Async
    @EventListener
    public void manejarSolicitudCreada(
            SolicitudCreadaEvent event) {

        System.out.println(
                "EVENTO RECIBIDO: Solicitud creada con ID "
                        + event.getSolicitudId()
                        + " | Hilo: "
                        + Thread.currentThread().getName()
        );

        EventLog log = new EventLog(
                "SOLICITUD_CREADA",
                "Solicitud",
                event.getSolicitudId(),
                "Se creó una solicitud para el equipo ID "
                        + event.getEquipoId()
        );

        log.setProcesado(true);

        eventLogRepository.save(log);
    }

    @Async
    @EventListener
    public void manejarSolicitudAprobada(
            SolicitudAprobadaEvent event) {

        Equipo equipo = equipoRepository.findById(
                event.getEquipoId()
        ).orElse(null);

        if (equipo == null) {
            System.out.println(
                    "No se encontró el equipo asociado a la solicitud."
            );
            return;
        }

        equipo.setEstado(EstadoEquipo.RESERVADO);
        equipoRepository.save(equipo);

        System.out.println(
                "EVENTO PROCESADO: Equipo "
                        + equipo.getCodigoInventario()
                        + " reservado por aprobación de solicitud."
                        + " | Hilo: "
                        + Thread.currentThread().getName()
        );

        EventLog log = new EventLog(
                "SOLICITUD_APROBADA",
                "Solicitud",
                event.getSolicitudId(),
                "Solicitud aprobada y equipo "
                        + equipo.getCodigoInventario()
                        + " reservado"
        );

        log.setProcesado(true);

        eventLogRepository.save(log);
    }

    @Async
    @EventListener
    public void manejarSolicitudRechazada(
            SolicitudRechazadaEvent event) {

        System.out.println(
                "EVENTO PROCESADO: Solicitud "
                        + event.getSolicitudId()
                        + " rechazada."
                        + " | Hilo: "
                        + Thread.currentThread().getName()
        );

        EventLog log = new EventLog(
                "SOLICITUD_RECHAZADA",
                "Solicitud",
                event.getSolicitudId(),
                "La solicitud fue rechazada."
        );

        log.setProcesado(true);

        eventLogRepository.save(log);
    }

    @Async
    @EventListener
    public void manejarEquipoEntregado(
            EquipoEntregadoEvent event) {

        Equipo equipo = equipoRepository.findById(
                event.getEquipoId()
        ).orElse(null);

        if (equipo == null) {
            System.out.println(
                    "No se encontró el equipo asociado a la entrega."
            );
            return;
        }

        equipo.setEstado(EstadoEquipo.PRESTADO);
        equipoRepository.save(equipo);

        System.out.println(
                "EVENTO PROCESADO: Equipo "
                        + equipo.getCodigoInventario()
                        + " entregado."
                        + " | Hilo: "
                        + Thread.currentThread().getName()
        );

        EventLog log = new EventLog(
                "EQUIPO_ENTREGADO",
                "Equipo",
                equipo.getId(),
                "Equipo "
                        + equipo.getCodigoInventario()
                        + " entregado para la solicitud "
                        + event.getSolicitudId()
        );

        log.setProcesado(true);

        eventLogRepository.save(log);
    }

    @Async
    @EventListener
    public void manejarEquipoDevuelto(
            EquipoDevueltoEvent event) {

        Equipo equipo = equipoRepository.findById(
                event.getEquipoId()
        ).orElse(null);

        if (equipo == null) {
            System.out.println(
                    "No se encontró el equipo asociado a la devolución."
            );
            return;
        }

        equipo.setEstado(EstadoEquipo.DISPONIBLE);
        equipoRepository.save(equipo);

        System.out.println(
                "EVENTO PROCESADO: Equipo "
                        + equipo.getCodigoInventario()
                        + " devuelto y disponible nuevamente."
                        + " | Hilo: "
                        + Thread.currentThread().getName()
        );

        EventLog log = new EventLog(
                "EQUIPO_DEVUELTO",
                "Equipo",
                equipo.getId(),
                "Equipo "
                        + equipo.getCodigoInventario()
                        + " devuelto para la solicitud "
                        + event.getSolicitudId()
        );

        log.setProcesado(true);

        eventLogRepository.save(log);
    }
}