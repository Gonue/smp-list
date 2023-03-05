package com.example.recommendedapi.service;

import com.example.recommendedapi.dto.WishListDto;
import com.example.recommendedapi.entity.WishList;
import com.example.recommendedapi.naver.NaverClient;
import com.example.recommendedapi.naver.dto.SearchImageReq;
import com.example.recommendedapi.naver.dto.SearchLocalReq;
import com.example.recommendedapi.repository.WishListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WishListService {

    private final NaverClient naverClient;
    private final WishListRepository wishListRepository;

    public WishListDto search(String query){

        //지역
        var searchLocalReq = new SearchLocalReq();
        searchLocalReq.setQuery(query);
        var searchLocalRes = naverClient.searchLocal(searchLocalReq);

        if(searchLocalRes.getTotal() > 0 ){
            var localItem = searchLocalRes.getItems().stream().findFirst().get();

            var imageQuery = localItem.getTitle().replaceAll("<[^>]*>", "");
            var searchImageReq = new SearchImageReq();
            searchImageReq.setQuery(imageQuery);
            //이미지 검색
            var searchImageRes = naverClient.searchImage(searchImageReq);
            if (searchImageRes.getTotal() > 0){
                var imageItem = searchImageRes.getItems().stream().findFirst().get();

                //결과 리턴
                var result = new WishListDto();
                result.setTitle(localItem.getTitle());
                result.setCategory(localItem.getCategory());
                result.setAddress(localItem.getAddress());
                result.setReadAddress(localItem.getAddress());
                result.setReadAddress(localItem.getRoadAddress());
                result.setHomePageLink(localItem.getLink());
                result.setImageLink(imageItem.getLink());

                return result;
            }
        }
        return new WishListDto();
    }

    public WishListDto add(WishListDto wishListDto) {
        var entity = dtoToEntity(wishListDto);
        var saveEntity = wishListRepository.save(entity);
        return entityToDto(saveEntity);
    }

    private WishList dtoToEntity(WishListDto wishListDto){
        var entity = new WishList();
        entity.setId(wishListDto.getId());
        entity.setTitle(wishListDto.getTitle());
        entity.setCategory(wishListDto.getCategory());
        entity.setAddress(wishListDto.getAddress());
        entity.setReadAddress(wishListDto.getReadAddress());
        entity.setHomePageLink(wishListDto.getHomePageLink());
        entity.setImageLink(wishListDto.getImageLink());
        entity.setVisit(wishListDto.isVisit());
        entity.setVisitCount(wishListDto.getVisitCount());
        entity.setLastVisitDate(wishListDto.getLastVisitDate());
        return entity;
    }
    private WishListDto entityToDto(WishList wishList){
        var dto = new WishListDto();
        dto.setId(wishList.getId());
        dto.setTitle(wishList.getTitle());
        dto.setCategory(wishList.getCategory());
        dto.setAddress(wishList.getAddress());
        dto.setReadAddress(wishList.getReadAddress());
        dto.setHomePageLink(wishList.getHomePageLink());
        dto.setImageLink(wishList.getImageLink());
        dto.setVisit(wishList.isVisit());
        dto.setVisitCount(wishList.getVisitCount());
        dto.setLastVisitDate(wishList.getLastVisitDate());
        return dto;
    }

    public List<WishListDto> findAll() {
        return wishListRepository.findAll()
                .stream()
                .map(it -> entityToDto(it))
                .collect(Collectors.toList());
    }

    public void delete(Long id) {
        wishListRepository.deleteById(id);
    }

    public void addVisit(Long id){
        var wishItem = wishListRepository.findById(id);
        if(wishItem.isPresent()){
            var item = wishItem.get();
            item.setVisit(true);
            item.setVisitCount(item.getVisitCount()+1);
            wishListRepository.save(item);
        }
    }
}
