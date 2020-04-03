package org.themselves.alber.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.themselves.alber.controller.wastebasket.dto.WastebasketForMyRegistWastebasketDto;
import org.themselves.alber.controller.wastebasketcomment.dto.WastebasketCommentForMyRegistCommentDto;
import org.themselves.alber.domain.*;
import org.themselves.alber.repository.projection.MyPageProjection;
import org.themselves.alber.service.UserService;

import javax.persistence.EntityManager;
import java.util.*;

import static com.querydsl.jpa.JPAExpressions.select;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.themselves.alber.domain.QImage.image;
import static org.themselves.alber.domain.QPin.pin;
import static org.themselves.alber.domain.QUser.user;
import static org.themselves.alber.domain.QWastebasket.wastebasket;
import static org.themselves.alber.domain.QWastebasketComment.wastebasketComment;
import static org.themselves.alber.domain.QWastebasketImage.wastebasketImage;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK
        ,properties = "spring.config.location=" +
        "classpath:/application.yml" +
        ",classpath:/db.yml")
@AutoConfigureMockMvc
@Transactional
class WastebasketCommentRepositoryTest {

    @Autowired
    WastebasketCommentRepository wastebasketCommentRepository;

    @Autowired
    WastebasketRepository wastebasketRepository;

    @Autowired
    UserService userService;

    @Autowired
    WastebasketImageRepository wastebasketImageRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    EntityManager em;

    @Test
    @Rollback(false)
    public void testAddComment() {
        User user = userService.getUserByEmail("aaa@aaa");
        Optional<Wastebasket> wastebasket = wastebasketRepository.findById(105L);
        WastebasketComment wc = new WastebasketComment();
        wc.setWastebasket(wastebasket.get());
        wc.setUser(user);
        wc.setContents("111111111");

        wastebasketCommentRepository.save(wc);
    }

//    @Test
//    public void testWastebasketComment() {
//        List<WastebasketCommentNImageDto> commentNImageDtoList = new ArrayList<>();
//        Optional<User> user = userRepository.findByEmail("aaa@aaa");
//        List<WastebasketComment> commentList = wastebasketCommentRepository.findByUser(user.get().getId());
//        for (WastebasketComment comment : commentList) {
//            List<WastebasketImage> imageList
//                    = wastebasketImageRepository.findByWastebasket(comment.getWastebasket());
//
//            WastebasketCommentNImageDto commentNImageDto = new WastebasketCommentNImageDto();
//            commentNImageDto.setContents(comment.getContents());
//            commentNImageDto.setBoxName(comment.getWastebasket().getBoxName());
//            commentNImageDto.setAreaSi(comment.getWastebasket().getAreaSi());
//            if(imageList.size() != 0)
//                commentNImageDto.setImage(imageList.get(0).getImage().getUrl());
//            commentNImageDtoList.add(commentNImageDto);
//        }
//
//        for (WastebasketCommentNImageDto commentNImageDto : commentNImageDtoList) {
//            System.out.println("wastebasketCommentNImageDto = " + commentNImageDto);
//        }
//    }

    @Test
    public void testWastebasketComment2() {
        List<WastebasketCommentForMyRegistCommentDto> commentNImageDtoList = new ArrayList<>();
        User user = userService.getUserByEmail("aaa@aaa");
        List<MyPageProjection> commentList = wastebasketCommentRepository.findByUser(user.getId());
        for (MyPageProjection comment : commentList) {

            WastebasketCommentForMyRegistCommentDto commentNImageDto = new WastebasketCommentForMyRegistCommentDto();
            commentNImageDto.setContents(comment.getContents().toString());
            commentNImageDto.setBoxName(comment.getBox_Name().toString());
            commentNImageDto.setAreaSi(comment.getArea_Si().toString());
            if (comment.getUrl() != null)
                commentNImageDto.setImage(comment.getUrl().toString());
            commentNImageDtoList.add(commentNImageDto);
        }

        for (WastebasketCommentForMyRegistCommentDto commentNImageDto : commentNImageDtoList) {
            System.out.println("wastebasketCommentNImageDto = " + commentNImageDto);
        }
    }

    @Test
    public void testWastebasketComment3() throws Exception {
        User user = userService.getUserByEmail("aaa@aaa");
        List<WastebasketCommentForMyRegistCommentDto> commentList = wastebasketCommentRepository.findByUserForMyRegistComment(user);
        assertNotNull(commentList);
    }

    @Test
    public void testWastebasketCommentForMyRegistWastebasket() {
        Set<Long> wastebasketIdList = new HashSet<>();
        User findUser = userService.getUserByEmail("aaa@aaa");
        JPAQueryFactory query = new JPAQueryFactory(em);

        QImage wastebasketImage = new QImage("wastebasketImage");
        QImage userImage = new QImage("userImage");

        List<Pin> result = query
                .selectDistinct(pin)
                .from(pin)
                .innerJoin(pin.wastebasket, wastebasket).fetchJoin()
                .leftJoin(wastebasket.image, wastebasketImage).fetchJoin()
                .leftJoin(wastebasket.wastebasketCommentList, wastebasketComment).fetchJoin()
                .leftJoin(wastebasketComment.user, user).fetchJoin()
                .leftJoin(user.image, userImage).fetchJoin()
                .where(pin.user.eq(findUser))
                .orderBy(wastebasket.createdDate.desc())
                .fetch();
    }

}