package com.wd.mqmodel.service.mq.test;

import com.wd.mqmodel.service.mq.base.BaseMqProducerHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class  TestSendMq extends BaseMqProducerHandler {

    @Value("${mq.produce.group}")
    private String producerGroup;

    @Value("${mq.nameserver.addr}")
    private String namesrvAddr;

    @Value("${mq.test.topic}")
    private String topic;

//    @Value
//    private String tag;

    @Override
    public void init() {
        super.producerGroup = producerGroup;
        super.namesrvAddr = namesrvAddr;
        super.topic = topic;
        initMq();
    }


    public Boolean testSendMq(String message){
        boolean b = super.sendMessage(message);
        return b;
    }
}
