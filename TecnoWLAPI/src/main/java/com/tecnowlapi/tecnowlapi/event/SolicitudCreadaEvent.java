package com.tecnowlapi.tecnowlapi.event;

public class SolicitudCreadaEvent {

    private final Long solicitudId;
    private final Long usuarioId;
    private final Long equipoId;

    public SolicitudCreadaEvent(Long solicitudId,
                                Long usuarioId,
                                Long equipoId) {
        this.solicitudId = solicitudId;
        this.usuarioId = usuarioId;
        this.equipoId = equipoId;
    }

    public Long getSolicitudId() {
        return solicitudId;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public Long getEquipoId() {
        return equipoId;
    }
}