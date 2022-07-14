package com.study.kdy.chapter06.config;

import com.study.kdy.chapter06.bean.Client;
import com.study.kdy.chapter06.bean.Client2;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.TestInstance.Lifecycle;

@TestMethodOrder(MethodOrderer.MethodName.class)
@TestInstance(Lifecycle.PER_CLASS)
public class CustomContextTest {

    private AnnotationConfigApplicationContext ctx;

    @BeforeAll
    public void setup() {
        ctx = new AnnotationConfigApplicationContext(CustomContext.class);
    }

    @AfterAll
    public void close() {
        ctx.close();
    }

    @Order(1)
    @Test
    public void _1_빈초기화_라이프사이클() {
        var client = ctx.getBean(Client.class);

        assertThat(client).isNotNull();
    }

    @Order(2)
    @Test
    public void _2_빈_어노테이션_초기화_소멸() {
        var client2 = ctx.getBean(Client2.class);
        assertThat(client2).isNotNull();

        client2.send();
    }

}
