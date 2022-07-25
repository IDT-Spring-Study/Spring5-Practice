package com.study.kts.chapter06;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class Client implements InitializingBean, DisposableBean {

    private String host;

    public void setHost(String host) {
        this.host = host;
    }

    //초기화 콜백 (의존관계 주입이 끝나면 호출)
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("Client.afterPropertiesSet() 실행");
        send();
    }

    public void send() {
        System.out.println("Client.send() to " + host);
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("Client.destroy() 실행");
    }
}
