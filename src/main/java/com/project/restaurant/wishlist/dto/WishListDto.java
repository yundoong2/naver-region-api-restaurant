package com.project.restaurant.wishlist.dto;

import com.project.restaurant.db.MemoryDbEntity;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class WishListDto {
    private int index;
    private String title;               //음식명, 장소명
    private String category;            //카테고리
    private String address;             //주소
    private String roadAddress;         //도로명
    private String homePageLink;        //홈페이지 주소
    private String imageLink;           //음식, 가게 이미지 주소
    private boolean isVisit;            //방문 여부
    private int visitCount;             //방문 카운트
    private LocalDateTime lastVisitDate; //마지막 방문 일자
}
