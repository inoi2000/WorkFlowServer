package com.petproject.workflow.store.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "wastes")
public class Waste {

    @Id
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(name = "is_recycle", nullable = false)
    private boolean isRecycle;

    @ManyToMany(mappedBy = "allowedWastes", fetch = FetchType.LAZY)
    private List<Storage> allowedStorages = new ArrayList<>();
}
