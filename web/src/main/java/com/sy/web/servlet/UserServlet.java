package com.sy.web.servlet;

import cn.dsna.util.images.ValidateCode;
import com.alibaba.druid.util.StringUtils;
import com.sy.pojo.Address;
import com.sy.pojo.User;
import com.sy.service.AddressService;
import com.sy.service.UserService;
import com.sy.uitls.Base64Utils;
import com.sy.uitls.RandomUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.web.context.ContextLoader;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * ssssyy
 * 2019/9/26 11:29
 */
@WebServlet(name = "UserServlet", value = "/userservlet")
public class UserServlet extends BaseServlet {
    //      注入service
    private UserService userService = (UserService) ContextLoader.getCurrentWebApplicationContext().getBean("userServiceImpl");
    private AddressService addressService = (AddressService) ContextLoader.getCurrentWebApplicationContext().getBean("addressServiceImpl");

    public String register(HttpServletRequest request, HttpServletResponse response) {
    //      注册
        try {
    //      获取数据
            User user = new User();
            BeanUtils.populate(user, request.getParameterMap());
            String rePassword = request.getParameter("repassword");
            //校验数据
            if (StringUtils.isEmpty(user.getUsername())) {
                request.setAttribute("registerMsg", "用户名不能为空");
                return "/register.jsp";
            }
            if (StringUtils.isEmpty(user.getPassword())) {
                request.setAttribute("registerMsg", "密码不能为空");
                return "/register.jsp";
            }
            if (!user.getPassword().equals(rePassword)) {
                request.setAttribute("registerMsg", "两次密码不一致");
                return "/register.jsp";
            }
            if (StringUtils.isEmpty(user.getEmail())) {
                request.setAttribute("registerMsg", "邮箱不能为空");
                return "/register.jsp";
            }
            user.setFlag(0);
            user.setRole(1);
            user.setCode(RandomUtils.createActive());
            userService.register(user);
//          重定向至 注册成功页面
            return "redirect:/registerSuccess.jsp";
        } catch (Exception e) {
            request.setAttribute("registerMsg", "注册失败");
            e.printStackTrace();
        }
//          转发至 当前页面
        return "/register.jsp";
    }

    public String checkUserName(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String username = request.getParameter("username");
        if(username==null||username.trim().length()==0){
            return null;
        }
        User user = userService.checkUserName(username);
        if(user!=null){
            response.getWriter().write("1");
        }else{
            response.getWriter().write("0");
        }
        return null;
    }

    public String login(HttpServletRequest request,  HttpServletResponse response) {
        //获取用户名和密码
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String auto=request.getParameter("auto");
        String valcode = request.getParameter("valcode");
        String servercode = (String) request.getSession().getAttribute("vcode");

        if(StringUtils.isEmpty(valcode)){
            request.setAttribute("msg", "验证码不能为空");
            return "/login.jsp";
        }
        if(!valcode.equalsIgnoreCase(servercode)){
            request.setAttribute("msg", "验证码输入有误");
            return "/login.jsp";
        }

        if(StringUtils.isEmpty(username)){
            request.setAttribute("msg", "用户名不能为空");
            return "/login.jsp";
        }
        if(StringUtils.isEmpty(password)){
            request.setAttribute("msg", "密码不能为空");
            return "/login.jsp";
        }
        //验证用户名和密码是否正确
        User user = userService.login(username, password);
        if(user==null){
            request.setAttribute("msg", "用户名或密码有误");
            return "/login.jsp";
        }else{
            //有用户
            //有没有激活
            if(user.getFlag()!=1){
                request.setAttribute("msg", "用户尚未激活或禁用");
                return "/login.jsp";
            }
            //有没有权限
            if(user.getRole()!=1){
                request.setAttribute("msg", "用户没有权限");
                return "/login.jsp";
            }
            //登录成功
            request.getSession().setAttribute("user", user);
            if(auto!=null){
                //创建cookie
                String userinfo=username+"#"+password;
                Cookie cookie=new Cookie("userinfo", Base64Utils.encode(userinfo));
                cookie.setMaxAge(60*60*24*14);
                cookie.setPath("/");
                cookie.setHttpOnly(true);
                response.addCookie(cookie);
            }
            //重定向到首页
            return "redirect:/index.jsp";
        }
    }

    public String code(HttpServletRequest request,  HttpServletResponse response)throws Exception {
        ValidateCode validateCode=new ValidateCode(100, 40, 4, 20);
        String code = validateCode.getCode();
        request.getSession().setAttribute("vcode", code);
        System.out.println(code);
        validateCode.write(response.getOutputStream());
        return null;
    }

    public String checkCode(HttpServletRequest request,  HttpServletResponse response)throws Exception {
        String clientcode= request.getParameter("code");
        String servercode = (String) request.getSession().getAttribute("vcode");
        if(StringUtils.isEmpty(clientcode)){
            return null;
        }
        if(clientcode.equalsIgnoreCase(servercode)){
            response.getWriter().write("0");
        }else{
            response.getWriter().write("1");
        }
        return null;
    }

    public String logOut(HttpServletRequest request,  HttpServletResponse response) {
        //1删除session中的用户数据
        request.getSession().removeAttribute("user");
        //2session失效
        request.getSession().invalidate();
        //3删除cookie
        Cookie cookie=new Cookie("userinfo", "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        return "redirect:/index.jsp";
    }

    public String getAddress(HttpServletRequest request,  HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        if(user==null){
            return "redirect:/login.jsp";
        }
        List<Address> addList = addressService.findByUid(user.getId());
        request.setAttribute("addList", addList);
        return "/self_info.jsp";
    }

    public String addAddress(HttpServletRequest request,  HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        if(user==null){
            return "redirect:/login.jsp";
        }
        // name  phone detail
        String name = request.getParameter("name");//收件人
        String phone = request.getParameter("phone"); //收件人电话
        String detail = request.getParameter("detail");//收件人的详细地址
        if(StringUtils.isEmpty(name)){
            request.setAttribute("msg", "收件人不能为空!");
            return getAddress(request, response);
        }
        if(StringUtils.isEmpty(phone)){
            request.setAttribute("msg", "电话不能为空!");
            return getAddress(request, response);
        }
        if(StringUtils.isEmpty(detail)){
            request.setAttribute("msg", "地址不能为空!");
            return getAddress(request, response);
        }
        Address address=new Address(null, detail, name, phone, user.getId(), 0);
        addressService.add(address);

        return getAddress(request, response);
    }

    public String defaultAddress(HttpServletRequest request,  HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        if(user==null){
            return "redirect:/login.jsp";
        }
        String id = request.getParameter("id");
        addressService.updateDefault(Integer.parseInt(id),user.getId());

        return getAddress(request, response);
    }

    public String deleteAddress(HttpServletRequest request,  HttpServletResponse response) {
        //deleteAddress&id=8
        User user = (User) request.getSession().getAttribute("user");
        if(user==null){
            return "redirect:/login.jsp";
        }
        String id = request.getParameter("id");
        addressService.delete(Integer.parseInt(id));
        return getAddress(request, response);
    }

    public String updateAddress(HttpServletRequest request,  HttpServletResponse response) throws Exception{
        //deleteAddress&id=8
        User user = (User) request.getSession().getAttribute("user");
        response.setContentType("text/html;charset=utf-8");
        if(user==null){
            return "redirect:/login.jsp";
        }
        String id = request.getParameter("id");
        String level = request.getParameter("level");
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String detail = request.getParameter("detail");

        if(StringUtils.isEmpty(name)){
            response.getWriter().write("<script type='text/javascript'>alert('收件人不能为空');window.location='userservlet?method=getAddress'</script>");
            return null;
        }

        if(StringUtils.isEmpty(phone)){
            response.getWriter().write("<script type='text/javascript'>alert('电话不能为空');window.location='userservlet?method=getAddress'</script>");
            return null;
        }

        if(StringUtils.isEmpty(detail)){
            response.getWriter().write("<script type='text/javascript'>alert('详细地址不能为空');window.location='userservlet?method=getAddress'</script>");
            return null;
        }

        //更新
        Address address=new Address(Integer.parseInt(id), detail, name, phone, user.getId(), Integer.parseInt(level));
        addressService.update(address);

        return  getAddress(request, response);
    }
}
