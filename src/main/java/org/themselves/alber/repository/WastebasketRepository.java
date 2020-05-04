package org.themselves.alber.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.themselves.alber.controller.wastebasket.dto.WastebasketForMyRegistWastebasketDto;
import org.themselves.alber.domain.Notifycation;
import org.themselves.alber.domain.User;
import org.themselves.alber.domain.Wastebasket;

import java.util.List;
import java.util.Optional;

public interface WastebasketRepository extends JpaRepository<Wastebasket, Long> , WastebasketRepositoryCustom{
    @Query("select w " +
            "from Wastebasket w " +
            "left join fetch w.wastebasketImageList wi " +
            "left join fetch wi.image " +
            "where w.id = :id ")
    Optional<Wastebasket> findByWastebasketWithImage(@Param("id") Long id);
}
