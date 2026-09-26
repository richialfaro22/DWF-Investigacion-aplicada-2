package com.tecnowlapi.tecnowlapi.repository;

import com.tecnowlapi.tecnowlapi.model.EventLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventLogRepository extends JpaRepository<EventLog, Long> {

    List<EventLog> findByTipoEvento(String tipoEvento);

    List<EventLog> findByEntidad(String entidad);

    List<EventLog> findByProcesado(Boolean procesado);
}
