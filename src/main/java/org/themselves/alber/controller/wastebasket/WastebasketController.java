package org.themselves.alber.controller.wastebasket;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.themselves.alber.config.response.ResponseContent;
import org.themselves.alber.config.response.StatusCode;
import org.themselves.alber.controller.wastebasket.dto.*;
import org.themselves.alber.domain.User;
import org.themselves.alber.domain.Wastebasket;
import org.themselves.alber.domain.WastebasketComment;
import org.themselves.alber.service.ImageService;
import org.themselves.alber.service.UserService;
import org.themselves.alber.service.WastebasketCommentService;
import org.themselves.alber.service.WastebasketService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/wastebasket")
@Api(description = "쓰레기통")
public class WastebasketController {

    private final ModelMapper modelMapper;

    private final WastebasketService wasteBasketService;

    private final ImageService imageService;

    private final WastebasketCommentService wastebasketCommentService;

    private final UserService userService;

    /**
     * 쓰레기통 등록
     * 쓰레기통과 이미지정보를 함께 등록
     * pin도 함께 등록
     */
    @PostMapping(consumes = "application/json; charset=UTF-8", produces = "application/json; charset=UTF-8", headers = "X-AUTH-TOKEN")
    @ApiOperation(value = "쓰레기통 등록")
    public ResponseEntity<ResponseContent> addWestebasket(@RequestBody @Valid WastebasketAddDto wastebasketAddDto, Principal principal) {

        Wastebasket wastebasket = modelMapper.map(wastebasketAddDto, Wastebasket.class);
        User user = userService.getUserByEmail(principal.getName());

        wasteBasketService.addWastebasket(wastebasket, user, wastebasketAddDto.getImageList());

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
    public ResponseEntity<ResponseContent<WastebasketResponseDto>> getWastebaseketOne(@PathVariable Long id) {

        Wastebasket wastebasket = wasteBasketService.getWastebasketOne(id);
        List<String> imageUrlList = imageService.getImageUrlList(wastebasket);

        WastebasketResponseDto wastebasketDto = modelMapper.map(wastebasket, WastebasketResponseDto.class);
        for (String imageUrl:imageUrlList) {
            wastebasketDto.getImageList().add(imageUrl);
        }

        for (WastebasketComment wastebasketComment : wastebasket.getWastebasketCommentList()) {
            WastebasketCommentResponseDto wastebasketCommentResponseDto = new WastebasketCommentResponseDto();
            wastebasketCommentResponseDto.setNickname(wastebasketComment.getUser().getNickname());
            wastebasketCommentResponseDto.setContents(wastebasketComment.getContents());
            wastebasketDto.getCommentDtoList().add(wastebasketCommentResponseDto);
        }

        return ResponseEntity
                .ok()
                .body(new ResponseContent<WastebasketResponseDto>(StatusCode.SUCCESS, wastebasketDto));
    }


    /**
     * 주변 500m에 있는 쓰레기통 보기
     * 위도 경도를 받아와야 함
     * 500m 계산 법? 위도 경도로부터 반지름 500m 이내 둘레의 쓰레기통 검색 로직
     * 간이 정보, 사진
     */

    /**
     * 쓰레기통 전체보기
     *
     */


    /**
     * 쓰레기통 수정
     * 이미지 수정가능
     * 본인이 작성하지 않은 댓글은 수정 불가
     * pin을 등록한 유저만이 수정가능
     */
    @PutMapping(value = "/{id}", consumes = "application/json; charset=UTF-8", produces = "application/json; charset=UTF-8", headers = "X-AUTH-TOKEN")
    @ApiOperation(value = "쓰레기통 수정")
    public ResponseEntity<ResponseContent> setWestebasketOne(@PathVariable Long id,
                                                             @RequestBody @Valid WastebasketUpdateDto wastebasketUpdateDto
                                                            , Principal principal) {

        Wastebasket wastebasket = modelMapper.map(wastebasketUpdateDto, Wastebasket.class);
        wastebasket.setId(id);
        User user = userService.getUserByEmail(principal.getName());

        wasteBasketService.setWastebasketOne(wastebasket, user, wastebasketUpdateDto.getImageList());

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
            , Principal principal) {

        User user = userService.getUserByEmail(principal.getName());
        wasteBasketService.delWastebasket(id, user);

        return ResponseEntity
                .ok()
                .body(new ResponseContent(StatusCode.SUCCESS));
    }

    /**
     * 쓰레기통 댓글 등록
     */
    @PostMapping(value = "/{id}/comment", consumes = "application/json; charset=UTF-8", produces = "application/json; charset=UTF-8", headers = "X-AUTH-TOKEN")
    @ApiOperation(value = "쓰레기통 댓글 등록")
    public ResponseEntity<ResponseContent> addWastebasketComment(@PathVariable Long id
            , Principal principal
            , @RequestBody @Valid WastebasketCommentRequestDto wastebasketCommentRequestDto) {

        User user = userService.getUserByEmail(principal.getName());
        Wastebasket wastebasket = wasteBasketService.getWastebasketOne(id);
        wastebasketCommentService.addWastebasketComment(wastebasket, user, wastebasketCommentRequestDto.getContents());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseContent(StatusCode.SUCCESS_CREATED));
    }


    /**
     * 쓰레기통 댓글 수정
     */
    @PutMapping(value = "/comment/{id}", consumes = "application/json; charset=UTF-8", produces = "application/json; charset=UTF-8", headers = "X-AUTH-TOKEN")
    @ApiOperation(value = "쓰레기통 댓글 수정")
    public ResponseEntity<ResponseContent> setWastebasketComment(@PathVariable Long id
            , Principal principal
            , @RequestBody @Valid WastebasketCommentRequestDto wastebasketCommentRequestDto) {

        User user = userService.getUserByEmail(principal.getName());
        wastebasketCommentService.setWastebasketComment(user, id, wastebasketCommentRequestDto.getContents());

        return ResponseEntity
                .ok()
                .body(new ResponseContent(StatusCode.SUCCESS));
    }

    /**
     * 쓰레기통 댓글 삭제
     */
    @DeleteMapping(value = "/comment/{id}", consumes = "application/json; charset=UTF-8", produces = "application/json; charset=UTF-8", headers = "X-AUTH-TOKEN")
    @ApiOperation(value = "쓰레기통 댓글 삭제")
    public ResponseEntity<ResponseContent> delWastebasketComment(@PathVariable Long id
            , Principal principal) {

        User user = userService.getUserByEmail(principal.getName());
        wastebasketCommentService.delWastebasketComment(user, id);

        return ResponseEntity
                .ok()
                .body(new ResponseContent(StatusCode.SUCCESS));
    }

    @GetMapping(value = "/myregist", produces = "application/json; charset=UTF-8", headers = "X-AUTH-TOKEN")
    @ApiOperation(value = "내가 등록한 쓰레기통")
    public ResponseEntity<ResponseContent<List<WastebasketForMyRegistWastebasketDto>>> delWastebasketComment(Principal principal) {
        User user = userService.getUserByEmail(principal.getName());

        List<WastebasketForMyRegistWastebasketDto> wastebasketDtoList = wasteBasketService.getPinByUserForMyRegist(user);

        return ResponseEntity
                .ok()
                .body(new ResponseContent<>(StatusCode.SUCCESS,wastebasketDtoList));
    }
}
