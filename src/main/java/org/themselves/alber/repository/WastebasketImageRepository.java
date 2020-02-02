package org.themselves.alber.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.themselves.alber.domain.Wastebasket;
import org.themselves.alber.domain.WastebasketImage;

import java.util.List;

public interface WastebasketImageRepository extends JpaRepository<WastebasketImage, Long> {

}
