package com.sy.web.servlet;

import com.sy.pojo.*;
import com.sy.service.AddressService;
import com.sy.service.CartService;
import com.sy.service.OrderService;
import com.sy.uitls.RandomUtils;
import org.springframework.web.context.ContextLoader;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ssssyy
 * 2019/9/26 15:11
 */
@WebServlet(name = "OrderServlet",value = "/orderservlet")
public class OrderServlet extends BaseServlet {

    private AddressService addressService = (AddressService) ContextLoader.getCurrentWebApplicationContext().getBean("addressServiceImpl");
    private CartService cartService = (CartService) ContextLoader.getCurrentWebApplicationContext().getBean("cartServiceImpl");
    private OrderService orderService = (OrderService) ContextLoader.getCurrentWebApplicationContext().getBean("orderServiceImpl");

    public String getOrderView(HttpServletRequest request, HttpServletResponse response) throws Exception{
        //1有没有登录
        User user = (User) request.getSession().getAttribute("user");
        if(user==null){
            return "redirect:/login.jsp";
        }
        //2查询购物的商品
        List<Cart> carts = cartService.findByUid(user.getId());
        request.setAttribute("carts", carts);
        //3获取收货地址

        List<Address> addList=addressService.findByUid(user.getId());
        request.setAttribute("addList", addList);
        return "/order.jsp";
    }

    //提交订单 (重点)
    public String addOrder(HttpServletRequest request,HttpServletResponse response) throws Exception{
        //addOrder&aid=9
        User user = (User) request.getSession().getAttribute("user");
        if(user==null){
            return "redirect:/login.jsp";
        }
        String aid = request.getParameter("aid");
        //1获取购买的东西
        List<Cart> carts = cartService.findByUid(user.getId());

        if(carts==null||carts.size()==0){
            request.setAttribute("msg", "购物车为空，请选择商品!");
            return "/message.jsp";
        }

        //2先创建一个订单号
        String oid= RandomUtils.createOrderId();
        //3创建订单详情
        List<OrderDetail> orderDetails=new ArrayList<>();
        BigDecimal sum=new BigDecimal(0);
        for (Cart cart : carts) {
            OrderDetail orderDetail=new OrderDetail(null, oid, cart.getPid(), cart.getNum(), cart.getMoney());
            orderDetails.add(orderDetail);
            sum=sum.add(cart.getMoney());
        }

        //4创建订单
        Order order=new Order(oid, user.getId() , sum, "1", new Date(), Integer.parseInt(aid));

        try {
            orderService.saveOrder(order,orderDetails);
            request.setAttribute("order", order);
            return "/orderSuccess.jsp";
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg", "订单提交失败："+e.getMessage());
            return "/message.jsp";
        }

    }

}
