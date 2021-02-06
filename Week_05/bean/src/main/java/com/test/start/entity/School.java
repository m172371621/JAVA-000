package com.test.start.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class School implements ISchool {
    @Autowired(required = true)
    Klass class1;

    @Autowired(required = true)
    Student student100;

    @Override
    public void ding() {
        System.out.println("Class1 have " + this.class1.getStudents().size() + " students and one is " + this.student100);
    }
}
