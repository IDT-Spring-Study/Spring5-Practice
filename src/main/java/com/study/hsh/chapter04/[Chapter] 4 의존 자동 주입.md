# [Chapter] 4 의존 자동 주입

## 1.@자동주입이란?

- 스프링 컨테이너에 있는 Bean객체를 타입에 맞게 스프링이 자동 주입함
- 자동주입을 위해 @Autowired와 @Resource 어노테이션이 존재

## @Autowired 애너테이션을 위한 의존 자동 주입

- 자동 주입 기능을 사용하면 스프링이 의존 객체를 찾아서 주입

### @Autowired의 필드 사용 예시

```java
@Bean
public MemberDao memberDao() {
	return new MemberDao();
}

@Bean
public ChangePasswordService changePwdSvc() {
	ChangePasswordService pwdSvc = new ChangePasswordService();
	pwdSvc.setMemberDao(memberDao());
	return pwdSvc;
}

```

Autowired 사용 시 Bean 메서드에서 의존을 주입하지 않아도 의존 객체가 주입

```java
    @Bean
    public MemberDao memberDao() {
        return new MemberDao();
    }
    
    @Bean
    public ChangePasswordService changePwdSvc() {
        ChangePasswordService pwdSvc = new ChangePasswordService();
        // pwdSvc.setMemberDao(memberDao());
        return pwdSvc;
    }

```

의존을 주입할 대상에 @Autowired 어노테이션을 붙임

```java
public class ChangePasswordService {

	@Autowired
	private MemberDao memberDao;

	public void changePassword(String email, String oldPwd, String newPwd) {
		Member member = memberDao.selectByEmail(email);
		if (member == null)
			throw new MemberNotFoundException();

		member.changePassword(oldPwd, newPwd);

		memberDao.update(member);
	}

	// never use
	/*public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
	}*/ 

}
```

### @Autowired의 메서드 사용

```java
public class MemberInfoPrinter {

	private MemberDao memDao;
	private MemberPrinter printer;

	public void printMemberInfo(String email) {
		Member member = memDao.selectByEmail(email);
		if (member == null) {
			System.out.println("데이터 없음\n");
			return;
		}
		printer.print(member);
		System.out.println();
	}

	@Autowired
	public void setMemberDao(MemberDao memberDao) {
		this.memDao = memberDao;
	}

	@Autowired
	@Qualifier("printer")
	public void setPrinter(MemberPrinter printer) {
		this.printer = printer;
	}

}
```

- setter로 의존성 주입하지 않아도 스프링이 찾아서 주입

```java
@Bean
public MemberDao memberDao() {
	return new MemberDao();
}

@Bean
public MemberDao memberPrinter() {
	return new MemberPrinter();
}

public MemberInfoPrinter infoPrinter() {
		MemberInfoPrinter infoPrinter = new MemberInfoPrinter();
		// infoPrinter.setMemberDao(memberDao()); 
		// infoPrinter.setPrinter(memberPrinter())
		return infoPrinter;
	}
```

## 3.일치하는 빈이 없는 경우 어떻게 될까???

- @Autowired 어노테이션 적용 대상에 일치하는 Bean이 없을 경우

![Untitled](%5BChapter%5D%204%20%E1%84%8B%E1%85%B4%E1%84%8C%E1%85%A9%E1%86%AB%20%E1%84%8C%E1%85%A1%E1%84%83%E1%85%A9%E1%86%BC%20%E1%84%8C%E1%85%AE%E1%84%8B%E1%85%B5%E1%86%B8%20aa82c7b1b55a4265a25c05de4fae6e5d/Untitled.png)

![Untitled](%5BChapter%5D%204%20%E1%84%8B%E1%85%B4%E1%84%8C%E1%85%A9%E1%86%AB%20%E1%84%8C%E1%85%A1%E1%84%83%E1%85%A9%E1%86%BC%20%E1%84%8C%E1%85%AE%E1%84%8B%E1%85%B5%E1%86%B8%20aa82c7b1b55a4265a25c05de4fae6e5d/Untitled%201.png)

- memberDao 필드에 대한 의존을 충족하지 않는다는 내용

## 만약 일치하는 빈이 2개 이상일 경우는???

![Untitled](%5BChapter%5D%204%20%E1%84%8B%E1%85%B4%E1%84%8C%E1%85%A9%E1%86%AB%20%E1%84%8C%E1%85%A1%E1%84%83%E1%85%A9%E1%86%BC%20%E1%84%8C%E1%85%AE%E1%84%8B%E1%85%B5%E1%86%B8%20aa82c7b1b55a4265a25c05de4fae6e5d/Untitled%202.png)

![Untitled](%5BChapter%5D%204%20%E1%84%8B%E1%85%B4%E1%84%8C%E1%85%A9%E1%86%AB%20%E1%84%8C%E1%85%A1%E1%84%83%E1%85%A9%E1%86%BC%20%E1%84%8C%E1%85%AE%E1%84%8B%E1%85%B5%E1%86%B8%20aa82c7b1b55a4265a25c05de4fae6e5d/Untitled%203.png)

- 타입의 빈을 한정할 수 없는데 해당 빈 타입이 한 개가 아니라 두 개의 빈을 발견

### 이럴 경우 @Qualifier 어노테이션을 사용

![Untitled](%5BChapter%5D%204%20%E1%84%8B%E1%85%B4%E1%84%8C%E1%85%A9%E1%86%AB%20%E1%84%8C%E1%85%A1%E1%84%83%E1%85%A9%E1%86%BC%20%E1%84%8C%E1%85%AE%E1%84%8B%E1%85%B5%E1%86%B8%20aa82c7b1b55a4265a25c05de4fae6e5d/Untitled%204.png)

![Untitled](%5BChapter%5D%204%20%E1%84%8B%E1%85%B4%E1%84%8C%E1%85%A9%E1%86%AB%20%E1%84%8C%E1%85%A1%E1%84%83%E1%85%A9%E1%86%BC%20%E1%84%8C%E1%85%AE%E1%84%8B%E1%85%B5%E1%86%B8%20aa82c7b1b55a4265a25c05de4fae6e5d/Untitled%205.png)

- 두 개일 경우 @Qualifier 어노테이션으로 printer를 준 MemberPrinter 타입의 빈을 자동 주입

## 4.상위/하위 타입 관계와 자동 주입

![Untitled](%5BChapter%5D%204%20%E1%84%8B%E1%85%B4%E1%84%8C%E1%85%A9%E1%86%AB%20%E1%84%8C%E1%85%A1%E1%84%83%E1%85%A9%E1%86%BC%20%E1%84%8C%E1%85%AE%E1%84%8B%E1%85%B5%E1%86%B8%20aa82c7b1b55a4265a25c05de4fae6e5d/Untitled%206.png)

- MemberSummaryPrinter 클래스가 MemberPrinter를 상속 받음

![Untitled](%5BChapter%5D%204%20%E1%84%8B%E1%85%B4%E1%84%8C%E1%85%A9%E1%86%AB%20%E1%84%8C%E1%85%A1%E1%84%83%E1%85%A9%E1%86%BC%20%E1%84%8C%E1%85%AE%E1%84%8B%E1%85%B5%E1%86%B8%20aa82c7b1b55a4265a25c05de4fae6e5d/Untitled%207.png)

- MemberPrinter를 주입할 때 에러 발생!!!!!

MemberSummaryPrinter 클래스는 MemberPrinter 타입에도 할당 할 수 있으므로 익셉션

MemberPrinter1 빈과 MemberPrinter2 빈 중에 어떤 걸 할당해야하지??

이럴 경우 @Qualifier 어노테이션으로 문제 해결 

![Untitled](%5BChapter%5D%204%20%E1%84%8B%E1%85%B4%E1%84%8C%E1%85%A9%E1%86%AB%20%E1%84%8C%E1%85%A1%E1%84%83%E1%85%A9%E1%86%BC%20%E1%84%8C%E1%85%AE%E1%84%8B%E1%85%B5%E1%86%B8%20aa82c7b1b55a4265a25c05de4fae6e5d/Untitled%208.png)

or MemberPrinter이 아닌 MemberSummaryPrinter 주입

```java
@Autowired
public void setMemberPrinter(MemberSummaryPrinter printer) {
	this.printer = printer;
	}
}
```

## 5.@Autowired 어노테이션의 필수 여부

```java
public class MemberPrinter {
	private DateTimeFormatter dateTimeFormatter;
	
	public MemberPrinter() {
		dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
	}
	
	public void print(Member member) {
		if (dateTimeFormatter == null) {
			System.out.printf(
					"회원 정보: 아이디=%d, 이메일=%s, 이름=%s, 등록일=%tF\n", 
					member.getId(), member.getEmail(),
					member.getName(), member.getRegisterDateTime());
		} else {
			System.out.printf(
					"회원 정보: 아이디=%d, 이메일=%s, 이름=%s, 등록일=%s\n", 
					member.getId(), member.getEmail(),
					member.getName(), 
					dateTimeFormatter.format(member.getRegisterDateTime()));
		}
	}
	
	@Autowired(required = false)
	public void setDateFormatter(DateTimeFormatter dateTimeFormatter) {
		this.dateTimeFormatter = dateTimeFormatter;
	}
}
```

- dateTimeFormatter 필드가 null일 경우와 null이 아닐 경우 로직처리
- @Autowired 어노테이션은 항상 required = true
- 앞선 코드처럼 자동 주입할 대상이 필수가 아닌 경우 @Autowired의 required의 값을 false 지정
- 매칭 되는 빈이 없어도 익셉션을 발생하지 않고 메서드 실행 x

```java
	@Autowired
	public void setDateFormatter(Optional<DateTimeFormatter> formatterOpt) {
		if (formatterOpt.isPresent()) {
			this.dateTimeFormatter = formatterOpt.get();
		} else {
			this.dateTimeFormatter = null;
		}
	}
	
	@Autowired
	public void setDateFormatter(@Nullable DateTimeFormatter dateTimeFormatter) {
		this.dateTimeFormatter = dateTimeFormatter;
	}
```

- @Nullable 사용 시 스프링 컨테이너는 의존 주입 대상이 존재하지 않으면 null값 전달
- Optional 타입은 매칭되는 빈이 없으면 값이 없는 Optional 값을 할당

## 6. 자동 주입과 명시적 의존 주입 간의 관계

```java
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
	public MemberInfoPrinter infoPrinter() {
		MemberInfoPrinter infoPrinter = new MemberInfoPrinter();
		infoPrinter.setPrinter(memberPrinter2());
		return infoPrinter;
	}
```

```java
public class MemberInfoPrinter {
...

@Autowired
@Qualifier("printer")
public void setPrinter(MemberPrinter printer) {
	this.printer = printer;
	}
}
```

- 설정 클래스에서 setter 메서드를 통해 의존을 주입해도  setter 메서드에 @Autowired 어노테이션이 붙어 있으면 자동 주입을 통해 일치하는 빈을 주입