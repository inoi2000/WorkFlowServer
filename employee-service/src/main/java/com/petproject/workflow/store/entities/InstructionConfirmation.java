package com.petproject.workflow.store.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "instruction_confirmations")
public class InstructionConfirmation {

    @EmbeddedId
    private InstructionConfirmationKey id;

    @Column(name = "is_confirmed")
    private boolean isConfirmed;

    @Column(name = "confirmed_at")
    private LocalDateTime confirmedAt;
}
