package com.sy.service;

import com.sy.pojo.Order;
import com.sy.pojo.OrderDetail;

import java.util.List;

/**
 * ssssyy
 * 2019/9/26 10:59
 */
public interface OrderService {
    void saveOrder(Order order, List<OrderDetail> orderDetails);

    void updateStatus(String oid, String status);
}
