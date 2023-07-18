package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memoryMemberRepository;
    @BeforeEach
    public void beforeEach(){
        // 이렇게 하면 같은 메모리의 리파지토리가 사용이 되겠죠
        // 이런것을 DI(Dependency Injection) 라고 한다.
        memoryMemberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memoryMemberRepository);
    }

    @AfterEach
    public void afterEach(){
        memoryMemberRepository.clearStore();
    }

    @Test
    void join() {
        // 뭔가가 주어졌는데 이거를 실행했을 때 결과가 이게 나와야한다.
        //given
        Member member = new Member();
        member.setName("hello");

        //when
        Long saveId = memberService.join(member);
        //then

        Member findMember = memberService.findOne(saveId).get();
        assertEquals(member.getName(),findMember.getName());

    }

    @Test
    void 중복_회원_예제(){
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다");

       /* try {
            memberService.join(member2);
            fail();
        }catch (IllegalStateException e){
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다");
        }
        */

    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}