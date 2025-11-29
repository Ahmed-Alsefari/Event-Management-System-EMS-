package ems.app.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data

// name  = service_package

public class ServicePackage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private double price;
    private boolean active = true;
}
