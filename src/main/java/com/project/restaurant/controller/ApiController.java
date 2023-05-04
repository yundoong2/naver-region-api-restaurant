package com.project.restaurant.controller;

import com.project.restaurant.wishlist.dto.WishListDto;
import com.project.restaurant.wishlist.service.WishListService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ApiController
 * <p>
 * 네이버 지역, 이미지 API를 활용한 맛집 검색 컨트롤러
 *
 * @author cyh68
 * @since 2023-05-03
 **/
@Slf4j
@RestController
@RequestMapping("/api/restaurant")
@RequiredArgsConstructor
@Tag(name = "네이버 맛집 API Controller", description = "네이버 지역, 이미지 검색 API를 활용한 맛집 리스트 Controller")
public class ApiController {

    private final WishListService wishListService;

    /**
     * 쿼리를 입력 받아 맛집 검색 수행 메소드
     *
     * @param query {@link String}
     * @return WishListDto {@link WishListDto}
     */
    @GetMapping("/search")
    @Operation(operationId = "search", summary = "검색 메소드", description = "query 검색 메소드")
    public WishListDto search(@RequestParam(value = "query") String query) {
        return wishListService.search(query);
    }

    /**
     * 검색한 맛집을 WishList에 추가하는 메소드
     *
     * @param wishListDto {@link WishListDto}
     * @return WishListDto {@link WishListDto}
     */
    @PostMapping("")
    public WishListDto add(@RequestBody WishListDto wishListDto) {
        log.info("wishListDto = {}", wishListDto);

        return wishListService.add(wishListDto);
    }

    /**
     * WishList에 등록한 맛집 리스트 조회
     *
     * @return List {@link List<WishListDto>}
     */
    @GetMapping("/all")
    public List<WishListDto> findAll() {
        return wishListService.findAll();
    }

    /**
     * WishList에서 특정 맛집 삭제
     * @param index {@link Integer}
     */
    @DeleteMapping("/{index}")
    public void delete(@PathVariable("index") Integer index) {
        wishListService.delete(index);
    }

    /**
     * WishList에서 특정 맛집 방문 횟수 추가
     * @param index
     * @return WishListDto {@link WishListDto}
     */
    @PostMapping("/{index}")
    public WishListDto addVisit(@PathVariable("index") Integer index) {
        return wishListService.addVisit(index);
    }
}
