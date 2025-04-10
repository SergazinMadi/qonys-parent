package serg.madi.apartmentservice.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "apartments")
@Data
public class Apartment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String type;
    private Integer capacity;
    private String address;
    private Double costPerNight;
    private Double area;

    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    private City city;
}
