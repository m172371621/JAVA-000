package com.test.start;

import com.test.start.entity.Klass;
import com.test.start.entity.School;
import com.test.start.entity.Student;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
@EnableConfigurationProperties(TestConfigProperties.class)
@ConditionalOnProperty(prefix = "test", name="enabled", havingValue = "true")
public class TestAutoConfiguration {

    @Bean
    @ConditionalOnClass(School.class)
    @ConditionalOnMissingBean(School.class)
    @ConditionalOnProperty(prefix = "student",name = "enabled", havingValue = "true", matchIfMissing = true)
    public School school(Klass klass, Student student) {
        return new School(klass, student);
    }

    @Bean
    @ConditionalOnClass(Klass.class)
    @ConditionalOnMissingBean(Klass.class)
    @ConditionalOnProperty(prefix = "klass", name = "enabled", havingValue = "true", matchIfMissing = true)
    public Klass klass(Student student) {
        return new Klass(Collections.singletonList(student));
    }

    @Bean
    @ConditionalOnClass(Student.class)
    @ConditionalOnMissingBean(Student.class)
    @ConditionalOnProperty(prefix = "student", name = "enabled", havingValue = "true", matchIfMissing = true)
    public Student student(TestConfigProperties properties) {
        return new Student(properties.getId(), properties.getName());
    }

}
