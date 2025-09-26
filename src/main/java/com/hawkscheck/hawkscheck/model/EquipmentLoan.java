package com.hawkscheck.hawkscheck.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "equipment_loans")
@Getter
@Setter
public class EquipmentLoan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Qual equipamento foi emprestado
    @ManyToOne
    @JoinColumn(name = "equipment_id", nullable = false)
    private Equipment equipment;

    // Quem pegou (professor = usuário do sistema)
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User professor;

    @Column(nullable = false)
    private LocalDateTime borrowedAt;

    private LocalDateTime returnedAt;

    // Se o professor assinou fisicamente/digitalmente
    private boolean signatureCollected;

    // Observações sobre o empréstimo ou devolução
    private String notes;

    // Estado na devolução (bom, regular, manutenção)
    @Enumerated(EnumType.STRING)
    private EquipmentConditionEnum conditionOnReturn;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
