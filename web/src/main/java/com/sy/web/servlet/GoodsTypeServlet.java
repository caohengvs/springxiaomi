package com.sy.web.servlet;

import com.alibaba.fastjson.JSON;
import com.sy.pojo.GoodsType;
import com.sy.service.GoodsTypeService;
import org.springframework.web.context.ContextLoader;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * ssssyy
 * 2019/9/26 15:15
 */
@WebServlet(name = "GoodsTypeServlet",value = "/goodstypeservlet")
public class GoodsTypeServlet extends BaseServlet {
    private GoodsTypeService goodsTypeService = (GoodsTypeService) ContextLoader.getCurrentWebApplicationContext().getBean("goodsTypeServiceImpl");
    public String goodstypelist(HttpServletRequest request, HttpServletResponse response) throws Exception{
        response.setContentType("application/json;charset=utf-8");
        //获取商品的类型
        //调用方法
        List<GoodsType> goodsTypeList= goodsTypeService.findTypeByLevel(1);
        System.out.println(goodsTypeList.toString());
        //使用fastjson转成json字符串
        String json = JSON.toJSONString(goodsTypeList);
        //返回给浏览器
        response.getWriter().write(json);
        return null;
    }
}
