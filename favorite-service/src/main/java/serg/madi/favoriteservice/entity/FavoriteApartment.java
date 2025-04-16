package serg.madi.favoriteservice.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "favorite_apartments")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class FavoriteApartment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long apartmentId;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

}
