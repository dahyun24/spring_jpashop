package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository //자동으로 spring bean에 의해 관리가 됨
// 마찬가지로 @RequiredArgsConstructor 를 사용하면 private final EntityManager em; 만으로 코드가 간편해진다.
public class MemberRepoistory {

    //JPA를 사용하기 떄문에,,,
    @PersistenceContext
    private EntityManager em;

    //entitiymanager factory를 주입하고 싶은 경우 PersistenceUnit을 어노테이션하면 된다.

    public void save(Member member) {
        em.persist(member); //jpa가 저장하는 logic
    }

    //member를 찾아서 반환, jpa에서 제공하는 find로
    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        // jpgl을 통해 전체 다 찾아옴
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    // 이름으로 회원조회하는 방법
    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name =: name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }

}
