package com.example.painting_app.repository;

import com.example.painting_app.model.Painting;
import com.example.painting_app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaintingRepository extends JpaRepository<Painting, Long> {
    Optional<Painting> findByUser(User user);
}