package org.themselves.alber.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.themselves.alber.domain.Pin;
import org.themselves.alber.domain.User;
import org.themselves.alber.domain.Wastebasket;

import java.util.List;
import java.util.Optional;

public interface PinRepository extends JpaRepository<Pin, Long> {

    @Query("select p from Pin p where p.wastebasket = :wastebasketId")
    Optional<Pin> findByWastebasket(@Param("wastebasketId") Long wastebasketId);

    @Query("select p " +
            "from Pin p " +
            "join fetch p.wastebasket w " +
            "where p.user = :user ")
    List<Pin> findByUser(@Param("user") User user);
}
