package org.themselves.alber.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.themselves.alber.domain.Pin;

public interface PinRepository extends JpaRepository<Pin, Long> {
}
