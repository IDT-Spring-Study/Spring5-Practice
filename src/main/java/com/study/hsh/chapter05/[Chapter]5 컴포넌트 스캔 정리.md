# [Chapter]5 컴포넌트 스캔

## 컴포넌트 스캔이란?

- 자동 주입과 함께 사용하는 추가 기능
- 스프링이 직접 클래스를 검색해서 빈으로 등록해주는 기능

## 1.@Component 어노테이션으로 스캔 대상 지정

```java
package spring;

import java.util.HashMap;
import java.util.Map;

import com.study.main.chapter11.spring.Member;
import org.springframework.stereotype.Component;

import config.ManualBean;

@ManualBean
@Component
public class MemberDao {

    private static long nextId = 0;

    private Map<String, Member> map = new HashMap<>();

    public Member selectByEmail(String email) {
        return map.get(email);
    }
...생략
}
```

```java
package spring;

import com.study.main.chapter11.spring.MemberDao;
import org.springframework.stereotype.Component;

@Component("infoPrinter")
public class MemberInfoPrinter {

    private MemberDao memDao;
    private MemberPrinter printer;
```

## 2.@ComponentScan 어노테이션으로 스캔 설정

### @Component 어노테이션을 붙인 클래스를 스캔해 빈으로 등록하려면?

- 설정 클래스에 @Component 어노테이션을 적용해야 함

```java
package config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import spring.MemberPrinter;
import spring.MemberSummaryPrinter;
import spring.VersionPrinter;

@Configuration
@ComponentScan(basePackages = {"com/study/main/chapter11/spring"})
public class AppCtx {

    @Bean
    @Qualifier("printer")
    public MemberPrinter memberPrinter1() {
        return new MemberPrinter();
    }

    @Bean
    @Qualifier("summaryPrinter")
    public MemberSummaryPrinter memberPrinter2() {
        return new MemberSummaryPrinter();
    }

    @Bean
    public VersionPrinter versionPrinter() {
        VersionPrinter versionPrinter = new VersionPrinter();
        versionPrinter.setMajorVersion(5);
        versionPrinter.setMinorVersion(0);
        return versionPrinter;
    }
}
```

- 13행 @ComponentScan(basePackages = {"spring"})은 spring패키지와 그 하위 패키지를 스캔 대상에 적용한다라는 뜻

## 3.스캔 대상에서 제외하거나 포함하기

```java
@Configuration
@ComponentScan(basePackages = {"com/study/main/chapter11/spring"},
        excludeFilters = {
                @Filter(type = FilterType.REGEX, pattern = "spring\\..*Dao"))			
})

public class AppCtxWithExclude {
    @Bean
    public MemberDao memberDao() {
        return new MemberDao();
    }

    @Bean
    @Qualifier("printer")
    public MemberPrinter memberPrinter1() {
        return new MemberPrinter();
    }
```

```java
@Configuration
@ComponentScan(basePackages = {"com/study/main/chapter11/spring"},
        excludeFilters = {
                @Filter(type = FilterType.ASPECTJ, pattern = "spring\\..*Dao"))			
})

public class AppCtxWithExclude {
    @Bean
    public MemberDao memberDao() {
        return new MemberDao();
    }

    @Bean
    @Qualifier("printer")
    public MemberPrinter memberPrinter1() {
        return new MemberPrinter();
    }
```

```java
@Retention(RUNTIME)
@Target(TYPE)
public @interface NoProduct {
}

@Retention(RUNTIME)
@target(TYPE)
public @interface ManualBean {
}
```

```java

@Configuration
@ComponentScan(basePackages = {"com/study/main/chapter11/spring", "spring2"},
        excludeFilters = {
                @Filter(type = FilterType.ANNOTATION, classes = ManualBean.class)
        })
public class AppCtxWithExclude {
    @Bean
    public MemberDao memberDao() {
        return new MemberDao();
    }
```

## 4. 기본 스캔 대상

- 다음 어노테이션을 붙인 클래스가 컴포넌트 스캔 대상에 포함된다.
    
    @Component
    
    @Controller
    
    @Service
    
    @Repository
    
    @Aspect
    
    @Configuration
    
    ```java
    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @Component
    public @interface Controller {
    
    	@AliasFor(annotation = Component.class)
    	String value() default "";
    
    }
    ```
    
    ## 5 .컴포넌트 스캔에 따른 충돌 처리
    
    - 컴포넌트 스캔 시 크게 빈 이름 충돌과 수동 등록에 따른 충돌이 발생할 수 있다.
    - 이럴 경우 명시적으로 빈 이름을 지정해서 이름 충돌을 피해야 함

    ```java
    @Component
    public class MemberDao {
    ...
    }
    ```

    ```java
    @Configuration
    @componentScan(basePackages = {"com/study/main/chapter11/spring"})
    public class AppCtx {
    
    	@Bean
    	public MemberDao memberDao() {
    		MemberDao memberDao() {
    		return memberDao;
    }
    ```
    
    - 스캔할 때 사용하는 빈 이름과 수동 등록한 빈 이름이 같은 경우 수동 등록한 빈이 우선 됨
    - but 빈 이름을 달리 하면 @Qualifier 어노테이션으로 어떤 빈을 가져올지 선택해야 함