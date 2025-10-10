package edu.the.joeun.mapper;

import edu.the.joeun.model.Goods;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
/*
* Mapper 의 경우
* resource/mappers/ 폴더 아래에
* 각 회사 개발자들이 작성한 SQL 을  기반으로 움직일 명칭들을 작성
*
* mapper.xml 파일에서
* id 로 작성한 명칭과 class Mapper.java 에서 작성하는 기능 명칭이
* 일치해야지 xml 파일에서 작성한 SQL 구문이 동작
* */
@Mapper
public interface GoodsMapper {
    /*
    <select id="getAll" resultMap="goodsMap">
        SELECT * FROM Goods ORDER BY id DESC
    </select>
    */
    List<Goods> getAll(); // 모든 상품 조회 = List<객체 타입 문서명칭 설정>
    /*
    <select id="getGoodsById" parameterType="int" resultMap="goodsMap">
        SELECT * FROM Goods WHERE = #{id}
    </select>
    */

    /*
    <insert id="insertGoods" parameterType="edu.the.joeun.model.Goods">
        Insert Into Goods (name, price, stock) Values(#{name}, #{price}, #{stock})
    </insert>
    */
    void insertGoods(Goods goods); // 매개변수로 저장할 데이터가 담겨진 변수명칭 한 번에 전달
    // void         : 몇 개 저장되었는지 반환하지 않음
    // int - return : 총 몇 개의 상품이 저장되었는지 SQL 에서 반환
}
