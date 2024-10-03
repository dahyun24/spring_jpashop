package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepoistory;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// component 스캔의 대상이 자동으로 된다.
@Service
// jpa 상에서 데이터 변경은 모두 transactional 안에서 이루어져야한다.
// 그러면 public 함수에는 기본적으로 transactional이 걸려 들어간다.
// transactional annotaion은 두개가 있는데 spring에서 제공하는 것이 좋음
@Transactional(readOnly = true) // DB한테 읽기 전용 transaction임을 알려줌 + 조회 성능 최적화 가능
// 필드 모든 걸 갖고 생성자를 만들어주는 롬복의 기능 => @AllArgsConstructor
// final을 갖고 있는 것을 대상으로 생성자를 만들어준다.
@RequiredArgsConstructor
public class MemberService {

    // 변경할 일이 없기에 final로!! 컴파일 시점에 check 가능
    private final MemberRepoistory memberRepoistory;
    /*
    @Autowired // spring bean에 들어있는 repository를 injection 되도록 해준다.
    // 생성자 injection을 쓰면 한번에 생성될 때 중간에 받을 일 없음 + autowired 없어도 된다.
    public MemberService(MemberRepoistory memberRepoistory) {
        this.memberRepoistory = memberRepoistory;
    }
    */

    // 회원 가입
    @Transactional // 쓰기의 경우는 따로 설정해놓으면 이게 우선권을 갖는다.
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepoistory.save(member);
        return member.getId();
    }

    // 동시에 같은 이름으로 회원 가입을 할 수 있으므로 가능하면 DB에서 unigue 설정을 해주는 것이 best
    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepoistory.findByName(member.getName());
        // EXCEPTION (혹은 개수를 세서 0보다 크면 예외를 던지도록 최적화 가능)
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다. Duplicate member name: " + member.getName());
        }
    }

    // 회원 전체 조회
    public List<Member> findMembers(){
        return memberRepoistory.findAll();
    }

    // 회원 한 명 조회
    public Member findOne(Long memberId){
        return memberRepoistory.findOne(memberId);
    }
}
