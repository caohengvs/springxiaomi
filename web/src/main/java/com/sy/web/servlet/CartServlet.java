package com.sy.web.servlet;

import com.alibaba.druid.util.StringUtils;
import com.sy.pojo.Cart;
import com.sy.pojo.Goods;
import com.sy.pojo.User;
import com.sy.service.CartService;
import com.sy.service.GoodsService;
import org.springframework.web.context.ContextLoader;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;

/**
 * ssssyy
 * 2019/9/26 15:21
 */
@WebServlet(name = "CartServlet",value = "/cartservlet")
public class CartServlet extends BaseServlet {

    private CartService cartService = (CartService) ContextLoader.getCurrentWebApplicationContext().getBean("cartServiceImpl");
    private GoodsService goodsService = (GoodsService) ContextLoader.getCurrentWebApplicationContext().getBean("goodsServiceImpl");

    public String addCart(HttpServletRequest request, HttpServletResponse response) throws Exception{
        //判断有没有登录
        User user = (User) request.getSession().getAttribute("user");
        if(user==null){
            return "redirect:/login.jsp";
        }
        //1获取参数
        String goodsId = request.getParameter("goodsId");
        String number = request.getParameter("number");
        if(StringUtils.isEmpty(goodsId)){
            return "redirect:/index.jsp";
        }
        //2添加到购物车 (数据库存)
        //2.1根据用户id和商品id查询有没有商品
        Cart cart=cartService.findByUidAndPid(user.getId(),Integer.parseInt(goodsId));
        //2.2 判断

        Goods goods = goodsService.findById(Integer.parseInt(goodsId));
        int num = Integer.parseInt(number);
        try {
            if(cart==null){
                //添加
                cart=new Cart(user.getId(), Integer.parseInt(goodsId), num, goods.getPrice().multiply(new BigDecimal(num)));
                cartService.add(cart);
            }else{
                //更新
                cart.setNum(cart.getNum()+num);
                cart.setMoney(goods.getPrice().multiply(new BigDecimal(cart.getNum())));
                cartService.update(cart);
            }
            return "redirect:/cartSuccess.jsp";
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "redirect:/index.jsp";
        }



    }

    public String getCart(HttpServletRequest request,HttpServletResponse response) throws Exception{
        //判断有没有登录
        User user = (User) request.getSession().getAttribute("user");
        if(user==null){
            return "redirect:/login.jsp";
        }
        List<Cart> carts= cartService.findByUid(user.getId());
        request.setAttribute("carts", carts);
        return "/cart.jsp";
    }

    public String addCartAjax(HttpServletRequest request,HttpServletResponse response) throws Exception{
        //addCartAjax&goodsId="+pid+"&number="+num,
        //判断有没有登录
        User user = (User) request.getSession().getAttribute("user");
        if(user==null){
            return "redirect:/login.jsp";
        }
        String goodsId = request.getParameter("goodsId");
        String number = request.getParameter("number");
        //查询
        Cart cart=cartService.findByUidAndPid(user.getId(), Integer.parseInt(goodsId));
        if(cart!=null) {
            if (number.equals("0")) {
                //删除
                cartService.delete(user.getId(),Integer.parseInt(goodsId));
            } else {
                //更新
                int num = Integer.parseInt(number);  // 1   或者  -1
                //获取单价
                BigDecimal price = cart.getMoney().divide(new BigDecimal(cart.getNum()));
                //更新数量
                cart.setNum(cart.getNum()+num);
                //更新金额
                cart.setMoney(price.multiply(new BigDecimal(cart.getNum())));
                cartService.update(cart);
            }
        }


        return null;
    }

    public String clearCartAjax(HttpServletRequest request, HttpServletResponse response) throws Exception{
        User user = (User) request.getSession().getAttribute("user");
        if(user==null){
            return "redirect:/login.jsp";
        }
        cartService.deleteByUid(user.getId());

        return null;
    }
}
