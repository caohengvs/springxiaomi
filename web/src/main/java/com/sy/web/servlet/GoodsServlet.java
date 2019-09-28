package com.sy.web.servlet;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sy.pojo.Goods;
import com.sy.service.GoodsService;
import org.springframework.web.context.ContextLoader;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * ssssyy
 * 2019/9/26 15:18
 */
@WebServlet(name = "GoodsServlet",value = "/goodsservlet")
public class GoodsServlet extends BaseServlet {

    private GoodsService goodsService = (GoodsService) ContextLoader.getCurrentWebApplicationContext().getBean("goodsServiceImpl");


    public String getGoodsListByTypeId(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String typeId = request.getParameter("typeId");
        String _pageNum = request.getParameter("pageNum");
        String _pageSize = request.getParameter("pageSize");
        int pageNum = 1;
        int pageSize = 8;
        if (!StringUtils.isEmpty(_pageNum)) {
            pageNum = Integer.parseInt(_pageNum);
            if (pageNum < 1) {
                pageNum = 1;
            }
        }
        if (!StringUtils.isEmpty(_pageSize)) {
            pageSize = Integer.parseInt(_pageSize);
            if (pageSize < 1) {
                pageSize = 8;
            }
        }
        System.out.println(pageNum + "..." + pageSize);

        String condition = "";
        if (typeId != null && typeId.trim().length() != 0) {
            condition = "typeid=" + typeId;
        }
        try {
//            重写分页操作
//            PageBean<Goods> pageBean=goodsService.findPageByWhere(pageNum,pageSize,condition);  // typeId=1;
            PageHelper.startPage(pageNum, pageSize);
            List<Goods> pageByWhere = goodsService.findPageByWhere(condition);
            PageInfo<Goods> pageInfo = new PageInfo<>(pageByWhere);
            request.setAttribute("pageBean", pageInfo);
            request.setAttribute("typeId", typeId);
            return "/goodsList.jsp";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/index.jsp";
        }
    }

    public String getGoodsById(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String id = request.getParameter("id");
        if (StringUtils.isEmpty(id)) {
            return "redirect:/index.jsp";
        }
        Goods goods = goodsService.findById(Integer.parseInt(id));
        request.setAttribute("goods", goods);

        return "/goodsDetail.jsp";

    }

}
