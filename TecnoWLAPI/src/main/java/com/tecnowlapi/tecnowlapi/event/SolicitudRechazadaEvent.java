package com.tecnowlapi.tecnowlapi.event;

public class SolicitudRechazadaEvent {

    private final Long solicitudId;

    public SolicitudRechazadaEvent(Long solicitudId) {
        this.solicitudId = solicitudId;
    }

    public Long getSolicitudId() {
        return solicitudId;
    }
}