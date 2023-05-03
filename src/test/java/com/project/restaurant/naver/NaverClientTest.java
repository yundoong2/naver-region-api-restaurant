package com.project.restaurant.naver;

import com.project.restaurant.naver.dto.SearchImageRequest;
import com.project.restaurant.naver.dto.SearchLocalRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class NaverClientTest {

    @Autowired
    private NaverClient naverClient;

    @Test
    void searchLocalTest() {
        var search = new SearchLocalRequest();
        search.setQuery("갈비집");

        var result = naverClient.searchLocal(search);
        log.info("result = {}", result);
    }

    @Test
    void searchImageTest() {
        var search = new SearchImageRequest();
        search.setQuery("갈비집");

        var result = naverClient.searchImage(search);
        log.info("result = {}", result);
    }
}
