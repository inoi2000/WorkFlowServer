package com.petproject.workflow.store;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "policies")
public class Policy {

    @Id
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 64)
    private AbsenceType type;

    @Column(name = "max_duration_days", nullable = false)
    private Integer maxDurationDays;

    @Column(name = "requires_approval", nullable = false)
    private Boolean requiresApproval;

    @ElementCollection
    @CollectionTable(
            name = "policies_positions",
            joinColumns = @JoinColumn(name = "policy_id")
    )
    @Column(name = "position_id", columnDefinition = "BINARY(16)")
    private List<UUID> canApprovePositionIds;
}