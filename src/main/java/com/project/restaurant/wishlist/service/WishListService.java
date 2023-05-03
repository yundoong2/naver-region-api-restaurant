package com.project.restaurant.wishlist.service;

import com.project.restaurant.naver.NaverClient;
import com.project.restaurant.naver.dto.SearchImageRequest;
import com.project.restaurant.naver.dto.SearchLocalRequest;
import com.project.restaurant.wishlist.dto.WishListDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class WishListService {
    private final NaverClient naverClient;

    public WishListDto search(String query) {

        log.info("query = {}", query);

        // 지역 검색
        var searchLocalRequest = new SearchLocalRequest();
        searchLocalRequest.setQuery(query);

        var searchLocalResponse = naverClient.searchLocal(searchLocalRequest);
        if (searchLocalResponse.getTotal() > 0) {
            var localItem = searchLocalResponse.getItems().stream().findFirst().get();

            // 이미지 검색
            var imageQuery = localItem.getTitle().replaceAll("<[^>]*>", "");
            var searchImageRequest = new SearchImageRequest();
            searchImageRequest.setQuery(imageQuery);

            var searchImageResponse = naverClient.searchImage(searchImageRequest);

            if (searchImageResponse.getTotal() > 0) {
                var imageItem = searchImageResponse.getItems().stream().findFirst().get();

                //결과 리턴
                WishListDto result = new WishListDto();
                result.setTitle(localItem.getTitle());
                result.setCategory(localItem.getCategory());
                result.setAddress(localItem.getAddress());
                result.setRoadAddress(localItem.getRoadAddress());
                result.setHomePageLink(localItem.getLink());
                result.setImageLink(imageItem.getLink());

                return result;
            }
        }
        return new WishListDto();
    }


}
