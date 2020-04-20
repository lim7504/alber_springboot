package org.themselves.alber.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.themselves.alber.domain.Notifycation;
import org.themselves.alber.domain.User;

import java.util.Optional;

public interface NotifycationRepository extends JpaRepository<Notifycation, Long> {
    Optional<Notifycation> findByNotiTitle(String notiTitle);
}
