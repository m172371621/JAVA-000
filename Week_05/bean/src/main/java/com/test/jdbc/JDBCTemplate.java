package com.test.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JDBCTemplate {

    /**
     * 查询列表
     *
     * @param sql
     * @param param
     * @return
     */
    public List<UserEntity> queryList(String sql, Object... param) {
        List<UserEntity> obj = new ArrayList<>();
        Connection connection = null;
        ResultSet rs = null;
        try {
//            connection = ConnectionUtils.getConnection();
            connection = HikariUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            setParams(preparedStatement, param);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                obj.add(setUser(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    /**
     * 查询信息
     *
     * @param sql
     * @param param
     * @return
     */
    public UserEntity queryOne(String sql, Object... param) {
        UserEntity obj = null;
        Connection connection = null;
        ResultSet rs = null;
        try {
//            connection = ConnectionUtils.getConnection();
            connection = HikariUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            setParams(preparedStatement, param);
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                obj = setUser(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    /**
     * 插入
     *
     * @param sql
     * @param param
     * @return
     */
    public boolean insert(String sql, Object... param) {
        Connection connection = null;
        boolean bl = false;
        try {
//            connection = ConnectionUtils.getConnection();
            connection = HikariUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            setParams(preparedStatement, param);
            bl = preparedStatement.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bl;
    }

    /**
     * 更新
     *
     * @param sql
     * @param param
     * @return
     */
    public boolean update(String sql, Object... param) {
        Connection connection = null;
        boolean bl = false;
        try {
//            connection = ConnectionUtils.getConnection();
            connection = HikariUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            setParams(preparedStatement, param);
            bl = preparedStatement.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bl;
    }

    /**
     * 更新
     *
     * @param sql
     * @param param
     * @return
     */
    public boolean delete(String sql, Object... param) {
        Connection connection = null;
        boolean bl = false;
        try {
//            connection = ConnectionUtils.getConnection();
            connection = HikariUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            setParams(preparedStatement, param);
            bl = preparedStatement.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bl;
    }

    /**
     * 设置对象
     *
     * @param rs
     * @return
     * @throws SQLException
     */
    public static UserEntity setUser(ResultSet rs) throws SQLException {
        UserEntity user = new UserEntity();
        user.setId(rs.getLong("id"));
        user.setName(rs.getString("name"));
        user.setAge(rs.getString("age"));
        user.setEmail(rs.getString("email"));
        return user;
    }

    /**
     * 设置参数
     *
     * @param statement
     * @param param
     */
    public static void setParams(PreparedStatement statement, Object... param) {
        try {
            for (int i = 0; i < param.length; i++) {
                statement.setObject(i + 1, param[i]);
            }
        } catch (Exception e) {
            System.out.println("设置参数错误");
            e.printStackTrace();
        }
    }
}
