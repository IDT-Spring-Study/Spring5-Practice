package com.study.kts.support;

import org.springframework.context.ApplicationContext;

public class PrintUtils {
    public static void printBeanInfo(ApplicationContext ac) {
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println("name = " + beanDefinitionName + ", object = "
                    + ac.getBean(beanDefinitionName));
        }
    }
}
