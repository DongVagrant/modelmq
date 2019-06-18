package com.wd.mqmodel.service.mq.test;

import com.alibaba.fastjson.JSONObject;
import com.wd.mqmodel.service.mq.base.BaseMqConsumerHandler;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public  class TestConsumerMq extends BaseMqConsumerHandler {

    @Value("${mq.consumer.group}")
    private String customerGroup;

    @Value("${mq.nameserver.addr}")
    private String namesrvAddr;

    @Value("${mq.test.topic}")
    private String topic;

    @PostConstruct
    @Override
    public void init() {
        super.customerGroup = customerGroup;
        super.namesrvAddr = namesrvAddr;
        super.topic = topic;
        initMq(false);
    }

    @Override
    public  void handleMessage(List<MessageExt> msgs)
    {
        for (MessageExt messageExt:msgs){
            String body = new String(messageExt.getBody());
            JSONObject jsonObject = JSONObject.parseObject(body);
            logger.info("消费mq 成功,消息体"+jsonObject.toJSONString());
        }
    }
}
