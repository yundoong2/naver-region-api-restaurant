package com.project.restaurant.wishlist.repository;

import com.project.restaurant.wishlist.entity.WishListEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class WishListRepositoryTest {

    @Autowired
    private WishListRepository wishListRepository;

    private WishListEntity create() {
        var wishList = new WishListEntity();
        wishList.setTitle("title");
        wishList.setCategory("category");
        wishList.setAddress("address");
        wishList.setRoadAddress("roadAddress");
        wishList.setHomePageLink("");
        wishList.setImageLink("");
        wishList.setVisit(false);
        wishList.setVisitCount(0);
        wishList.setLastVisitDate(null);

        return wishList;
    }

    @Test
    void saveTest() {
        var wishListEntity = create();
        WishListEntity expected = wishListRepository.save(wishListEntity);

        assertThat(expected).isNotNull();
        assertThat(expected.getIndex()).isEqualTo(1);
    }

    @Test
    void updateTest() {
        var wishListEntity = create();
        WishListEntity expected = wishListRepository.save(wishListEntity);

        expected.setTitle("update title");
        wishListRepository.save(expected);

        assertThat(expected.getIndex()).isEqualTo(1);
        assertThat(expected.getTitle()).isEqualTo("update title");
        assertThat(wishListRepository.listAll().size()).isEqualTo(1);
    }

    @Test
    void findByIdTest() {
        var wishListEntity = create();
        wishListRepository.save(wishListEntity);

        var expected = wishListRepository.findById(1);

        assertThat(expected.isPresent()).isEqualTo(true);
        assertThat(expected.get().getIndex()).isEqualTo(1);
    }

    @Test
    void deleteTest() {
        var wishListEntity = create();
        wishListRepository.save(wishListEntity);

        wishListRepository.deleteById(1);

        int count = wishListRepository.listAll().size();

        assertThat(count).isEqualTo(0);
    }

    @Test
    void listAllTest() {
        var wishListEntity1 = create();
        wishListRepository.save(wishListEntity1);

        var wishListEntity2 = create();
        wishListRepository.save(wishListEntity2);

        int count = wishListRepository.listAll().size();

        assertThat(count).isEqualTo(2);
    }


}
