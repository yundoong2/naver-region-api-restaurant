package com.project.restaurant.wishlist.service;

import com.project.restaurant.wishlist.dto.WishListDto;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
public class WishListServiceTest {

    @Autowired
    private WishListService wishListService;

    @Test
    void searchTest() {
        WishListDto result = wishListService.search("갈비집");
        log.info("result = {}", result);

        assertThat(result).isNotNull();
    }
}
