package serg.madi.apartmentservice.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "amenities")
@Data
public class Amenity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}
