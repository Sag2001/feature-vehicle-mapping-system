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
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "vehicle_model",
        uniqueConstraints = {
                @UniqueConstraint(name = "unique_vehicle", columnNames = {"name", "platform", "variant"})
        }
)
public class VehicleTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 100)
    private String platform;

    @Column(nullable = false, length = 100)
    private String variant;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

}
