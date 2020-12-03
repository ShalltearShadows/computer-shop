/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/12/3
 * @Time: 17:08
 */
package com.qun.common.interceptor;

import com.alibaba.druid.util.StringUtils;
import com.qun.util.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component
public class JwtInterceptor implements HandlerInterceptor {

    // JwtUtils使用@Component注解方式注入（需要配置如下代码）
    private static JwtInterceptor jwtInterceptor;

    @PostConstruct
    public void init(){
        jwtInterceptor = this;
        jwtInterceptor.jwtUtils = this.jwtUtils;
    }


    @Autowired
    private JwtUtils jwtUtils;

    // 进入控制器接口之前，要执行的方法
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1.根据token 检验用户是否登录
        //2.检验用户是否拥有访问当前接口的权限

        //通过request获取请求token信息
        String token = request.getHeader(  "Authorization");
        //判断请求头信息是否为空
        if(!StringUtils.isEmpty(token)){
            //获取token数据

            System.out.println(jwtUtils);

            Claims claims = jwtInterceptor.jwtUtils.getClaimsByToken(token);
            if(claims != null) {
                request.setAttribute("user_claims", claims);
                return true;
            }

        }

        throw new IllegalArgumentException("请先登录");
    }


    // 拦截于接口方法成功返回后，视图渲染前，可以对modelAndView进行操作。
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }


    //相应结束之前执行的内容
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
