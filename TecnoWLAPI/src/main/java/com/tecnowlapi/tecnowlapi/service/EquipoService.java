package com.tecnowlapi.tecnowlapi.service;

import com.tecnowlapi.tecnowlapi.exception.RecursoDuplicadoException;
import com.tecnowlapi.tecnowlapi.exception.RecursoNoEncontradoException;
import com.tecnowlapi.tecnowlapi.model.Equipo;
import com.tecnowlapi.tecnowlapi.model.EstadoEquipo;
import com.tecnowlapi.tecnowlapi.repository.EquipoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipoService {

    private final EquipoRepository equipoRepository;

    public EquipoService(EquipoRepository equipoRepository) {
        this.equipoRepository = equipoRepository;
    }

    public List<Equipo> obtenerTodos() {
        return equipoRepository.findAll();
    }

    public Equipo obtenerPorId(Long id) {
        return equipoRepository.findById(id)
                .orElseThrow(() ->
                        new RecursoNoEncontradoException(
                                "Equipo no encontrado con ID: " + id
                        )
                );
    }

    public Equipo guardar(Equipo equipo) {

        if (equipoRepository.existsByCodigoInventario(
                equipo.getCodigoInventario())) {

            throw new RecursoDuplicadoException(
                    "Ya existe un equipo con el código de inventario: "
                            + equipo.getCodigoInventario()
            );
        }

        return equipoRepository.save(equipo);
    }

    public Equipo actualizar(Long id, Equipo datosActualizados) {

        Equipo equipo = obtenerPorId(id);

        equipo.setCodigoInventario(
                datosActualizados.getCodigoInventario()
        );
        equipo.setNombre(datosActualizados.getNombre());
        equipo.setTipo(datosActualizados.getTipo());
        equipo.setMarca(datosActualizados.getMarca());
        equipo.setModelo(datosActualizados.getModelo());
        equipo.setEstado(datosActualizados.getEstado());

        return equipoRepository.save(equipo);
    }

    public void eliminar(Long id) {
        Equipo equipo = obtenerPorId(id);
        equipoRepository.delete(equipo);
    }

    public List<Equipo> obtenerPorEstado(EstadoEquipo estado) {
        return equipoRepository.findByEstado(estado);
    }
}