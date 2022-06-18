package chapter02;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppContext.class);

        Greeter greeter = ctx.getBean("greeter", Greeter.class);

        System.out.println(greeter.greet("스프링 챕터2"));

        // 싱글톤 예제
        Greeter greeter1 = ctx.getBean("greeter", Greeter.class);
        Greeter greeter2 = ctx.getBean("greeter", Greeter.class);

        System.out.println("greeter1 == greeter2: " + (greeter1 == greeter2));
        ctx.close();
    }

}
