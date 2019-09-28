package com.sy.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * ssssyy
 * 2019/9/26 11:06
 */
public class BaseServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //判断用户的行为
        String methodName = request.getParameter("method");
        //利用反射调用
        try {
            Method method = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            String url = (String) method.invoke(this, request, response); //this.method();
            if (url != null && url.trim().length() != 0) {
                //转发
                if (url.startsWith("redirect:")) {
                    response.sendRedirect(request.getContextPath() + url.split(":")[1]);
                } else {
                    //System.out.println(url);
                    request.getRequestDispatcher(url).forward(request, response);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
