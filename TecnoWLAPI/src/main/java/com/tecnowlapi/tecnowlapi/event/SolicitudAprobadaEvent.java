package com.tecnowlapi.tecnowlapi.event;

public class SolicitudAprobadaEvent {

    private final Long solicitudId;
    private final Long equipoId;

    public SolicitudAprobadaEvent(Long solicitudId, Long equipoId) {
        this.solicitudId = solicitudId;
        this.equipoId = equipoId;
    }

    public Long getSolicitudId() {
        return solicitudId;
    }

    public Long getEquipoId() {
        return equipoId;
    }
}