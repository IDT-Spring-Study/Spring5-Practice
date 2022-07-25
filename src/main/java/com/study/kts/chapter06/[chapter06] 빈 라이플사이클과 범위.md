## 1. 컨테이너 초기화와 종료
- 스프링 컨테이너는 초기화와 종료라는 라이프사이클은 갖는다

```java
public class Main {
	public static void main(String[] args) throws IOException {
		//1. 컨테이너 초기화 (빈 객체 생성, 의존 주입, 초기화)
        AnnotationConfigApplicationContext ctx =
				new AnnotationConfigApplicationContext(AppCtx.class);

        //2. 컨테이너 빈 사용
		Client client = ctx.getBean(Client.class);
		client.send();

        //3. 컨테이너 종료
		ctx.close();
	}

}
```

## 2. 스프링 빈 객체의 라이프사이클

- 스프링컨테이너는 빈 객체의 라이프사이클을 관리
- 객체 생성 -> 의존 설정 -> 초기화 -> 소멸
- 스프링 컨테이너는 모든 의존 설정이 완료되면 빈 객체의 초기화를 수행. 이때, 스프링은 빈 객체의 지정된 메서드를 호출
- 객체의 생성과 초기화를 분리하도록 한다
    - 생성자는 메모리를 할당해서 객체를 생성하는 책임을 가진다. 반면 초기화는
      이렇게 생성된 값들을 활용해서 외부 커넥션(db커넥션 등)을 연결하는 등 무거운 동작을 수행한다. 따라서, 생성자에서 무거운 초기화 작업을 함께 진행하는 것 보다 구분하여 작업하는 것이 더 유리할 수 있다


### - 빈 객체의 초기화와 소멸

1) 스프링 인터페이스
```java
public interface InitializingBean {
	// 초기화 콜백 (의존관계 주입이 끝나면 호출)
	void afterPropertiesSet() throws Exception;
}

public interface DisposableBean {
	// 소멸 전 콜백 (메모리 반납, 연결 종료와 같은 과정)
	void destroy() throws Exception;
}
```

2) 커스텀 메서드
    - 외부에서 제공받은 클래스(예 라이브러리)를 스프링 빈 객체를 설정하고 싶을 때 사용
    - @Bean 태그에서 속성값을 사용
    - 속성은 initMethod, destroyMethod로 지정
    - 속성값은 메서드 명이며 파라미터가 없어야함, 만약 파라미터가 존재하는 메서드일 경우 스프링 컨테이너는 익셉션 발생

```java
public class AppCtx {
    @Bean(initMethod = "connect", destroyMethod = "close")
    public Client2 client2() {
        Client2 client = new Client2();
        client.setHost("host");
        return client;
    }
}
```

## 3. 빈 객체의 생성과 관리 범위

```java
public @interface Scope {

    @AliasFor("scopeName")
    String value() default "";

    /**
     Specifies the name of the scope to use for the annotated component/bean.
     Defaults to an empty string ("") which implies SCOPE_SINGLETON.
     */
    @AliasFor("value")
    String scopeName() default "";
}

public interface ConfigurableBeanFactory extends HierarchicalBeanFactory, SingletonBeanRegistry {

    String SCOPE_SINGLETON = "singleton";

    String SCOPE_PROTOTYPE = "prototype";
}
```

1) default는 SCOPE_SINGLETON 즉, "singleton"임
2) 빈 범위를 prototype으로 지정할 경우 빈 객체를 구할 때마다 새로운 객체를 생성
```java
public class ClientCtxTest {
    @Test
    @DisplayName("@Scope prototype 테스트")
    void client2Test() {
        Client2 client = ac.getBean("client2", Client2.class);
        Client2 client2 = ac.getBean("client2", Client2.class);

        assertThat(client).isNotSameAs(client2);    //true
    }
}
```