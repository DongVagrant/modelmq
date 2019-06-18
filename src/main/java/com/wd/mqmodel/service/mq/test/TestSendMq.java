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

    private String consumerMessage;

//    @Value
//    private String tag;

    @Override
    public void init() {
        super.producerGroup = producerGroup;
        super.namesrvAddr = namesrvAddr;
        super.topic = topic;
        initMq();
    }


    public String testSendMq(String message) throws InterruptedException {
        init();
        boolean b = super.sendMessage(message);

        logger.info("发送mq结果："+b);
        consumerMessage = "默认值";
//        Thread.sleep(1000*10);
        return b?"成功":"失败";
    }

//    class ConsumerMq extends BaseMqConsumerHandler {
//
//        @Value("${mq.consumer.group}")
//        private String customerGroup;
//
//        @Value("${mq.nameserver.addr}")
//        private String namesrvAddr;
//
//        @Value("${mq.test.topic}")
//        private String topic;
//
//        @PostConstruct
//        @Override
//        public void init() {
//            super.customerGroup = customerGroup;
//            super.namesrvAddr = namesrvAddr;
//            super.topic = topic;
//            initMq(true);
//        }
//
//        @Override
//        public void handleMessage(List<MessageExt> msgs) {
//            for (MessageExt messageExt:msgs){
//                String body = new String(messageExt.getBody());
//                JSONObject jsonObject = JSONObject.parseObject(body);
//                consumerMessage = jsonObject.getString("message");
//                logger.info("消费mq 成功");
//            }
//        }
//    }
}
