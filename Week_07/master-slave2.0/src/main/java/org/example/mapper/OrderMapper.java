package org.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.entity.OrderEntity;

@Mapper
public interface OrderMapper {

    int insert(OrderEntity orderEntity);

    OrderEntity selectById(Long orderEntity);

}
