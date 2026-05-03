package com.Feature_to_Market.and.Vehicle.Applicability.Mapping.JLRProjectVehicleMapping.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "market",
        uniqueConstraints = {
                @UniqueConstraint(name = "unique_market", columnNames = {"name", "region"})
        }
)
public class MarketTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 100)
    private String name;

    @Column(nullable = false,length = 100)
    private String region;

    @Column(columnDefinition = "TEXT")
    private String regulations;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}
