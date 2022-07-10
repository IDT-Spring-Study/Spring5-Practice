## 1. @Component 애노테이션으로 스캔 대상 지정

### 1) @Component
- 스프링 빈 등록 어노테이션
- 클래스에 명시해주면 됨
```java
@Component
public class MemberDao {
    //...
}
```

### 2) @Component 빈 이름
- 기본 지정 : 클래스명을 사용하되 맨 앞글자만 소문자
```java
@Component
public class MemberDao { // --> bean name : memberDao
    //...
}
```

- 직접 지정 : 빈 이름 직접 명시
```java
@Component("mdao")
public class MemberDao { // --> bean name : mdao
    //...
}
```


## 2. @ComponentScan 애노테이션으로 스캔 설정

### 1) @ComponentScan
- 설정 정보 없이 @Component가 붙어있는 모든 클래스를 스프링 빈으로 등록

```java
@ComponentScan(
 excludeFilters = @Filter(type = FilterType.ANNOTATION, classes =
Configuration.class))
public class AppConfig {

}
```

### 2) config 코드의 간소화

- 기존 @Configuration을 통한 빈 등록
```java
@Configuration
public class AppCtx {

	@Bean
	public MemberDao memberDao() {
		return new MemberDao();
	}
	
    //component 수정시 이름이 클래스명으로 변경되므로 관련 getBean 테스트코드 수정이 필요함
	@Bean
	public MemberRegisterService memberRegSvc() {
		return new MemberRegisterService();
	}
	
	@Bean
	public ChangePasswordService changePwdSvc() {
		return new ChangePasswordService();
	}
	
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
	public MemberListPrinter listPrinter() {
		return new MemberListPrinter();
	}
	
	@Bean
	public MemberInfoPrinter infoPrinter() {
		MemberInfoPrinter infoPrinter = new MemberInfoPrinter();
		return infoPrinter;
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
- @Component를 통한 빈 등록
```java
@Configuration
@ComponentScan(basePackages = {"spring"})
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

## 3. 탐색 위치 및 스캔 대상

~~~
@ComponentScan(basePackages = {"spring"})
~~~
- basePackages : 탐색할 패키지의 시작 위치를 지정한다. 이 패키지를 포함해서 하위 패키지를 모두 탐색

- 여러 시작 위치를 지정할 수 있음   
  (basePackages = {"com.study.a", "com.study.b"})

- 만약 지정하지 않으면 @ComponentScan이 붙은 설정 정보 클래스의 패키지가 시작 위치가 된다

- basePackageClasses : 지정한 클래스의 패키지를 탐색 시작 위치로 지정한다.

- 권장방식
    - 패키지 위치 지정 없이 설정 정보 클래스의 위츠를 프로젝트 패키지 최상단에 두는 것
    - 스프링부트(@SpringBootApplication)도 이 방식을 기본으로 제공

- ComponentScan 대상
    - @Component
    - @Controller
    - @Service
    - @Repository
    - @Configuration

## 4. 스캔 대상에서 제외하거나 포함하기

1) 필터
    - includeFilters : 컴포넌트 스캔 대상을 추가로 지정한다.
    - excludeFilters : 컴포넌트 스캔에서 제외할 대상을 지정한다.

2) includeFilters
    - @Component 면 충분하기 때문에 includeFilters 를 사용하는 경우는 거의 없음

3) excludeFilters
- FilterType
    - ANNOTATION: 기본값, 애노테이션을 인식해서 동작한다.  
      ex) org.example.SomeAnnotation
    - ASSIGNABLE_TYPE: 지정한 타입과 자식 타입을 인식해서 동작한다.  
      ex) org.example.SomeClass
    - ASPECTJ: AspectJ 패턴 사용  
      ex) org.example..*Service+
    - REGEX: 정규 표현식  
      ex) org\.example\.Default.*
    - CUSTOM: TypeFilter 이라는 인터페이스를 구현해서 처리

```java
public class AppCtxWithExcludeTest {

    @Test
    @DisplayName("config 등록 빈 조회")
    void findBeanByConfig() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppCtxWithExclude.class);

        Assertions.assertThrows(NoSuchBeanDefinitionException.class,
                () -> ac.getBean("memberDao", MemberDao.class));

    }

    @ComponentScan(basePackages = {"com.study.kts.chapter05.dao"},
            excludeFilters = {
                    @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = ManualBean.class)
            })
    static class AppCtxWithExclude {
    }
}
```

## 5. 컴포넌트 스캔에 따른 충돌

1) 빈 이름 충돌
    - spring, spring2 두 패키지 안의 동일한 이름의 MemberRegisterSerivce 클래스가 존재할 경우 충돌 발생

```java
public class AppCtxConflictTest {

    @Test
    @DisplayName("component 충돌")
    void getBean() {
        Assertions.assertThrows(BeanDefinitionStoreException.class,
                () -> new AnnotationConfigApplicationContext(AppConflictTxt.class));
    }

    @ComponentScan(basePackages = {"com.study.main.chapter05.spring", "com.study.main.chapter05.spring2"})
    static class AppConflictTxt {

    }
}
```

2) 수동 등록한 빈과 충돌
```java
@Component
public class MemberDao {

}
```
```java
@Configuration
public class AppCtx {
        @Bean
        public MemberDao memberDao() {
            return new MemberDao();
        }
    }
```
수동 빈 등록이 우선