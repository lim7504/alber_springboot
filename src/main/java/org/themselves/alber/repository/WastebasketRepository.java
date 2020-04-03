package org.themselves.alber.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.themselves.alber.controller.wastebasket.dto.WastebasketForMyRegistWastebasketDto;
import org.themselves.alber.domain.User;
import org.themselves.alber.domain.Wastebasket;

import java.util.List;

public interface WastebasketRepository extends JpaRepository<Wastebasket, Long> , WastebasketRepositoryCustom{

}
