package com.study.kdy.chapter05.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import static org.springframework.context.annotation.ComponentScan.Filter;

@ComponentScan(
        basePackages = "com.study.kdy.chapter05",
        excludeFilters = @Filter(type = FilterType.ASPECTJ, pattern = "com.study.kdy.chapter05.service.*Service")
)
public class ExcludeAppCtx {
}
