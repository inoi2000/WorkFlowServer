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
@Table(name = "storages")
public class Storage {

    @Id
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(name = "total_volume", nullable = false)
    private double totalVolume;

    @Column(name = "current_volume", nullable = false)
    private double currentVolume;

    @ManyToOne()
    @JoinColumn(name = "current_waste_id")
    private Waste currentWaste;

    @ManyToMany(cascade = CascadeType.PERSIST,  fetch = FetchType.LAZY)
    @JoinTable(
            name = "allowed_storages_wastes",
            joinColumns = @JoinColumn(name = "storage_id"),
            inverseJoinColumns = @JoinColumn(name = "waste_id")
    )
    private List<Waste> allowedWastes = new ArrayList<>();
}
