package org.themselves.alber.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
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
    public void addWastebasket(Wastebasket wastebasket, User user, MultipartFile[] files) {

        wastebasket.setGarType(GarType.Standard); //쓰레기통 종류를 넣을것인가 말것인가

        //예외처리 무슨 예외가 있을까???? 곰곰히 생각해보자.......

        Wastebasket newWastebaseket = wastebasketRepository.save(wastebasket);

        //핀저장

        //이미지 저장

    }

}
