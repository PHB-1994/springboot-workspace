package edu.the.joeun.service;

import edu.the.joeun.mapper.GoodsMapper;
import edu.the.joeun.model.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*
* @Mapper 에서 xml 에 작성한 SQL 을 id 값으로 보유
*
* 보유한 SQL 을 각 기능에 맞게 호출해서 사용하고.
* SQL 에 데이터를 저장하거나, 조회하거나, 수정, 삭제와 같은
* 전반적인 모든 기능을 담당하는 공간
* mapper, controller, service 중에서 코드가 가장 긴 공간
* @Service 에서 전반적인 기능 로직 작성
* */
@Service
public class GoodsService {

    // new 생성자로 사용할 객체 인스턴스 생성을 대신 해주는 어노테이션
    @Autowired
    private GoodsMapper goodsMapper;

    /**
     * 모든 상품 정보를 조회
     *
     * @return SQL 에서 가져온 상품 목록(List<Goods>)을 반환하여
     *         controller api/endpoing 주소에서 반환된 상품 목록 조회 가능
     */
    public List<Goods> getAll(){
        return goodsMapper.getAll();
    }

    /**
     * 전달받은 상품 정보를 데이터베이스에 저장
     * 
     * @param goods 저장할 상품 정보(Goods 객체)
     *       
     * @param = 파라미터 = 매개변수로 Controller 에서 전달받은 데이터를 담고 있는 변수 명칭들 설정
     * 
     * void insertGoods(Goods goods)
     * void insertGoods(int id, String name, int price, int stock)
     *                 상품번호,   상품이름,   상품가격, 상품수량을 하나하나 전달받는다 설정할 수 있지만
     *                 하나하나 작성하기 번거롭기 때문에
     *                 Goods goods 명칭으로 사용
     */
    public void insertGoods(Goods goods){
        goodsMapper.insertGoods(goods);
    }

}
