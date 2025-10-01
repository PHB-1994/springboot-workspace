package com.thejoeun.TheMall.service;

import com.thejoeun.TheMall.mapper.GoodsMapper;
import com.thejoeun.TheMall.model.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    public List<Goods> getAllGoods(){
        return goodsMapper.getAllGoods();
    };
}
