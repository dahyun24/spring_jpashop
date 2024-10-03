package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepoistory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

// spring이랑 integration 후 test 진행, 즉 spring과 test 통합
@RunWith(SpringRunner.class)
@SpringBootTest // 스프링 부트 띄우고 테스트 (이게 없으면 @Autowired 다 실패)
@Transactional // 롤백 가능하도록
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepoistory memberRepoistory;
    @Test
    @Rollback(false) // insert문 등록 쿼리 볼 수 있음
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("kim");

        //when
        Long saveId = memberService.join(member);

        //then
        assertEquals(member, memberRepoistory.findOne(saveId));
    }

    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        //when
        memberService.join(member1);
        memberService.join(member2);

        //then
        fail("예외가 발생해야 한다.");
    }

}