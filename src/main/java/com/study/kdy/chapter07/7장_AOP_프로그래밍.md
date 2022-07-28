# 7.2 프록시와 AOP
- 프록시 객체란 핵심 기능 외 부가적인 기능을 제공하는 객체를 의미한다.
- 핵심 기능을 제공하는 객체 = 대상 객체
- `핵심 기능을 구현하지 않는 것이 특징`
```java
public class ExeTimeCalculator implements Calculator {
	private final Calculator delegate;

	public ExeTimeCalculator(Calculator delegate) {
        this.delegate = delegate;
    }

	@Override
	public long factorial(long num) {
		long start = System.nanoTime();
		long result = delegate.factorial(num); // 대상 객체에게 핵심 기능 실행 요청(메소드 실행)
		long end = System.nanoTime();
		System.out.printf("%s.factorial(%d) 실행 시간 = %d\n",
				delegate.getClass().getSimpleName(),
				num, (end - start));
		return result;
	}
}
```
```java
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ExeTimeCalculatorTest {
    @Order(1)
    @Test
    public void CalculatorImplTest() {
        var calculator = new ExeTimeCalculator(new CalculatorImpl());
        var result = calculator.factorial(20);
        System.out.println(result);
    }

    @Order(2)
    @Test
    public void RecCalculatorTest() {
        var calculator = new ExeTimeCalculator(new RecCalculator());
        var result = calculator.factorial(20);
        System.out.println(result);
    }
}
```
![프록시 예제](https://user-images.githubusercontent.com/43669379/181524432-19d4a9a2-edf4-425d-8992-54b92090bf9c.png)

### 7.2.1 AOP(Aspect Oriented Programming)
- 관점 지향 프로그래밍
- 여러 객체에 공통으로 적용할 기능을 프록시 객체로 분리해 관리하는 기법  
  `예) API Request/Response 정보 로깅, DB 트랜잭션 단위 설정, 요청 검증(Security)`
- 대상 객체(비즈니스 로직을 담당하는 객체, Service 등) 와 별도로 관리할 수 있어 재사용성을 높일 수 있다.
- 스프링 AOP 에서는 프록시 객체를 자동으로 생성한다.

**핵심 기능 적용 방법**

| 번호  | AOP 적용 시점          | 특이사항                              |
|-----|----------------------|-----------------------------------|
| 1   | 컴파일 전 소스에 삽입         | 스프링 AOP에서 지원 X, 코드에 공통 기능 삽입      |
| 2   | 클래스 로딩 시 바이트 코드에 삽입  | 스프링 AOP에서 지원 X, 코드에 공통 기능 삽입      |
| 3   | 런타임 시 객체를 생성하여 기능 수행 | 스프링 AOP에서 정식 지원, 프록시 객체로 공통 기능 수행 |

**AOP 주요 용어**

| 번호  | 용어        | 의미                                      | 예시                                                                             |
|-----|-----------|-----------------------------------------|--------------------------------------------------------------------------------|
| 1   | Advice    | 공통 기능을 적용할 시점                           | 대상 객체 메서드 호출 전, 후                                                              |
| 2   | Joinpoint | 대상 객체 메소드 정보를 담을 수 있는 인터페이스             | 보통 Advice 메소드의 인자로 사용                                                          |
| 3   | Pointcut  | Advice 가 어떤 Joinpoint 에 적용될 지 지정하는 표현식  | 정규식, AspectJ 문법으로 표현 <br/>execution(* com.study.kdy.chapter07..*Service.*(..)) |
| 4   | Aspect    | 공통으로 적용될 기능                             | @Aspect 표기된 객체                                                                 |
| 5   | weaving   | 공통 기능을 가지는 프록시 객체(@Aspect 객체) 가 생성되는 과정 | 비즈니스 객체 생성 <br/> &nbsp;&nbsp;&nbsp;-> Advice 적용 대상인 경우 해당 프록시 생성               |


# 7.3 스프링 AOP 구현
- 
```java

```

# 7.4 프록시 생성 방식
- 
```java

```