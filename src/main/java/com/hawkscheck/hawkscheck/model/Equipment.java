package com.hawkscheck.hawkscheck.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String patrimonyNumber;   // Patrimônio / Tombamento
    private String serialNumber;      // Nº de série
    private String brandModel;        // Marca / Modelo

    @Enumerated(EnumType.STRING)
    private EquipmentConditionEnum condition; // Estado de conservação

    private String defaultLocation;   // Localização padrão (ex: Carrinho 1)
    private String notes;             // Observações

    private LocalDateTime createdAt;
}
