package com.thejoeun.TheMall.controller;

import com.thejoeun.TheMall.model.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.thejoeun.TheMall.service.GoodsService;

import java.util.List;

@RestController
public class JSONController {

    @Autowired
    private GoodsService goodsService;

    @GetMapping("/goods/all")
    public List<Goods> getAllGoods(){
        return goodsService.getAllGoods();
    }
}
