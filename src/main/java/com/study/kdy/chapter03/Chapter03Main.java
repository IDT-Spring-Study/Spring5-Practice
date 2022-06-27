package com.study.kdy.chapter03;

import com.study.kdy.chapter03.config.MemberComponent;
import com.study.kdy.chapter03.dto.RegisterRequestDto;
import com.study.kdy.chapter03.exception.DuplicateMemberException;
import com.study.kdy.chapter03.exception.MemberNotFoundException;
import com.study.kdy.chapter03.exception.WrongIdPasswordException;
import com.study.kdy.chapter03.model.MemberDao;
import com.study.kdy.chapter03.service.ChangePasswordService;
import com.study.kdy.chapter03.service.MemberRegisterService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Chapter03Main {

    private static ApplicationContext ctx = null;

    public static void main(String[] args) throws IOException {
        ctx = new AnnotationConfigApplicationContext(MemberComponent.class);

        // 동일 객체 검증
        MemberRegisterService memberRegisterService = ctx.getBean(
                "memberRegisterService", MemberRegisterService.class
        );
        MemberDao memberDao = ctx.getBean(
                "memberDao", MemberDao.class
        );
        if (memberRegisterService.memberDao == memberDao) {
            System.out.println("두 객체 동일!");
            System.out.println("memberDao 주소: " + memberDao);
            System.out.println("memberRegisterService 주소: " + memberRegisterService.memberDao);
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.print("명령어를 입력하세요: ");
            String command = reader.readLine();

            if ("exit".equals(command)) {
                System.out.println("종료합니다.");
                break;
            }
            if (command.startsWith("new ")) {
                processNewCommand(command.split(" "));
                continue;
            }
            if (command.startsWith("change ")) {
                processChangeCommand(command.split(" "));
                continue;
            }

            printHelp();
        }
    }

    private static void processNewCommand(String[] args) {
        if (args == null || args.length != 5) {
            printHelp();
            return;
        }

        MemberRegisterService memberRegisterService = ctx.getBean(
                "memberRegisterService", MemberRegisterService.class
        );

        RegisterRequestDto reqDto = new RegisterRequestDto();
        reqDto.setEmail(args[1]);
        reqDto.setName(args[2]);
        reqDto.setPassword(args[3]);
        reqDto.setConfirmPassword(args[4]);

        if (!reqDto.isPasswordEqualToConfirmPassword()) {
            System.out.println("암호와 암호확인이 일치하지 않습니다.\n");
            return;
        }

        try {
            memberRegisterService.regist(reqDto);
            System.out.println("등록했습니다.");
        } catch (DuplicateMemberException e) {
            System.out.println(e.getMessage());
            System.out.println("이미 존재하는 이메일입니다.\n");
        }
    }

    private static void processChangeCommand(String[] args) {
        if (args == null || args.length != 4) {
            printHelp();
            return;
        }

        ChangePasswordService changePasswordService = ctx.getBean(
                "changePasswordService", ChangePasswordService.class
        );

        try {
            changePasswordService.changePassword(args[1], args[2], args[3]);
            System.out.println("암호를 변경했습니다.\n");
        } catch (MemberNotFoundException e) {
            System.out.println(e.getMessage());
            System.out.println("존재하지 않는 이메일입니다.\n");
        } catch (WrongIdPasswordException e) {
            System.out.println(e.getMessage());
            System.out.println("이메일과 암호가 일치하지 않습니다.");
        }
    }

    private static void printHelp() {
        System.out.println();
        System.out.println("잘못된 명령입니다. 아래 명령어 사용법을 확인하세요.");
        System.out.println("명령어 사용법: ");
        System.out.println("new 이메일 이름 암호 암호확인");
        System.out.println("change 이메일 현재비번 변경비번");
        System.out.println("exit");
        System.out.println();
    }
}
