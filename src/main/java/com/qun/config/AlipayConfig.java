package com.qun.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

@Getter
@Component
@ConfigurationProperties(prefix = "alipay")
public class AlipayConfig {

//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String appId;

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String privateKey;

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String publicKey;

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notifyUrl;

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String returnUrl;

    // 签名方式
    public static String signType;

    // 字符编码格式
    public static String charset;

    // 支付宝网关
    public static String gatewayUrl;



    @Value("${alipay.appId}")
    public void setAppId(String appId) {
        AlipayConfig.appId = appId;
    }
    @Value("${alipay.privateKey}")
    public void setPrivateKey(String privateKey) {
        AlipayConfig.privateKey = privateKey;
    }
    @Value("${alipay.publicKey}")
    public void setPublicKey(String publicKey) {
        AlipayConfig.publicKey = publicKey;
    }
    @Value("${alipay.notifyUrl}")
    public void setNotifyUrl(String notifyUrl) {
        AlipayConfig.notifyUrl = notifyUrl;
    }
    @Value("${alipay.returnUrl}")
    public void setReturnUrl(String returnUrl) {
        AlipayConfig.returnUrl = returnUrl;
    }
    @Value("${alipay.signType}")
    public void setSignType(String signType) {
        AlipayConfig.signType = signType;
    }
    @Value("${alipay.charset}")
    public void setCharset(String charset) {
        AlipayConfig.charset = charset;
    }
    @Value("${alipay.gatewayUrl}")
    public void setGatewayUrl(String gatewayUrl) {
        AlipayConfig.gatewayUrl = gatewayUrl;
    }
}

