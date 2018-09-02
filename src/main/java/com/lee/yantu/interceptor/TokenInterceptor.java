package com.lee.yantu.interceptor;

import com.lee.yantu.util.JWT;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        if(httpServletRequest.getRequestURL().equals("/login") || httpServletRequest.getRequestURL().equals("/register")){
            return true;
        }
        System.out.println("token验证拦截器");
        String token = httpServletRequest.getHeader("token");
        if (token == null || token.equals("")) {
            return true;
        }
        //解密token并验证
        JWT.getTokenInstance(token);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        System.out.println("post");
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        System.out.println("after");
    }
}
