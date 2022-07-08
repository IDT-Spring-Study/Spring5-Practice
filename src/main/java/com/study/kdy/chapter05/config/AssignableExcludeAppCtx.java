package com.study.kdy.chapter05.config;

import com.study.kdy.chapter05.service.ChangePasswordService;
import static org.springframework.context.annotation.ComponentScan.Filter;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@ComponentScan(
        basePackages = "com.study.kdy.chapter05",
        excludeFilters = @Filter(type = FilterType.ASSIGNABLE_TYPE, classes = ChangePasswordService.class)
)
public class AssignableExcludeAppCtx {
}
