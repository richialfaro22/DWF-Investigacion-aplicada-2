package com.tecnowlapi.tecnowlapi.repository;

import com.tecnowlapi.tecnowlapi.model.Equipo;
import com.tecnowlapi.tecnowlapi.model.EstadoEquipo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EquipoRepository extends JpaRepository<Equipo, Long> {

    Optional<Equipo> findByCodigoInventario(String codigoInventario);

    List<Equipo> findByEstado(EstadoEquipo estado);

    boolean existsByCodigoInventario(String codigoInventario);
}
