package com.test.bean;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class MainTest {
    public static void main(String[] args) {

        //  xml 装配
        ApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
        BeanXml beanXml = context.getBean(BeanXml.class);
        System.out.println(beanXml.toString());

        //  config 装配
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(BeanConfig.class);
        BeanC bean = applicationContext.getBean(BeanC.class);
        System.out.println(bean.toString());

        // 注解 装配
        AnnotationConfigApplicationContext annotationAppContext = new AnnotationConfigApplicationContext(BeanAnnotation.class);
        BeanAnnotation beanAnnotation = annotationAppContext.getBean(BeanAnnotation.class);
        System.out.println(beanAnnotation.toString());

    }
}
