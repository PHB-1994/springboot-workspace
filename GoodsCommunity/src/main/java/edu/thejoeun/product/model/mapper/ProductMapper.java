package edu.thejoeun.product.model.mapper;


import edu.thejoeun.product.model.dto.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductMapper {

    // 전체 상품 조회
    List<Product> getAllProducts();

    //상품 상세 조회
    Product getProductById(int id);

    // 상품 코드로 조회
    Product getProductByCode(String productCode);

    // 카테고리별 조회
    List<Product> getProductsByCategory(String category);

    // 상품명으로 검색
    List<Product> searchProducts(String keyword);
    /*
    수정 삭제도 동일
    int  = 등록데이터가 0부터 ~ n 개 까지 저장 확인 가능 다수의 상품 등록 사용
    void = 등록데이터의 유무만 확인 가능 성공 / 실패
     */
    // 상품 등록
    int insertProduct(Product product);

    // 상품 수정
    int updateProduct(Product product);

    // 상품 삭제
    int deleteProduct(int id);

    // 재고 업데이트
    int updateStock(int id, int quantity);
}