## @Autowired
1) 자동 빈 주입을 위한 어노테이션
2) 해당 어노테이션이 붙어있는 **타입**을 우선으로 빈을 찾아 주입  

- 생성자 주입
```java
public class Member {
    
    @Autowired
    public Member(Sample sample) {
        this.sample = sample;
    //...
    }
}
```

- 세터 주입
```java
public class Member {
    
    @Autowired
    public setSample(Sample sample) {
        this.sampe = sample;
    }
}
```

- 필드 주입
```java
public class Member {
    
    @Autowired
    private Sample sample;
}
```

## @Qualifier
앞서 말했듯이 @Autowired는 **타입**으로 빈 객체를 찾아 주입한다고 했음  
그럼, 타입이 동일할 경우는 어떻게??


다음 상황의 경우를 보자
```java
public class AppCtx {
    
    @Bean
    public MemberPrinter memberPrinter() {
        return new MemberPrinter();
    }

    @Bean
    public MemberPrinter summaryPrinter() {
        return new MemberSummaryPrinter();
    }
}
```

동일 타입의 MemberPrinter인 경우

```java
public class MemberListPrinter {
    //...

    @Autowired
    public void setMemberPrinter(MemberPrinter printer) {
        this.printer = printer;
    }
}
```
다음과 같이 MemberPrinter타입을 주입받을 때,
~~~
org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'memberListPrinter': Unsatisfied dependency expressed through method 'setMemberPrinter' parameter 0; 
nested exception is org.springframework.beans.factory.NoUniqueBeanDefinitionException: No qualifying bean of type 'com.study.main.chapter04.spring.MemberPrinter'
available: expected single matching bean but found 2: memberPrinter,summaryPrinter
~~~

친절한 에러 메시지가 뜨는 것을 확인할 수 있음  
마지막문구를 보면 알 수 있듯이, MemberPrinter 타입의 빈이 2개가 존재해서 둘 중 어떤것을 주입해야할 지 모르는 상황

이럴때 사용하는 어노테이션이 **@Qualifier**임


1) AppCtx 클래스에서 @Qualifier를 붙여주고 이름을 등록
```java
public class AppCtx {
    
    @Bean
    @Qualifier("printer")   // 이름 등록
    public MemberPrinter memberPrinter() {
        return new MemberPrinter();
    }

    @Bean
    @Qualifier("summaryPrinter")    // 이름 등록
    public MemberPrinter summaryPrinter() {
        return new MemberSummaryPrinter();
    }
}
```

2) 주입받는 곳에도 @Qualifier를 붙여주고 이름을 등록
```java
public class MemberListPrinter {
    //...

    @Autowired
    @Qualifier("summaryPrinter")    //MemberSummaryPrinter 인스턴스를 주입하겠다는 의미
    public void setMemberPrinter(MemberPrinter printer) {
        this.printer = printer;
    }
}
```