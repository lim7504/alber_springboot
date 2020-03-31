package org.themselves.alber.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.themselves.alber.domain.User;
import org.themselves.alber.domain.Wastebasket;
import org.themselves.alber.domain.WastebasketComment;
import org.themselves.alber.domain.WastebasketImage;

import java.util.List;
import java.util.Optional;

public interface WastebasketImageRepository extends JpaRepository<WastebasketImage, Long> {

    @Query("select wi " +
            "from WastebasketImage wi " +
            "inner join fetch wi.image " +
            "where wi.wastebasket = :wastebasket " +
            "order by wi.createdDate desc ")
    List<WastebasketImage> findByWastebasket(@Param("wastebasket") Wastebasket wastebasket);
}
