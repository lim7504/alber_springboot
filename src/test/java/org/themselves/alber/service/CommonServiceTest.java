package org.themselves.alber.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.themselves.alber.domain.Image;
import org.themselves.alber.repository.ImageRepository;
import org.themselves.alber.repository.mapper.ImageMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK
        ,properties = "spring.config.location=" +
        "classpath:/application.yml" +
        ",classpath:/db.yml")
@AutoConfigureMockMvc
@Transactional
class CommonServiceTest {

    @Autowired
    CommonService commonService;

    @Autowired
    ImageMapper imageMapper;

    @Test
    @Disabled //image경로가 다르기 때문에 유닛 테스트 할때만 사용
    public void testAddImage() throws IOException{

        Path path = Paths.get("image/wastebasket_1/7fd70c0ddf264079a95b3f69e8ec0e63.png");
        String name = "a.png";
        String originalFileName = "a.png";
        String contentType = "image/png";
        byte[] content = null;
        content = Files.readAllBytes(path);

        MultipartFile result = new MockMultipartFile(name,
                originalFileName, contentType, content);

        commonService.imageFileUpload(result);

    }

    @Test
    public void testImageContructor() {
        Image image = imageMapper.mapping("10");
        assertNotNull(image);
    }

    @Test
    public void testImageConstructorWithImageIdList() {
        List<String> imageIdList = new ArrayList<>();
        imageIdList.add("10");
        imageIdList.add("11");

        List<Image> imageList = imageMapper.mapping(imageIdList);
        assertEquals(2, imageList.size());
    }
}