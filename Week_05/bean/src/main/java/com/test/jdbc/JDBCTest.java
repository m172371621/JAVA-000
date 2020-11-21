package com.test.jdbc;

import java.sql.Connection;
import java.util.List;

public class JDBCTest {

    public static void main(String[] args) {
        JDBCTemplate template = new JDBCTemplate();
        System.out.println("==========queryOne========");
        String sqlOne = "SELECT * FROM user WHERE id = ?";
        UserEntity o = template.queryOne(sqlOne, new Object[]{"2"});
        System.out.println(o.toString());

        System.out.println("==========querylist========");
        String sqlList = "SELECT * FROM user";
        List<UserEntity> list = template.queryList(sqlList);
        for (UserEntity user : list) {
            System.out.println(user.toString());
        }

        System.out.println("==========insert========");
        String insert = "INSERT INTO user(id,name,age,email) VALUES(?,?,?,?)";
        if (template.insert(insert, new Object[]{"99", "TEST", "100", "test@gmail.com"}))
            System.out.println("插入成功!");
        else
            System.out.println("插入失败!");

        System.out.println("==========update========");
        String update = "UPDATE user SET name=? WHERE id=?";
        if (template.update(update, new Object[]{"TEST99_4", "99"}))
            System.out.println("更新成功!");
        else
            System.out.println("更新失败!");

        System.out.println("==========delete========");
        String delete = "DELETE FROM user WHERE id=?";
        if (template.delete(delete, new Object[]{"99"}))
            System.out.println("删除成功!");
        else
            System.out.println("删除失败!");

    }
}
