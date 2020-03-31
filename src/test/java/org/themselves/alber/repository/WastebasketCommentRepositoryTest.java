package org.themselves.alber.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.themselves.alber.controller.wastebasketcomment.dto.WastebasketCommentNImageDto;
import org.themselves.alber.domain.User;
import org.themselves.alber.domain.WastebasketComment;
import org.themselves.alber.repository.projection.MyPageProjection;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    UserRepository userRepository;

    @Autowired
    WastebasketImageRepository wastebasketImageRepository;

    @Autowired
    ObjectMapper objectMapper;

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
        List<WastebasketCommentNImageDto> commentNImageDtoList = new ArrayList<>();
        Optional<User> user = userRepository.findByEmail("aaa@aaa");
        List<MyPageProjection> commentList = wastebasketCommentRepository.findByUser(user.get().getId());
        for (MyPageProjection comment : commentList) {

            WastebasketCommentNImageDto commentNImageDto = new WastebasketCommentNImageDto();
            commentNImageDto.setContents(comment.getContents().toString());
            commentNImageDto.setBoxName(comment.getBox_Name().toString());
            commentNImageDto.setAreaSi(comment.getArea_Si().toString());
            commentNImageDto.setImage(comment.getUrl().toString());
            commentNImageDtoList.add(commentNImageDto);
        }

        for (WastebasketCommentNImageDto commentNImageDto : commentNImageDtoList) {
            System.out.println("wastebasketCommentNImageDto = " + commentNImageDto);
        }
    }

    @Test
    public void testWastebasketComment3() throws JsonProcessingException {

        Optional<User> user = userRepository.findByEmail("aaa@aaa");
        List<WastebasketComment> commentList = wastebasketCommentRepository.findByUserQueryDSL(user.get());

        System.out.println(objectMapper.writeValueAsString(commentList));

    }
}