package com.zyc.controller;

import com.zyc.model.User;
import com.zyc.service.UserService;
import com.zyc.util.EncodeMD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by YuChen Zhang on 17/11/03.
 */
public class AddUserController extends AbstractController {
    @Autowired
    @Qualifier("userServiceImplement")
    UserService userService;

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        String verifyCode = request.getParameter("veudyCode_email");
        HttpSession session = request.getSession(false);
        if(!session.getAttribute("verifyCode").equals(verifyCode)){
            modelAndView.addObject("addError","验证码错误");
            modelAndView.setViewName("redirect:/user/logIn.jsp");
            return modelAndView;
        }
        User user = new User();
        user.setUsername(request.getParameter("username"));
        user.setUserpassword(EncodeMD5.encodeMD5(request.getParameter("password")));
        user.setUsermail(request.getParameter("useremail"));
        userService.insertuUser(user);

        session.setAttribute("handleUrl",request.getParameter("service"));
        session.setAttribute("user",user);
        modelAndView.setViewName("redirect:/login?service");
        return modelAndView;
    }
}
