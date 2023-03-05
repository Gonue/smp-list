package com.example.recommendedapi.controller;


import com.example.recommendedapi.dto.WishListDto;
import com.example.recommendedapi.service.WishListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.security.PublicKey;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/wish")
@RequiredArgsConstructor
public class ApiController {


    private final WishListService wishListService;

    @GetMapping("/search")
    public WishListDto search(@RequestParam String query){
        return wishListService.search(query);
    }

    @PostMapping
    public WishListDto add(@RequestBody WishListDto wishListDto){
        log.info("{}", wishListDto);
        return wishListService.add(wishListDto);
    }

    @GetMapping("/all")
    public List<WishListDto> findAll(){
        return wishListService.findAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        wishListService.delete(id);
    }

    @PostMapping("/{id}")
    public void addVisit(@PathVariable Long id){
        wishListService.addVisit(id);
    }
}
