package com.test;

import com.test.start.entity.Klass;
import com.test.start.entity.School;
import com.test.start.entity.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest(classes = {School.class,Student.class,Klass.class})
public class StartTest {

    @Autowired
    School school;

    @Test
    public void startTest() {
        school.ding();

        Klass klass = school.getClass1();
        Student student100 = school.getStudent100();
        klass.setStudents(Arrays.asList(student100));
        klass.getStudents();


    }
}
