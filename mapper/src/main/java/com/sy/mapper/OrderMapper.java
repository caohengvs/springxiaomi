package com.sy.mapper;

import com.sy.pojo.Order;
import org.apache.ibatis.annotations.Param;

/**
 * ssssyy
 * 2019/9/26 10:11
 */
public interface OrderMapper {
    void add(Order order);

    void updateStatus(@Param("oid") String oid,@Param("status") String status);
}
