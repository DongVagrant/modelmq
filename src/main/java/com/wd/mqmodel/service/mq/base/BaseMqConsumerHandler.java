package com.wd.mqmodel.service.mq.base;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PreDestroy;
import java.util.List;

/**
 * MQ消费消息基础类
 *
 * @author huangjp
 */
public abstract class BaseMqConsumerHandler {

    protected Logger logger = LoggerFactory.getLogger(BaseMqConsumerHandler.class);
    protected Logger operateLog = LoggerFactory.getLogger("operateLog");
    private DefaultMQPushConsumer defaultMQPushConsumer;

    protected String customerGroup;

    protected String namesrvAddr;

    protected String topic;

    protected String subExpression = "*";

    /**
     * 初始化实例接口，初始化完成后需要调用initMq()方法
     */
    public abstract void init();

    /**
     * 初始化mq
     *
     * @param orderly true:有序 false:无序
     */
    protected void initMq(boolean orderly) {
        try {
            // 初始化
            this.defaultMQPushConsumer = new DefaultMQPushConsumer(customerGroup);
            this.defaultMQPushConsumer.setNamesrvAddr(namesrvAddr);
            this.defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
            this.defaultMQPushConsumer.subscribe(topic, subExpression);
            this.defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
            if (orderly) {
                registerMessageListenerOrderly();
            } else {
                registerMessageListener();
            }
            this.defaultMQPushConsumer.start();
            operateLog.info("初始化MQ成功");
        } catch (MQClientException e) {
            logger.error("初始化MQ失败", e);
        }
    }

    /**
     * Spring bean destroy-method
     */
    @PreDestroy
    public void destroy() {
        if (defaultMQPushConsumer != null) {
            defaultMQPushConsumer.shutdown();
        }
    }

    /**
     * 有序的
     */
    private void registerMessageListenerOrderly() {
        defaultMQPushConsumer.registerMessageListener(new MessageListenerOrderly() {
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
                handleMessage(msgs);
                return ConsumeOrderlyStatus.SUCCESS;
            }
        });
    }

    /**
     * 无序消费
     */
    private void registerMessageListener() {
        defaultMQPushConsumer.registerMessageListener(new MessageListenerConcurrently() {

            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                handleMessage(msgs);
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
    }

    /**
     * 处理消息
     *
     * @param msgs
     */
    public abstract void handleMessage(List<MessageExt> msgs);
}
