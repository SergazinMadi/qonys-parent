package serg.madi.apartmentservice.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "amenity_apartment")
public class AmenityApartment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "apartment_id", nullable = false)
    private Apartment apartment;

    @ManyToOne
    @JoinColumn(name = "amenity_id", nullable = false)
    private Amenity amenity;
}
