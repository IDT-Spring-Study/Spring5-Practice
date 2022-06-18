package chapter03;

import chapter03.config.MemberDaoComponent;
import chapter03.dto.RegisterRequestDto;
import chapter03.model.MemberDao;
import chapter03.service.MemberRegisterService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(MemberDaoComponent.class);

        MemberRegisterService memberRegisterService = new MemberRegisterService(
                ctx.getBean("memberDao", MemberDao.class)
        );

        RegisterRequestDto reqDto = new RegisterRequestDto();
        reqDto.setEmail("idean3885@naver.com");
        reqDto.setName("kim-dongyoung");

        Long memberId = memberRegisterService.regist(reqDto);
        System.out.println(memberId);
    }

}
