package com.zybrof.url_shortener.src.repository;

import com.zybrof.url_shortener.src.entity.Shortening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShorteningRepository extends JpaRepository<Shortening, Long> {
    Optional<Shortening> findByCode(String code);

    Optional<Shortening> findByUrl(String url);
}
