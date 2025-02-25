package com.petproject.workflow_server.entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "vacations")
public class Vacation {
    @Id
    @Column(name = "id", columnDefinition = "BYNARY(16)")
    private UUID id;

    @Column(name = "start")
    private String start;

    @Column(name = "end")
    private String end;

    public Vacation() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
