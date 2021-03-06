/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/12/1
 * @Time: 20:22
 */
package com.qun.shiro;

import cn.hutool.json.JSONUtil;
import com.alibaba.druid.util.StringUtils;
import com.qun.common.lang.Result;
import com.qun.util.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.PostConstruct;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
@Slf4j
public class JwtFilter extends AuthenticatingFilter {

    //JwtUtils使用@Component注解方式注入（需要配置如下代码）
//    private static JwtFilter obj;
//
//    @PostConstruct
//    public void initial(){
//        obj = this;
//        obj.jwtUtils = this.jwtUtils;
//    }


    @Autowired
    private JwtUtils jwtUtils;


    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse){

        HttpServletRequest request = (HttpServletRequest)servletRequest;

        String jwt = request.getHeader("Authorization");

        if (StringUtils.isEmpty(jwt)){
            //相当于没有jwt，
            return null;
        }
        //有，则放入shiro
        return new JwtToken(jwt);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {

        HttpServletRequest request = WebUtils.toHttp(servletRequest);

        String token = request.getHeader("Authorization");

        if (StringUtils.isEmpty(token)){
            //放行,交给认证注解拦截
            return true;
        }else {
            //检验jwt
            System.out.println(jwtUtils==null?"JwtUtils又在JwtFilter为NULL":"");
            Claims claim = jwtUtils.getClaimsByToken(token);
            if (claim==null||jwtUtils.isTokenExpired(claim.getExpiration())){
                log.info("===>token已失效");
                throw new ExpiredCredentialsException("token已失效，请重新登录");
            }
            //执行登录
            return executeLogin(servletRequest,servletResponse);
        }
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {

        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        Throwable throwable =  e.getCause() == null ? e : e.getCause();

        Result fail = Result.fail(throwable.getMessage());

        String json = JSONUtil.toJsonStr(request);

        try {
            httpServletResponse.getWriter().print(json);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        return false;
    }

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
        HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
        httpServletResponse.setHeader("Access-Control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods","GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers" , httpServletRequest.getHeader("Access-Control-Request-Headers"));
        //跨域时会首先发送一个OPTIONS请求，这里我们给OPTIONS请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(org.springframework.http.HttpStatus.OK.value());
            return false;
        }

        return super.preHandle(request, response);
    }
}
