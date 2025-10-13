package edu.the.joeun.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
// @Entity -> JPA 가 아니기 때문에 사용하지 않음
public class Goods {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    // @Column(length=100) -> JPA 가 아니기 때문에 사용하지 않음
    private String name;
    private int price;
    private int stock;
    private String category;
}
