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

**스프링에서 구현가능한 Advice 종류**

| 종류              | 설명                                             |
|-----------------|------------------------------------------------|
| @Before         | 대상 객체 메서드 호출 전 공통 기능 실행                        |
| @AfterReturning | 대상 객체 메서드가 예외없이 실행된 후 공통 기능 실행                 |
| @AfterThrowing  | 대상 객체 메서드 실행 중 예외 발생 시 공통기능 실행                 |
| @After          | 대상 객체 메서드 실행 후 공통 기능 실행(예외 여부 상관없음)            |
| @Around         | 대상 객체 메서드 실행 전/후/예외 발생 시 기능 실행(범용성이 좋아 주로 사용됨) |

# 7.3 스프링 AOP 구현
- 스프링 AOP는 아래와 같은 방식으로 구현된다.
1. Aspect 클래스 작성 -> `@Aspect`
2. 공통 기능이 적용될 PointCut 설정 -> `@PointCut(정규식 | AspectJ 표현식)`
3. 공통 기능을 구현한 메서드에 Advice 설정 -> `@Around(@PointCut 적용된 메서드)`
```java
@Aspect // 공통 기능 객체 설정
public class ExeTimeAspect {
    @Pointcut("execution(public * com.study.kdy.chapter07.service..*(..))") // 공통 기능 적용 범위 설정
    private void publicTarget() {
    }

    @Around("publicTarget()") // 공통 기능 적용방식(Advice) 설정
    public Object measure(ProceedingJoinPoint joinPoint) throws Throwable {
        var start = System.nanoTime();
        try {
            return joinPoint.proceed();
        } finally {
            var finish = System.nanoTime();
            var sig = joinPoint.getSignature();
            System.out.printf("%s.%s(%s) 실행시간: %d ns\n", joinPoint.getTarget().getClass().getSimpleName(),
                    sig.getName(), Arrays.toString(joinPoint.getArgs()), (finish - start));
        }
    }
}
```
```java
@Configuration
@EnableAspectJAutoProxy
public class AspectAppCtx {
    @Bean
    public ExeTimeAspect exeTimeAspect() { // 공통 기능 bean 등록
        return new ExeTimeAspect();
    }

    @Bean
    public Calculator calculator() { // 대상 객체 bean 등록
        return new RecCalculator();
    }
}
```
```java
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(PER_CLASS)
public class AspectAppCtxTest {
    private AnnotationConfigApplicationContext aspectAppCtx;

    @BeforeAll
    public void setup() {
        aspectAppCtx = new AnnotationConfigApplicationContext(AspectAppCtx.class);
    }

    @Order(1)
    @Test
    public void measureCalculateTime() {
        var calculator = aspectAppCtx.getBean(Calculator.class);

        var result = calculator.factorial(20); // Advice 대상 객체 메서드 실행
        System.out.println("calc result: " + result);
    }
}
```
![@Aroud 적용 테스트](https://user-images.githubusercontent.com/43669379/181808251-bc9e5cad-5251-46eb-b1e1-c4af776c8147.png)
# 7.4 프록시 생성 방식
- 빈 객체가 인터페이스를 상속받은 경우, 스프링 AOP는 기본적으로 인터페이스 객체로 프록시를 생성한다.
```java
@Configuration
@EnableAspectJAutoProxy
public class AspectAppCtx {
    /* 중략 */
    @Bean
    public RecCalculator calculator() { // 리턴타입을 RecCalulator 로 명시
        return new RecCalculator();
    }
}
```
```java
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(PER_CLASS)
public class AspectAppCtxTest {

    private AnnotationConfigApplicationContext aspectAppCtx;

    @BeforeAll
    public void setup() {
        aspectAppCtx = new AnnotationConfigApplicationContext(AspectAppCtx.class);
    }

    @Order(1)
    @Test
    public void measureCalculateTime() {
        var calculator = aspectAppCtx.getBean("calculator", RecCalculator.class);

        var result = calculator.factorial(20);
        System.out.println("calc result: " + result);
    }

}
```
![AOP 인터페이스 프록시 예제](https://user-images.githubusercontent.com/43669379/182030088-1a294a61-319e-4c2d-bc20-64ee46624f42.png)

- 인터페이스를 상속받은 클래스로 프록시를 생성하려는 경우, `proxyTargetClass = true` 로 설정하면 된다.

### 7.4.1 execution 명시자 표현식
- Aspect 를 적용할 위치를 지정하는 @Pointcut 에는 execution 명시자를 사용한다.  
`execution(수식어패턴? 리턴타입패턴 클래스이름패턴?메서드이름패턴(파라미터패턴))`
  1. 수식어
    - public, protected 등 java 의 접근제어자
    - 생략가능하며 스프링 AOP 는 public 메서드에만 Pointcut 을 설정할 수 있다.

### 7.4.2 Advice 적용 순서
- 동일한 대상 객체에 여러 Advice 를 적용시킬 수 있다.
- 적용 순서는 스프링/자바 버전에 따라 다르게 적용된다. 아래 예제는 동일한 코드임에도 스프링/자바 버전이 달라 ExeTimeAspect 가 먼저 적용된 경우
  ![스프링, 자바 버전에 따라 적용순서가 다른 AOP 예제_1](https://user-images.githubusercontent.com/43669379/182032155-c81577fb-18b0-4faa-967a-43ed3625ff9a.png)
- 순서를 고정하려는 경우, @Aspect 클래스에 @Order 어노테이션을 설정하여 순서를 변경할 수 있다.
```java
@Order(1) // 적용 순서: @Order 붙은 Aspect -> 그 외 Aspect(스프링/자바 버전 영향받음)
@Aspect
public class CacheAspect {

  private Map<Long, Object> cache = new HashMap<>();

  @Pointcut("execution(public * com.study.kdy.chapter07.service..*(long))")
  public void cacheTarget() {
  }
  
  /* 이하 생략 */
}
```
![스프링, 자바 버전에 따라 적용순서가 다른 AOP 예제_2](https://user-images.githubusercontent.com/43669379/182032108-263ffaf8-d349-410f-82f5-62a2787eeca3.png)

### 7.4.3 @Around 의 Pointcut 설정과 @Pointcut 재사용
- @Pointcut 메서드 없이 @Around 에서 pointcut 을 직접 설정할 수 있다.
```java
@Around("execution(public * com.study.kdy.chapter07.service..*(..))")
```
- 재사용성을 높이기 위해 @Pointcut 어노테이션으로 pointcut 메소드를 만들고 이를 다른 Advice 에서 재사용할 수 있다.
```java
@Aspect
public class ExeTimeAspect {
    /* 중략 */

    // pointcut 직접 설정
    @Around("execution(public * com.study.kdy.chapter07.service..*(..))")
    public Object pointCutExpressionDirect(ProceedingJoinPoint joinPoint) throws Throwable {
        var start = System.nanoTime();
        try {
            return joinPoint.proceed();
        } finally {
            var finish = System.nanoTime();
            var sig = joinPoint.getSignature();
            System.out.printf("[@Pointcut 생략]%s.%s(%s) 실행시간: %d ns\n", joinPoint.getTarget().getClass().getSimpleName(),
                    sig.getName(), Arrays.toString(joinPoint.getArgs()), (finish - start));
        }
    }

    @Around("CacheAspect.cacheTarget()") // 다른 Aspect 의 Pointcut
    public Object cacheAspectPointcut(ProceedingJoinPoint joinPoint) throws Throwable {
        var start = System.nanoTime();
        try {
            return joinPoint.proceed();
        } finally {
            var finish = System.nanoTime();
            var sig = joinPoint.getSignature();
            System.out.printf("[@CacheAspect 의 @Pointcut]%s.%s(%s) 실행시간: %d ns\n", joinPoint.getTarget().getClass().getSimpleName(),
                    sig.getName(), Arrays.toString(joinPoint.getArgs()), (finish - start));
        }
    }

}
```
![다른 클래스 @Pointcut 적용](https://user-images.githubusercontent.com/43669379/182032763-257188c6-567d-41df-8c22-d7a759b63937.png)