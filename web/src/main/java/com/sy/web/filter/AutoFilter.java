package com.sy.web.filter;

import com.sy.pojo.User;
import com.sy.service.UserService;
import com.sy.uitls.Base64Utils;
import org.springframework.web.context.ContextLoaderListener;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * ssssyy
 * 2019/9/26 17:03
 */
@WebFilter(filterName = "AutoFilter",value = "/index.jsp")
public class AutoFilter implements Filter {
    private UserService userService = (UserService) ContextLoaderListener.getCurrentWebApplicationContext().getBean("userServiceImpl");

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        //1判断当前是否已经登录
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            chain.doFilter(req, resp);
            return;
        }
        //2没有登录
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("userinfo")) {
                    String value = cookie.getValue();
                    String userinfo = Base64Utils.decode(value);
                    String[] userinfos = userinfo.split("#");
                    System.out.println(userService.toString());
                    User user2 = userService.login(userinfos[0], userinfos[1]);
                    if (user2 != null) {
                        if (user2.getFlag() == 1) {
                            request.getSession().setAttribute("user", user2);
                            chain.doFilter(req, resp);
                            return;
                        }
                    } else {
                        Cookie cookie1 = new Cookie("userinfo", "");
                        cookie1.setMaxAge(0);
                        cookie1.setPath("/");
                        response.addCookie(cookie1);
                    }

                }

            }
        }
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
