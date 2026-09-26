package com.tecnowlapi.tecnowlapi.repository;

import com.tecnowlapi.tecnowlapi.model.EstadoSolicitud;
import com.tecnowlapi.tecnowlapi.model.Solicitud;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SolicitudRepository extends JpaRepository<Solicitud, Long> {

    List<Solicitud> findByEstado(EstadoSolicitud estado);

    List<Solicitud> findByUsuarioId(Long usuarioId);

    List<Solicitud> findByEquipoId(Long equipoId);
}
