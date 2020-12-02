package org.insertbatch;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.Date;

/**
 * 批量插入订单表
 */
public class InsertBatch {

    public static void main(String[] args) {

        try {
            long start = System.currentTimeMillis();
            insertBatch(1000000);
            long end = System.currentTimeMillis();
            System.out.printf("insert 耗时:" + (end - start));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 插入
     *
     * @param count 数量
     */
    private static void insertBatch(int count) {
        String sql = "INSERT INTO t_order(good_id, user_id, order_price, order_address, order_time, pay_way, pay_time, order_status) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        Connection connection;
        PreparedStatement preparedStatement;
        try {
            connection = ConnectionUtils.getConnection();

            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(sql);

            for (int i = 0; i < count; i++) {
                preparedStatement.setInt(1, (int) (Math.random() * 99999));
                preparedStatement.setLong(2, (int) (Math.random() * 999));
                BigDecimal bigDecimal = BigDecimal.valueOf(Math.random() * 99);
                bigDecimal.setScale(2, RoundingMode.CEILING);
                preparedStatement.setBigDecimal(3, bigDecimal);
                preparedStatement.setString(4, "地址:" + Math.random() * 1000);
                preparedStatement.setTimestamp(5, Timestamp.valueOf("2020-12-02 09:00:00"));
                preparedStatement.setInt(6, (int) (Math.random() * 10));
                preparedStatement.setTimestamp(7, Timestamp.valueOf("2020-12-02 09:00:00"));
                preparedStatement.setInt(8, (int) (Math.random() * 2));
                preparedStatement.addBatch();
                if (i % 10000 == 0)
                    connection.commit();
            }
            preparedStatement.executeBatch();
            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try{
                preparedStatement.clearBatch();
                connection.close();
            }catch(Exception e){
                e.printStackTrace();
            }

        }
    }
}