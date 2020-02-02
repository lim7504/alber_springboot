package org.themselves.alber.controller.wastebasket;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.themselves.alber.config.response.ResponseContent;
import org.themselves.alber.config.response.StatusCode;
import org.themselves.alber.controller.session.UserLoginDto;
import org.themselves.alber.controller.user.UserDto;
import org.themselves.alber.domain.Image;
import org.themselves.alber.domain.User;
import org.themselves.alber.domain.Wastebasket;
import org.themselves.alber.repository.WastebasketImageRepository;
import org.themselves.alber.service.UserService;
import org.themselves.alber.service.WasteBasketService;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/wastebasket")
public class WastebasketController {

    private final ModelMapper modelMapper;

    private final WasteBasketService wasteBasketService;

    private final UserService userService;

    /**
     * 쓰레기통 등록
     * 쓰레기통과 이미지정보를 함께 등록
     * pin도 함께 등록
     */
    @PostMapping(consumes="multipart/form-data; charset=UTF-8", produces = "application/json; charset=UTF-8", headers = "X-AUTH-TOKEN")
    @ApiOperation(value = "쓰레기통 등록")
    public ResponseEntity<ResponseContent> addWestebasket(
                                                          @RequestParam("boxName") String boxName
                                                        , @RequestParam("areaSi") String areaSi
                                                        , @RequestParam("areaGu") String areaGu
                                                        , @RequestParam("areaDong") String areaDong
                                                        , @RequestParam("latitude") String latitude
                                                        , @RequestParam("longitude") String longitude
                                                        , @RequestParam("files") MultipartFile[] files
                                                        , Principal principal) {

        Wastebasket wastebasket = new Wastebasket();
        wastebasket.setBoxName(boxName);
        wastebasket.setAreaSi(areaSi);
        wastebasket.setAreaGu(areaGu);
        wastebasket.setAreaDong(areaDong);
        wastebasket.setLatitude(latitude);
        wastebasket.setLongitude(longitude);

        User user = userService.getUserByEmail(principal.getName());

        wasteBasketService.addWastebasket(wastebasket, user, files);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseContent(StatusCode.SUCCESS_CREATED));
    }

    /**
     * 쓰레기통 보기(상세)
     * 아무나 가능~~
     * 댓글도 한번에 불러오기 등록된 순서 내림차순
     */
    @GetMapping(value = "/{id}", consumes = "application/json; charset=UTF-8", produces = "application/json; charset=UTF-8", headers = "X-AUTH-TOKEN")
    @ApiOperation(value = "쓰레기통 상세")
    public ResponseEntity<ResponseContent<WastebasketDto>> getWastebaseketOne(@PathVariable Long id) {

        WastebasketDto wastebasketDto = wasteBasketService.getWastebasketOne(id);

        return ResponseEntity
                .ok()
                .body(new ResponseContent<WastebasketDto>(StatusCode.SUCCESS,wastebasketDto));
    }


    /**
     * 주변 500m에 있는 쓰레기통 보기
     * 위도 경도를 받아와야 함
     * 500m 계산 법? 위도 경도로부터 반지름 500m 이내 둘레의 쓰레기통 검색 로직
     * 간이 정보, 사진
     */



    /**
     * 쓰레기통 수정
     * 이미지 수정가능
     * 본인이 작성하지 않은 댓글은 수정 불가
     * pin을 등록한 유저만이 수정가능
     */
    @PutMapping(value = "/{id}", consumes = "multipart/form-data; charset=UTF-8", produces = "application/json; charset=UTF-8", headers = "X-AUTH-TOKEN")
    @ApiOperation(value = "쓰레기통 수정")
    public ResponseEntity<ResponseContent> setWastebaseketOne(@PathVariable Long id
                                                                            , @RequestParam("boxName") String boxName
                                                                            , @RequestParam("areaDesc") String areaDesc
                                                                            , @RequestParam("areaSi") String areaSi
                                                                            , @RequestParam("areaGu") String areaGu
                                                                            , @RequestParam("areaDong") String areaDong
                                                                            , @RequestParam("latitude") String latitude
                                                                            , @RequestParam("longitude") String longitude
                                                                            , @RequestParam("files") MultipartFile[] files) {

        Wastebasket requestWastebasket = new Wastebasket();
        requestWastebasket.setId(id);
        requestWastebasket.setBoxName(boxName);
        requestWastebasket.setAreaDesc(areaDesc);
        requestWastebasket.setAreaSi(areaSi);
        requestWastebasket.setAreaGu(areaGu);
        requestWastebasket.setAreaDong(areaDong);
        requestWastebasket.setLatitude(latitude);
        requestWastebasket.setLongitude(longitude);

        wasteBasketService.setWastebasketOne(requestWastebasket, files);

        return ResponseEntity
                .ok()
                .body(new ResponseContent(StatusCode.SUCCESS));
    }


    /**
     * 쓰레기통 삭제
     * 댓글이 달리지 않은 쓰레기통만 삭제
     * pin을 등록한 유저만 삭제가능
     */
    @DeleteMapping(value = "/{id}", consumes = "application/json; charset=UTF-8", produces = "application/json; charset=UTF-8", headers = "X-AUTH-TOKEN")
    @ApiOperation(value = "쓰레기통 삭제")
    public ResponseEntity<ResponseContent> delWastebasket(@PathVariable Long id
                                                            ,Principal principal) {

        User user = userService.getUserByEmail(principal.getName());
        wasteBasketService.delWastebasket(id, user);

        return ResponseEntity
                .ok()
                .body(new ResponseContent(StatusCode.SUCCESS));
    }

    /**
     * 쓰레기통 댓글 등록
     */

    /**
     * 쓰레기통 댓글 수정
     */

    /**
     * 쓰레기통 댓글 삭제
     */



}
