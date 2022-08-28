# 10.1 스프링 MVC 핵심 구성 요소
![Spring MVC](https://velog.velcdn.com/images/idean3885/post/af165bda-7361-4c20-89e8-f8b78ce1160f/image.png)
1. 클라이언트(웹 브라우저)에서 서버(Spring) 으로 요청 전송
2. `DispatcherServlet` -> `HandlerMapping` 로 요청 처리할 컨트롤러 검색 요청
   2-2. `HandlerMapping` -> `DispatcherServlet` 로 요청 경로(URL) 에 맞는 컨트롤러 빈 객체 전달
3. `DispatcherServlet` -> `HandlerAdapter` 빈에게 컨트롤러 처리를 요청한다.
   4~5. `HandlerAdapter` -> `Controller` 메소드 실행하여 요청을 처리하고 결과를 응답받는다.
6. `HandlerAdapter` -> `DispatcherServlet` 로 요청 결과 리턴
7. `DispatcherServlet` -> `ViewResolver` 빈에게 요청 결과에 포함된 View 이름에 해당하는 객체를 찾아 응답한다.
   ***이 과정은 응답데이터가 ModelAndView 인 경우만 해당한다.***
8. 리턴된 `View` 객체에게 응답 결과 생성 요청
9. `View` 객체가 `JSP` 인 경우, JSP로 생성하여 최종 결과를 전달한다.

### 10.1.1 컨트롤러와 핸들러
- `DispatcherServlet` 은 클라이언트의 요청을 전달받는 창구 역할
- 컨트롤러를 찾아주는 역할은 `HandlerMapping` 빈이 수행한다.
- @Controller 외 다른 클래스를 통해 요청을 처리할 수도 있기 때문에 `Handler` 라는 범용적인 표현을 사용한다.
- 요청을 처리할 핸들러는 응답 결과를 `ModelAndView` 뿐만 아니라 다른 값(Json...) 으로도 변환해서 응답해야 하기 때문에 이 작업을 `HandlerAdapter` 에게 위임하여 처리한다.

# 10.2 DispatcherServlet과 스프링 컨테이너
- `DispatcherServlet` 은 전달받은 설정파일을 이용해 스프링 컨테이너를 생성한다.
- `HandlerMapping`, `HandlerAdapter`, `ViewResolver` 등 위 MVC 기능을 구현하는 빈을 스프링 컨테이너에서 구하기 때문에 설정파일에는 반드시 이러한 빈들의 정의가 포함되어야 한다.
```java
@Configuration
public class MvcConfig {

	@Bean
	public HandlerMapping handlerMapping() {
		RequestMappingHandlerMapping hm = new RequestMappingHandlerMapping();
		hm.setOrder(0);
		return hm;
	}

	@Bean
	public HandlerAdapter handlerAdapter() {
		RequestMappingHandlerAdapter ha = new RequestMappingHandlerAdapter();
		return ha;
	}

	@Bean
	public HandlerMapping simpleHandlerMapping() {
		SimpleUrlHandlerMapping hm = new SimpleUrlHandlerMapping();
		Map<String, Object> pathMap = new HashMap<>();
		pathMap.put("/**", defaultServletHandler());
		hm.setUrlMap(pathMap);
		return hm;
	}

	@Bean
	public HttpRequestHandler defaultServletHandler() {
		DefaultServletHttpRequestHandler handler = new DefaultServletHttpRequestHandler();
		return handler;
	}

	@Bean
	public HandlerAdapter requestHandlerAdapter() {
		HttpRequestHandlerAdapter ha = new HttpRequestHandlerAdapter();
		return ha;
	}

	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver vr = new InternalResourceViewResolver();
		vr.setPrefix("/WEB-INF/view/");
		vr.setSuffix(".jsp");
		return vr;
	}

}

@Configuration
public class ControllerConfig {

	@Bean
	public HelloController helloController() {
		return new HelloController();
	}

}
```


# 10.3 @Controller를 위한 HandlerMapping과 HandlerAdapter
- @EnableWebMvc 어노테이션을 통해 `HandlerMapping` 및 `HandlerAdapter` 빈을 자동으로 등록시킬 수 있다.
- `HandlerMapping` 빈의 구현체는 주로 `RequestMappingHandlerMapping` 클래스를 사용하여 요청을 처리할 컨트롤러를 찾아낸다.
- `HandlerAdapter` 빈의 구현체는 주로 `RequestMappingHandlerAdapter` 클래스를 사용하여 컨트롤러 메소드를 통해 요청을 처리하고 리턴 결과를 매핑시켜(ModelAndView...) 전달한다.
```java
@Controller
public class HelloController {

	@GetMapping("/hello") // 1. HandlerMapping -> 실행시킬 메소드 검색
	public String hello(Model model,
			@RequestParam(value = "name", required = false) String name) {
		model.addAttribute("greeting", "안녕하세요, " + name);
		return "hello";
	} // 2. HandlerAdapter 가 hello() 메소드를 수행하고 결과 값을 ModelAndView 객체로 변경하여 리턴한다.
}
```

# 10.4 WebMvcConfigurer 인터페이스와 설정
- @EnableWebMvc 어노테이션을 사용하면 @Controller 어노테이션을 붙인 컨트롤러를 위한 설정을 생성한다.(HandlerMapping, HandlerAdapter ...)
- 또한 위 어노테이션을 통해 WebMvcConfigurer 타입의 빈을 생성할 수 있으며, MVC 설정을 추가로 생성할 수 있다.
```java
@Configuration
@EnableWebMvc // 어노테이션 있어야 WebMvcConfigurer 인터페이스 구현가능
public class MvcConfig implements WebMvcConfigurer {

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		registry.jsp("/WEB-INF/view/", ".jsp");
	}

}
```
- 스프링 5버전부터 jdk1.8를 정식 지원함에 따라 인터페이스 메소드 별 default 설정을 통해 필요한 메서드만 구현가능하다.
```java
public interface WebMvcConfigurer {

	/**
	 * Help with configuring {@link HandlerMapping} path matching options such as
	 * whether to use parsed {@code PathPatterns} or String pattern matching
	 * with {@code PathMatcher}, whether to match trailing slashes, and more.
	 * @since 4.0.3
	 * @see PathMatchConfigurer
	 */
     // default 로 구현되므로 오버라이드하지 않아도 오류가 발생하지 않는다.
	default void configurePathMatch(PathMatchConfigurer configurer) {
	}
 /* 중략 */
```
- default 가 생김에 따라 인터페이스 구현체였던 WebMvcConfigurerAdapter 추상 클래스는 deprecated 되었다.
```java
/**
 * An implementation of {@link WebMvcConfigurer} with empty methods allowing
 * subclasses to override only the methods they're interested in.
 *
 * @author Rossen Stoyanchev
 * @since 3.1
 * @deprecated as of 5.0 {@link WebMvcConfigurer} has default methods (made
 * possible by a Java 8 baseline) and can be implemented directly without the
 * need for this adapter
 */
@Deprecated
public abstract class WebMvcConfigurerAdapter implements WebMvcConfigurer {
```

# 10.6 디폴트 핸들러와 HandlerMapping의 우선순위
- @EnableWebMvc 어노테이션의 경우, 컨트롤러를 통한 URL 만 처리할 수 있다.
- 따라서 정적 자원인 /css/bootstrap.css /index.html 등은 404 에러가 발생한다.
- WebMvcConfigurer.configureDefaultServletHandling() 메서드를 통해 이 문제를 해결할 수 있다.