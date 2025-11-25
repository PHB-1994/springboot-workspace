package edu.thejoeun.product.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    private int id;
    private String productName;
    private String productCode;
    private String category;
    private int price;
    private int stockQuantity;
    private String description;
    private String manufacturer;
    private String imageUrl;
    /*
    private boolean isActive;
    mysql 의 경우 boolean 타입 존재
    oracle 의 경우 boolean 타입 없으므로
    char 를 이용해서 'Y' 'N' 형태를 주로 사용

    만약 isActive 를 boolean 타입으로 제공한다면
    serviceImpl 에서
    isActive 가 'Y' 일 경우 boolean true 로 변환하여
    frontend 에 전달할 수 있으나,
    private String isActive 로 타입을 전달받아
    활용하는 것이 용이
     */
    private String isActive;
    private String createdAt;
    private String updatedAt;
}
