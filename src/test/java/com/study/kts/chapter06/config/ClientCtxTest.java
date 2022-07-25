package com.study.kts.chapter06.config;

import com.study.kts.chapter06.Client;
import com.study.kts.chapter06.Client2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ClientCtxTest {

    private AnnotationConfigApplicationContext ac =
            new AnnotationConfigApplicationContext(ClientCtx.class);

    @Test
    @DisplayName("빈 초기화 및 소멸 콘솔로그 테스트 & 빈 객체 싱글톤 테스트")
    void clientTest() {
        Client client = ac.getBean("client", Client.class);
        Client client2 = ac.getBean("client", Client.class);

        assertThat(client).isSameAs(client2);
        ac.close();
    }

    @Test
    @DisplayName("@Scope prototype 테스트")
    void client2Test() {
        Client2 client = ac.getBean("client2", Client2.class);
        Client2 client2 = ac.getBean("client2", Client2.class);

        assertThat(client).isNotSameAs(client2);
    }
}
