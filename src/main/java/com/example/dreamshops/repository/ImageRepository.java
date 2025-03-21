package com.example.dreamshops.repository;

import com.example.dreamshops.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
//    Image findByName(String fileName);
//
//    boolean existsByName(String fileName);
}
