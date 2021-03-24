package com.qun.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.qun.common.lang.Result;
import com.qun.config.AlipayConfig;
import com.qun.pojo.vo.CartOrderVO;
import com.qun.pojo.entity.Order;
import com.qun.mapper.OrderMapper;
import com.qun.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public List<Order> getAll(int start,int num,String query) {
        return orderMapper.getAll(start,num,query);
    }

    @Override
    public Order get(long id) {
        return orderMapper.get(id);
    }

    @Override
    public int add(CartOrderVO order) {
        return orderMapper.add(order);
    }

    @Override
    public int delete(long id) {
        return orderMapper.delete(id);
    }

    @Override
    public int update(Order order) {
        return orderMapper.update(order);
    }

    @Override
    public int getTotal(int start,int num,String query) {
        return orderMapper.getTotal(start, num, query);
    }

    @Override
    public Result pay(CartOrderVO cartOrderVO) {

        System.out.println(cartOrderVO);

        String result = payOrder(System.currentTimeMillis()+"",cartOrderVO.getTotal().doubleValue(),"暗影精灵6 Plus","一台好贵的电脑");

        return Result.success(result);// 这个结果是一个表单，返回给浏览器填写，并且系统立即提交，才出来二维码
    }


    private String payOrder(String out_trade_no,Double total_amount,String subject,String body) {
        try {
            //获得初始化的AlipayClient
            AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.appId, AlipayConfig.privateKey, "json", AlipayConfig.charset, AlipayConfig.publicKey, AlipayConfig.signType);

            //设置请求参数
            AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
            alipayRequest.setReturnUrl(AlipayConfig.returnUrl);
            alipayRequest.setNotifyUrl(AlipayConfig.notifyUrl);


            alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\"," // 订单号
                    + "\"total_amount\":\""+ total_amount +"\"," // 金额
                    + "\"subject\":\""+ subject +"\"," // 名称
                    + "\"body\":\""+ body +"\"," // 描述
                    + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");


            //请求
            String result = alipayClient.pageExecute(alipayRequest).getBody();
            return result;
        } catch (AlipayApiException e){
            e.printStackTrace();
            return null;
        }
    }
}
