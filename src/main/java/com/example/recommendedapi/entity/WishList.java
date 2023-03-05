package com.example.recommendedapi.entity;


import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
public class WishList {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;                //id
    private String title;           //장소
    private String category;        //카테고리
    private String address;         //주소
    private String readAddress;     //도로명
    private String homePageLink;    //홈페이지 주소
    private String imageLink;       //음식, 가게이미지 주소
    private boolean isVisit;        //방문 여부
    private int visitCount;         // 방문 카운트
    private LocalDateTime lastVisitDate;    //마지막 방문 일자

}
