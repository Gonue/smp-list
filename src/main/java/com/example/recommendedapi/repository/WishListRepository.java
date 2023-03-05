package com.example.recommendedapi.repository;


import com.example.recommendedapi.entity.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
public interface WishListRepository extends JpaRepository<WishList, Long> {
}
