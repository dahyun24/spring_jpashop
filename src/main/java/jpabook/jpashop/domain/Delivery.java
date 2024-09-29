package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery")
    private Order order;

    @Embedded
    private Address address;

    // ENUM 타입에서 주의 사항
    // Enumerated 어노테이션을 넣어야함, eunm type은 ordinal(= column이 1,2,3 숫자로 들어간다.)과 string이 존재
    // ordinal은 다른 상태가 들어오면 망함,,, 밀리기 때문, 따라서 절대 쓰면 안된다.
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status; // [READY, COMP]


}
