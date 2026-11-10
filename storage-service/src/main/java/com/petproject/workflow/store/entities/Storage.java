package com.petproject.workflow.store.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
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
    @JoinColumn(name = "current_product_id")
    private Product currentProduct;

    @Column(name = "allowed_mixing", nullable = false)
    private boolean allowedMixing;

    @ManyToOne()
    @JoinColumn(name = "output_product_id")
    private Product outputProduct;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StorageSector sector;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StoragePlacement placement;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StorageLocation location;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToMany(cascade = CascadeType.PERSIST,  fetch = FetchType.LAZY)
    @JoinTable(
            name = "allowed_storages_products",
            joinColumns = @JoinColumn(name = "storage_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> allowedProducts = new ArrayList<>();
}
