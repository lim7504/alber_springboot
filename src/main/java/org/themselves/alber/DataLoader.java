package org.themselves.alber;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.themselves.alber.controller.user.dto.UserJoinDto;
import org.themselves.alber.domain.User;
import org.themselves.alber.domain.Wastebasket;
import org.themselves.alber.domain.common.UserType;
import org.themselves.alber.repository.UserRepository;
import org.themselves.alber.repository.WastebasketRepository;
import org.themselves.alber.service.UserService;

@Component
@Transactional
@RequiredArgsConstructor
public class DataLoader implements ApplicationRunner {

    private final UserService userService;
    private final WastebasketRepository wastebasketRepository;


    public void run(ApplicationArguments args) {

//        for(int i = 0; i< 100; i++){
//            UserJoinDto user = new UserJoinDto();
//            user.setNickname("TestUser" + i);
//            user.setEmail("TestUser" + i + "@alber.org");
//            user.setPassword("1234abcd");
//            userService.joinUser(user, UserType.USER);
//
//            Wastebasket wastebasket = new Wastebasket();
//            wastebasket.setBoxName("box" + i);
//            wastebasket.setAreaSi("서울시");
//            wastebasket.setAreaGu("강남구");
//            wastebasket.setAreaDong("서초동");
//            wastebasket.setLatitude("37.131123");
//            wastebasket.setLongitude("126.123122");
//            wastebasketRepository.save(wastebasket);
//        }



    }
}