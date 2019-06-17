package com.wd.mqmodel.service.mq.base;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.client.producer.selector.SelectMessageQueueByHash;
import org.apache.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PreDestroy;

/**
 * MQ消息生产者基础类
 *
 * @author huangjp
 */
public abstract class BaseMqProducerHandler {
    protected Logger logger = LoggerFactory.getLogger(BaseMqProducerHandler.class);
    protected Logger operateLog = LoggerFactory.getLogger("operateLog");

    private DefaultMQProducer defaultMQProducer;

    protected String producerGroup;

    protected String namesrvAddr;

    protected String topic;

    protected String tag;

    /**
     * 初始化实例接口，初始化完成后需要调用initMq()方法
     */
    public abstract void init();

    protected void initMq() {
        this.defaultMQProducer = new DefaultMQProducer(producerGroup);
        this.defaultMQProducer.setNamesrvAddr(namesrvAddr);
        this.defaultMQProducer.setInstanceName(String.valueOf(System.currentTimeMillis()));
        this.defaultMQProducer.setSendMsgTimeout(3000);
        this.defaultMQProducer.setRetryTimesWhenSendFailed(2);
        try {
            this.defaultMQProducer.start();
        } catch (MQClientException e) {
            logger.error("初始化MQ失败", e);
        }
    }

    /**
     * 无序发送
     * 说明：队列的选择采用默认方式
     *
     * @param messageBody
     * @return
     */
    public boolean sendMessage(String messageBody) {
        Message message = new Message(topic, tag, messageBody.getBytes());
        SendResult sendResult = null;
        try {
            sendResult = defaultMQProducer.send(message);
        } catch (Exception e) {
            logger.error("mq消息发送失败,messageBody=" + messageBody.toString(), e);
        }
        if (sendResult == null || sendResult.getSendStatus() != SendStatus.SEND_OK) {
            logger.error("mq消息发送失败,messageBody=" + messageBody.toString());
            return false;
        }
        return true;
    }
    
    /**
     * 说明：队列的选择采用默认方式
     * @param messageBody
     * @param msgTag 消息所属Tag
     * @return
     */
    public SendResult sendMessageByTag(String messageBody,String msgTag) {
        Message message = new Message(topic, msgTag, messageBody.getBytes());
        SendResult sendResult = null;
        try {
            sendResult = defaultMQProducer.send(message);
        } catch (Exception e) {
            logger.error("mq消息发送失败,messageBody=" + messageBody.toString(), e);
        }
        if (sendResult == null || sendResult.getSendStatus() != SendStatus.SEND_OK) {
            logger.error("mq消息发送失败,messageBody=" + messageBody.toString());
            return null;
        }
        return sendResult;
    }

    /**
     * 有序发送
     * 说明：采用参数 msgKeys 取余的结果进行队列选择
     *
     * @param messageBody
     * @param msgKeys     与消息队列选择器一起工作的参数。
     * @return
     */
    public boolean sendMessage(String messageBody, String msgKeys) {
        Message message = new Message(topic, tag, messageBody.getBytes());
        SendResult sendResult = null;
        try {
            sendResult = defaultMQProducer.send(message, new SelectMessageQueueByHash(), msgKeys);
        } catch (Exception e) {
            logger.error("mq消息发送失败,messageBody=" + messageBody.toString(), e);
        }
        if (sendResult == null || sendResult.getSendStatus() != SendStatus.SEND_OK) {
            logger.error("mq消息发送失败,messageBody=" + messageBody.toString());
            return false;
        }
        return true;
    }

    /**
     * Spring bean destroy-method
     */
    @PreDestroy
    public void destroy() {
        defaultMQProducer.shutdown();
    }
}
