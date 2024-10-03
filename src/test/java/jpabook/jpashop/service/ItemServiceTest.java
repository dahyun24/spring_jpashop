package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.domain.item.Book; // 예시로 사용할 Book 엔티티
import jpabook.jpashop.repository.ItemRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ItemServiceTest {

    @Autowired
    ItemService itemService;

    @Autowired
    ItemRepository itemRepository;

    @Test
    public void 상품저장_테스트() throws Exception {
        // given
        Item item = new Book();  // Book 엔티티 사용 (Item의 자식 클래스)
        item.setName("Test Book");
        item.setPrice(10000);
        item.setStockQuantitiy(10);

        // when
        itemService.join(item);

        // then
        Item foundItem = itemRepository.findOne(item.getId());
        assertEquals("저장된 상품 이름이 같아야 한다.", "Test Book", foundItem.getName());
        assertEquals("저장된 상품 가격이 같아야 한다.", 10000, foundItem.getPrice());
        assertEquals("저장된 상품 재고 수량이 같아야 한다.", 10, foundItem.getStockQuantitiy());
    }

    @Test
    public void 상품조회_테스트() throws Exception {
        // given
        Item item = new Book();
        item.setName("Test Book 2");
        item.setPrice(20000);
        item.setStockQuantitiy(5);
        itemService.join(item);

        // when
        Item foundItem = itemService.findOne(item.getId());

        // then
        assertNotNull("상품이 조회되어야 한다.", foundItem);
        assertEquals("상품 이름이 같아야 한다.", "Test Book 2", foundItem.getName());
    }
}
