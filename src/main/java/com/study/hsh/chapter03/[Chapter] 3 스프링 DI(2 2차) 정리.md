# [Chapter] 3 스프링 DI(2/2차)

## 6.스프링의 DI 설정 + SOLID

- 스프링은 앞서 구현한 **조립기**와 유사한 기능 제공

조립기란?

- 객체를 생성하고 의존 객체를 주입해주는 클래스를 따로 작성하는 것

### DI 방식

1. 생성자 방식

생성자를 통해 의존 객체를 주입받아 필드에 할당

```java
public class MemberRegisterService {
	private MemberDao memberDao;

	//생성자를 통해 의존 객체를 주입받음
	public MemberRegisterService(MemberDao memberDao) {
		// 주입 받은 객체를 필드에 할당
		this.memberDao = memberDao;
}
public Long regist(RegisterRequest req) {
	// 주입 받은 의존 객체의 메서드를 사용
	Member member = memberDao.selectByEmail(req.getEmail());
	//...
	memberDao.insert(newMember);
	return newMember.getId();
	}
}
```

1. 세터 메서드 방식

생성자 외에 세터 메서드를 이용해서 주입

```java
public class MemberInfoPrinter {

	private MemberDao memDao;
	private MemberPrinter printer;

	public void printMemberInfo(String email) {
		Member member = memDao.selectByEmail(email);
		//...(중략)
	}

	public void setMemberDao(MemberDao memberDao) {
		this.memDao = memberDao;
	}

	public void setPrinter(MemberPrinter printer) {
		this.printer = printer;
	}
}
```

---

## 7 .@Configuration 클래스의 @Bean 설정과 싱글톤

  **만약 한 클래스에서 같은 객체를 여러번 생성해 주입한다면**

  **주입 받은 객체는 매번 다른 객체를 상속 받는 게 아닐까**🧐**?**

- 스프링 컨테이너가 생성한 Bean은 싱글톤 객체
- 스프링 컨테이너는 @Bean이 붙은 메서드에 대해 한 개의 객체만 생성
- because 스프링은 설정 클래스 그대로 사용 x but 상속한 새로운 설정 클래스를 만들어 사용

→매번 새로운 객체 생성 x 생성한 객체를 보관했다가 이후 동일한 객체를 리턴

---

## 8. 두 개 이상의 설정 파일 사용하기

-스프링 개발 시 적게는 수십 개 많게는 수백 여 개 이상의 Bean을 사용

→한 개의 클래스 파일에 설정하는 것보다 영역별로 설정 파일 나누면 관리 용이

**@Autowired 어노테이션**

- 스프링의 자동 주입 기능
- 해당 Bean을 찾아서 필드에 자동 할당

→ 클래스에 @Bean 메서드에서 의존 주입을 위한 코드 작성하지 않아도 됨

@Autowired 사용 전

```java

public class BookService {
	private BookRepository bookRepository;

	public BookService(BookRepository bookRepository){
		this.bookRepository = bookRepository;
	}
	//todo something...

}
```

```java
@Configuration
public class ApplicationConfig() {
	@Bean
  public BookRepository bookRepository(){
		return new BookRepository();
	}

	@Bean
  public BookService bookservice(){
		return new BookService(bookRepository());
	}	
} 
```

@Autowired 사용 

```java

public class BookService {
	private BookRepository bookRepository;
	
	@Autowired
	public BookService(BookRepository bookRepository){
		this.bookRepository = bookRepository;
	}
	//todo something...

}
```

**@Import 어노테이션**

ex) @import(AppConf2.class)

• AppConfig에서 AppConf2를 import하고 있으므로  스프링 컨테이너 생성 시 AppConfig 클래스만 사용하면 AppConf2 클래스의 설정도 함께 사용하여 초기화

- Import 어노테이션으로 지정한 AppConf2 설정 클래스도 함께 사용
- 스프링 컨테이너를 생성할 때 AppConf2 설정 클래스를 지정할 필요 x

---

## 9. getBean() 메서드 사용

getBean()메서드 이용 사용할 빈 객체 구하기

```java
//VersionPrinter versionPrinter = ctx.getBean("versionprinter", VersionPrinter.class)
```

해당 타입의 빈 객체가 한 개만 존재하면 Bean 이름을 지정하지 않고 타입만으로 빈을 구할 수 있음

```java
//VersionPrinter versionPrinter = ctx.getBean(VersionPrinter.class)
```

---

## 10. 주입 대상 객체를 모두 빈 객체로 설정해야 하나😢?

- 꼭 주입하는 객체를 Bean으로 등록하지 않아도 됨
- but 빈 등록 여부 차이는 스프링이 객체를 관리 해주냐 안 해주냐 차이
- Bean 등록을 안할 시 스프링 컨테이너에서 주입 객체를 구할 수 없음

```java
//MemberPrinter printer = ctx.getBean(MemberPrinter.class)
//MemberPrinter는 Bean으로 등록하지 않은 상태면 Exception 에러
```

getBean() 메서드로 구할 필요가 없다면 빈 객체로 등록 필요 x but 최근 추세는 빈 등록