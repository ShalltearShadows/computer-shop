/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/12/1
 * @Time: 21:06
 */
package com.qun.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Data
@Component
@ConfigurationProperties(prefix = "lupus.jwt")
public class JwtUtils {

    private String secret;
    private long expire;
    private String header;

    /**
     * 生成token
     * @param id 用户ID
     * @return token
     */
    public String generateToken(long id){

        Date nowDate = new Date();

        //过期时间
        Date expireDate = new Date(nowDate.getTime()+expire * 1000);

        return Jwts.builder().setHeaderParam("typ","JWT")
                .setId(id+"")
                .setSubject(id+"")//设置签发人，也就是给谁的
                .setIssuedAt(nowDate)//签发时间
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS256,secret)//签名：加密算法和私钥信息；防止token被篡改
                .claim("companyId","123456").claim("company","the computer shop")//设置其他私有属性
                .compact();//压缩
    }


    /**
     * 解析token
     * @param token 用户令牌
     * @return 信息
     */
    public Claims getClaimsByToken(String token){
        try {

            return Jwts.parser()
                    .setSigningKey(secret)//设置私钥信息
                    .parseClaimsJws(token)
                    .getBody();

        }catch (Exception e){
            log.debug("validate is token error",e);
            return null;
        }
    }

    /**
     * token 是否过期
     */
    public boolean isTokenExpired(Date expiration){
        log.info("====>正在检查token是否已过期");
        return expiration.before(new Date());
    }

}
