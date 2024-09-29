package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded // 딱 보고 내장 타입임을 알 수 있음
    private Address address;

    // 주인이 아닌 연관관계의 거울인 경우에 mappedBy를 사용
    @OneToMany(mappedBy = "member") // order 테이블에 있는 member라는 필드에 의해 매핑된 것임을 알려줌
    private List<Order> orders = new ArrayList<>();
}
