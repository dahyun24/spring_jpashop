package jpabook.jpashop.domain;

import jakarta.persistence.*;
import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Category {
    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    // 카테고리도 list로 item을 갖고, item도 list로 category를 갖는다.
    @ManyToMany
    // 관계형 DB는 Collection 관계를 양쪽에 갖을 수 없다. 1:다, 다:1로 풀어낼 수 있는 테이블이 필요하다.
    @JoinTable(name = "category_item", // 중간 테이블 매핑 필요
            joinColumns = @JoinColumn(name = "category_id"), // 중간 table에 있는 category_id
            inverseJoinColumns = @JoinColumn(name = "item_id"))
    private List<Item> items = new ArrayList<>();

    // 다른 entity 매핑하는 것 처럼 하면 된다.
    // category = 계층구조,,, 어떻게 구현?
    // 내 부모 manytoone,, 반대로 내 자식은 여러가지 category를 가질 수 있음 onetomany
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

}
