package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV3;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.sql.SQLException;

/**
 * 트렌젝션 - @Transactional AOP
 */
@Slf4j
@RequiredArgsConstructor
public class MemberServiceV3_3 { // 계좌이체

//  private final TransactionTemplate txTemplate; @Transactional로 해당 DI 제거    
    private final MemberRepositoryV3 memberRepository;

    //@RequiredArgConstructor 어노테이션 등록시, 생성자 제거
    //PlatformTransactionManager transactionManager 파라미터 또한 @Transactional로 제거
//    public MemberServiceV3_3(MemberRepositoryV3 memberRepository) {
//        this.memberRepository = memberRepository;
//    }

    @Transactional
    public void accountTransfer(String fromId, String toId, int money) throws SQLException {
        bizLosic(fromId, toId, money);
    }

    private void bizLosic(String fromId, String toId, int money) throws SQLException {
        Member fromMember = memberRepository.findById( fromId);
        Member toMember = memberRepository.findById(toId);

        memberRepository.update(fromId, fromMember.getMoney() - money);
        validation(toMember); // Exception이 터지면 -> ("ex")로 이동, rollback이 되어야 한다.
        memberRepository.update(toId, toMember.getMoney() + money);
    }

    private static void validation(Member toMember) {
        if (toMember.getMemberId().equals("ex")) {
            throw new IllegalStateException("이체중 예외 발생");
        }
    }
}
