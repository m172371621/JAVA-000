package com.test.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public BeanC beanC() {
        return new BeanC("我是谁");
    }
}



