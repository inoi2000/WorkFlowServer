package com.petproject.workflow.store;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "absence_policies")
public class AbsencePolicy {
    @Id
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, unique = true, length = 64)
    private AbsenceType type;

    @Column(name = "max_duration_days", nullable = false)
    private Integer maxDurationDays;

    @Column(name = "requires_approval", nullable = false)
    private Boolean requiresApproval;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
