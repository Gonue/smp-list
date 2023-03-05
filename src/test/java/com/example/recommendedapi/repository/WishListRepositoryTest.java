package com.example.recommendedapi.repository;

import com.example.recommendedapi.entity.WishList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WishListRepositoryTest {
    @Autowired
    private WishListRepository wishListRepository;


    @Test
    public void saveTest(){
        var wishList = create();
        var expected = wishListRepository.save(wishList);

        Assertions.assertNotNull(expected);
        Assertions.assertEquals(1,expected.getId());
    }
    @Test

    public void findByIdTest(){
        var wishList = create();
        wishListRepository.save(wishList);
        var expected = wishListRepository.findById(1L);
        Assertions.assertEquals(true,expected.isPresent());
        Assertions.assertEquals(1,expected.get().getId());
    }

    @Test
    public void deleteTest(){
        var wishList = create();
        wishListRepository.save(wishList);

        wishListRepository.deleteById(1L);
        int count = wishListRepository.findAll().size();

        Assertions.assertEquals(0,count);
    }
    @Test
    public void listAllTest(){
        var wishList1 = create();
        wishListRepository.save(wishList1);
        var wishList2 = create();
        wishListRepository.save(wishList2);

        int count = wishListRepository.findAll().size();
        Assertions.assertEquals(2,count);
    }
    @Test
    void updateTest(){
        var wishList = create();
        var expected = wishListRepository.save(wishList);
        expected.setTitle("update test");
        var saveEntity = wishListRepository.save(expected);
        Assertions.assertEquals("update test", saveEntity.getTitle());
        Assertions.assertEquals(1,wishListRepository.findAll().size());

    }

    private WishList create(){
        var wishList = new WishList();
        wishList.setTitle("title");
        wishList.setCategory("category");
        wishList.setAddress("address");
        wishList.setReadAddress("readAddress");
        wishList.setHomePageLink(":");
        wishList.setImageLink("");
        wishList.setVisit(false);
        wishList.setVisitCount(0);
        wishList.setLastVisitDate(null);

        return wishList;
    }
}