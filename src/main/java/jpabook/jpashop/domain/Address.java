package jpabook.jpashop.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

// jpa의 내장 파일임을 알려주는, 어딘가에 내장이 될 수 있다.
@Embeddable

//값 타입은 변경 불가능하게 설계해야 한다. 따라서, Setter 없음
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;

    protected Address() {
    }

    // 생성할 때만 값이 세팅되고 Setter를 아예 제공하지 않는 방법을 사용
    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
