package com.test.bean;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Data
@Component
public class BeanAnnotation {
    @Value("我是小可爱")
    private String nickName;


}
