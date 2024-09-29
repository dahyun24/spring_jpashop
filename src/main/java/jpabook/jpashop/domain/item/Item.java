package jpabook.jpashop.domain.item;

import jakarta.persistence.*;
import jpabook.jpashop.domain.Category;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
// 상속 관계 전략을 부모 class에 적어줘야 한다. (single table 전략)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype") // book이면 어떻게 할건지 등을 알려주는
@Getter @Setter
// 추상 클래스 구현
public abstract class Item {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantitiy;

    // 상속관계 매핑!!
    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

}