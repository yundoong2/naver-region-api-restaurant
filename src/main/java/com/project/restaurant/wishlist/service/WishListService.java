package com.project.restaurant.wishlist.service;

import com.project.restaurant.naver.NaverClient;
import com.project.restaurant.naver.dto.SearchImageRequest;
import com.project.restaurant.naver.dto.SearchLocalRequest;
import com.project.restaurant.wishlist.dto.WishListDto;
import com.project.restaurant.wishlist.entity.WishListEntity;
import com.project.restaurant.wishlist.repository.WishListRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class WishListService {
    private final NaverClient naverClient;
    private final WishListRepository wishListRepository;

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
                result.setTitle(localItem.getTitle().replaceAll("<[^>]*>", ""));
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


    public WishListDto add(WishListDto wishListDto) {
        var entity = dtoToEntity(wishListDto);
        log.info("entity = {}", entity);

        return entityToDto(wishListRepository.save(entity));
    }


    public List<WishListDto> findAll() {
        return wishListRepository.findAll()
                .stream().map(result -> entityToDto(result))
                .collect(Collectors.toList());
    }

    public void delete(Integer index) {
        wishListRepository.deleteById(index);
    }

    public WishListDto addVisit(Integer index) {
        Optional<WishListEntity> wishItem = wishListRepository.findById(index);

        if (wishItem.isPresent()) {
            WishListEntity item = wishItem.get();
            item.setVisit(true);
            item.setVisitCount(item.getVisitCount() + 1);

            return entityToDto(item);
        }
        return null;
    }

    private WishListEntity dtoToEntity(WishListDto wishListDto) {

        WishListEntity entity = new WishListEntity();

        entity.setIndex(wishListDto.getIndex());
        entity = entity.builder()
                .title(wishListDto.getTitle())
                .category(wishListDto.getCategory())
                .address(wishListDto.getAddress())
                .roadAddress(wishListDto.getRoadAddress())
                .homePageLink(wishListDto.getHomePageLink())
                .imageLink(wishListDto.getImageLink())
                .visitCount(wishListDto.getVisitCount())
                .isVisit(wishListDto.isVisit())
                .lastVisitDate(wishListDto.getLastVisitDate())
                .build();

        return entity;
    }

    private WishListDto entityToDto(WishListEntity wishListEntity) {
        return WishListDto.builder()
                .index(wishListEntity.getIndex())
                .title(wishListEntity.getTitle())
                .category(wishListEntity.getCategory())
                .address(wishListEntity.getAddress())
                .roadAddress(wishListEntity.getRoadAddress())
                .homePageLink(wishListEntity.getHomePageLink())
                .imageLink(wishListEntity.getImageLink())
                .visitCount(wishListEntity.getVisitCount())
                .isVisit(wishListEntity.isVisit())
                .lastVisitDate(wishListEntity.getLastVisitDate())
                .build();
    }
}
