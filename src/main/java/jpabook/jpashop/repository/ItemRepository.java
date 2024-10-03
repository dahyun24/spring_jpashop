package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item) {
        // item은 jpa에 저장하기 전까지 id 값이 없다.
        // id 값이 없다 = 새로 생성하는 객체 => 신규 등록
        if (item.getId() == null) {
            em.persist(item);
        } else { // 이미 DB에 등록된 것을 가져온것 => UPDATE와 비슷한 개념
            em.merge(item); //update 비슷한것
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}
