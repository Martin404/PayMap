package com.hugnew.sps.mq;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import com.hugnew.core.mq.Gson2JsonMessageConverter;
import com.hugnew.sps.dto.PayRequestParam;
import com.hugnew.sps.services.IPayRouteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * MQ监听支付请求入口消息
 * Created by Martin on 2016/7/01.
 */
public class PayRequestQueueListener implements ChannelAwareMessageListener {

    private static Logger logger = LoggerFactory.getLogger(PayRequestQueueListener.class);
    @Autowired
    private IPayRouteService payRouteService;
    @Autowired
    private Gson2JsonMessageConverter messageConverter;

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        channel.basicQos(100);
        PayRequestParam queueObject = (PayRequestParam)messageConverter.fromMessage(message);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        if(payRouteService.getPayRetMap4MQ(queueObject)){
            logger.trace("success processed pay request:{}", JSON.toJSONString(queueObject));
        }else{
            logger.error("error processed pay request:{}",JSON.toJSONString(queueObject));
            //补偿机制忽略
        }
    }
}
