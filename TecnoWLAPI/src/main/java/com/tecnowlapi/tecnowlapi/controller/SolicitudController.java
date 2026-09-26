package com.tecnowlapi.tecnowlapi.controller;

import com.tecnowlapi.tecnowlapi.model.Solicitud;
import com.tecnowlapi.tecnowlapi.service.SolicitudService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/solicitudes")
public class SolicitudController {

    private final SolicitudService solicitudService;

    public SolicitudController(SolicitudService solicitudService) {
        this.solicitudService = solicitudService;
    }

    @GetMapping
    public ResponseEntity<List<Solicitud>> obtenerTodas() {
        return ResponseEntity.ok(solicitudService.obtenerTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Solicitud> obtenerPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                solicitudService.obtenerPorId(id)
        );
    }

    @PostMapping
    public ResponseEntity<Solicitud> crear(
            @RequestBody Solicitud solicitud) {

        Solicitud solicitudCreada =
                solicitudService.guardar(solicitud);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(solicitudCreada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id) {

        solicitudService.eliminar(id);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/aprobar")
    public ResponseEntity<Solicitud> aprobar(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                solicitudService.aprobar(id)
        );
    }

    @PostMapping("/{id}/rechazar")
    public ResponseEntity<Solicitud> rechazar(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                solicitudService.rechazar(id)
        );
    }

    @PostMapping("/{id}/entregar")
    public ResponseEntity<Solicitud> entregar(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                solicitudService.entregar(id)
        );
    }

    @PostMapping("/{id}/devolver")
    public ResponseEntity<Solicitud> devolver(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                solicitudService.devolver(id)
        );
    }
}