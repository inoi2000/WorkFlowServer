package com.petproject.workflow.store.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "accesses")
public class Access {

    @Id
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private AccessDurationType type;

    @Column(name = "valid_until")
    private LocalDate validUntil;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "data_id")
    private AccessData data;

    @Column(name = "issuer_id", columnDefinition = "BINARY(16)")
    private UUID issuerId;
    @Column(name = "holder_id", columnDefinition = "BINARY(16)")
    private UUID holderId;

    private static final int NUMBER_OF_DAYS_UNTIL_ACCESS_EXPIRES = 7;

    public Status getStatus() {
        switch (type) {
            case ONETIME -> {
                if(LocalDate.now().isAfter(validUntil)) {
                    return Status.EXPIRED;
                } else {
                    return Status.VALID;
                }
            }
            case TEMPORARY -> {
                if(LocalDate.now().isAfter(validUntil)) {
                    return Status.EXPIRED;
                } else if (LocalDate.now().isAfter(validUntil.minusDays(NUMBER_OF_DAYS_UNTIL_ACCESS_EXPIRES))) {
                    return Status.EXPIRING;
                } else {
                    return Status.VALID;
                }
            }
            case PERMANENT -> {
                return Status.VALID;
            }
        };
        return null;
    }
}
