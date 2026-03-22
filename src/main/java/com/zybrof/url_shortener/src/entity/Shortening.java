package com.zybrof.url_shortener.src.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "shortening")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Shortening {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "shortening_seq")
    @SequenceGenerator(name = "shortening_seq", sequenceName = "shortening_id_seq", allocationSize = 1)
    private Long id;

    @NonNull
    @Column(nullable = false, length = 2048, unique = true)
    private String url;

    @Column(length = 10, unique = true)
    @Setter
    private String code;
}
