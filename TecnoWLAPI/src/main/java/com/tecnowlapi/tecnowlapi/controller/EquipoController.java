package com.tecnowlapi.tecnowlapi.controller;

import com.tecnowlapi.tecnowlapi.model.Equipo;
import com.tecnowlapi.tecnowlapi.model.EstadoEquipo;
import com.tecnowlapi.tecnowlapi.service.EquipoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/equipos")
public class EquipoController {

    private final EquipoService equipoService;

    public EquipoController(EquipoService equipoService) {
        this.equipoService = equipoService;
    }

    @GetMapping
    public ResponseEntity<List<Equipo>> obtenerTodos() {
        return ResponseEntity.ok(equipoService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Equipo> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(equipoService.obtenerPorId(id));
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Equipo>> obtenerPorEstado(
            @PathVariable EstadoEquipo estado) {

        return ResponseEntity.ok(
                equipoService.obtenerPorEstado(estado)
        );
    }

    @PostMapping
    public ResponseEntity<Equipo> crear(@RequestBody Equipo equipo) {

        Equipo equipoCreado = equipoService.guardar(equipo);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(equipoCreado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Equipo> actualizar(
            @PathVariable Long id,
            @RequestBody Equipo equipo) {

        return ResponseEntity.ok(
                equipoService.actualizar(id, equipo)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {

        equipoService.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}