package com.sy.service.impl;

import com.sy.mapper.CartMapper;
import com.sy.mapper.OrderDetailMapper;
import com.sy.mapper.OrderMapper;
import com.sy.pojo.Order;
import com.sy.pojo.OrderDetail;
import com.sy.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ssssyy
 * 2019/9/26 11:00
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private CartMapper cartMapper;

    //切入 事务
    @Required
    @Override
    public void saveOrder(Order order, List<OrderDetail> orderDetails) {
        try {
//      开启事务
//      添加订单
            orderMapper.add(order);
//      添加数据
            for (OrderDetail orderDetail : orderDetails) {
                orderDetailMapper.add(orderDetail);
            }
//      清空购物车
            cartMapper.deleteByUid(order.getUid());
//      事务提交
        } catch (Exception e) {
            e.printStackTrace();
            //事务回滚
        } finally {
            //事务关闭
        }
    }

    @Override
    public void updateStatus(String oid, String status) {
        orderMapper.updateStatus(oid, status);
    }
}
