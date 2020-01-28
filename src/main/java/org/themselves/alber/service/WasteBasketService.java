package org.themselves.alber.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.themselves.alber.domain.GarType;
import org.themselves.alber.domain.Image;
import org.themselves.alber.domain.User;
import org.themselves.alber.domain.Wastebasket;
import org.themselves.alber.repository.WastebasketRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WasteBasketService {

    private final WastebasketRepository wastebasketRepository;

    @Transactional
    public void addWastebasket(Wastebasket wastebasket, User user, Image[] images) {

        wastebasket.setGarType(GarType.Standard); //쓰레기통 종류를 넣을것인가 말것인가

        //예외처리

        Wastebasket newWastebaseket = wastebasketRepository.save(wastebasket);

        //핀저장

        //이미지 저장

    }

}
