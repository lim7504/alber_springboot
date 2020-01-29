package org.themselves.alber.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.themselves.alber.domain.Image;

public interface ImageRepository extends JpaRepository<Image,Long> {
}
