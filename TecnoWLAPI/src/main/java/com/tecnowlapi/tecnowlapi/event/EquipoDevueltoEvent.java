package com.tecnowlapi.tecnowlapi.event;

public class EquipoDevueltoEvent {

    private final Long solicitudId;
    private final Long equipoId;

    public EquipoDevueltoEvent(Long solicitudId, Long equipoId) {
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
