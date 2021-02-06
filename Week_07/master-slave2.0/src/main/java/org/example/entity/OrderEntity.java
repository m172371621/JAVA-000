package org.example.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderEntity implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 商品id
     */
    private Long goodId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 订单金额
     */
    private BigDecimal orderPrice;

    /**
     * 订单地址
     */
    private String orderAddress;

    /**
     * 下单时间
     */
    private Date orderTime;

    /**
     * 支付方式
     */
    private String payWay;

    /**
     * 支付时间
     */
    private Date payTime;

    /**
     * 订单状态
     */
    private String orderStatus;

    private static final long serialVersionUID = 1L;
}

