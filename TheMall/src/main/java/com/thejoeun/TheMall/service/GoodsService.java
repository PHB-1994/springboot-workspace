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
    /*
    void 와 return 형태 모두 가능

    void = 등록/실패를 예외가 발생해야 확인 가능

    return = 성공 시 1의 값을 반환
             0 일 경우 실패인 것으로
             성공 실패에 대한 결과 유무를 소비자에게 전달할 수 있다.
    */
    public void insertGoods(Goods goods){
        goodsMapper.insertGoods(goods);
    }
}
