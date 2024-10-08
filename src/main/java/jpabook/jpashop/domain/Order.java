package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.*;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = LAZY) // member랑 서로 반대인 것
    @JoinColumn(name = "member_id") //FK가 member_id가 됨 => 연관관계의 주인
    private Member member;

    // 특정 엔티티를 영속 상태로 만들 경우, 연관된 엔티티도 함께 영속 상태로 만들고 싶은 경우 영속성 전이를 사용
    // 영속성 = 엔티티를 영구적으로 저장해주는 환경
    // Cascade를 통해 영속성 정의 설정, 관리 가능
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL) //order만 persist하면 orderItem까지 전파
    private List<OrderItem> orderItems = new ArrayList<>();

    //order를 보면서 delivery를 보는 경우가 많기에 order에 delivery_id의 FK를 둠
    @OneToOne(cascade = CascadeType.ALL, fetch = LAZY) //order에 저장할때 delivery도 같이
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate; // 주문시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // 주문상태 [ORDER, CANCEL]

    //==연관관계 메서드==//
    // 양방향인 경우, 양쪽 모두 관계를 메서드를 설정해주어야함 (실수 방지)
    // control 하는 쪽에 이 메서드를 넣어주는 것이 좋음
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }
    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }
    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    //==생성 메서드==//
    // 복잡한 비즈니스 로직을 하나의 생성 메서드에서 set set set으로 완결을 시킨다.
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    //==비스니스 로직==//
    /**
     * 주문 취소
     */
    public void cancel(){
        if (delivery.getStatus() == DeliveryStatus.COMP){
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }

        this.setStatus(OrderStatus.CANCEL);
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }

    //==조회 로직==//
    /**
     * 전체 주문 가격 조회
     */
    public int getTotalPrice(){
        /*
        for (OrderItem orderItem : orderItems){
            totalPrice += orderItem.getTotalPrice()
            }
        */
        return orderItems.stream().mapToInt(OrderItem::getTotalPrice).sum();
    }
}
