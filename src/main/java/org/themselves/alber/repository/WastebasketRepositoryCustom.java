package org.themselves.alber.repository;

import org.themselves.alber.controller.wastebasket.dto.WastebasketForMyRegistWastebasketDto;
import org.themselves.alber.domain.Pin;
import org.themselves.alber.domain.User;

import java.util.List;

public interface WastebasketRepositoryCustom {

//    List<WastebasketForMyRegistWastebasketDto> findByUserMyRegist(User user);
    List<Pin> findByUserForMyRegistWastebasket(User user);

}
