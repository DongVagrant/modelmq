package com.wd.mqmodel.controller;

import com.alibaba.fastjson.JSONObject;
import com.wd.mqmodel.common.pojo.Result;
import com.wd.mqmodel.service.mq.test.TestSendMq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/test")
public class TestController {
    private static final Logger logger = LoggerFactory.getLogger(TestController.class);
    @Autowired
    private TestSendMq testSendMq;

    @RequestMapping("/sendmq")
    @ResponseBody
    public Result testSendMq(String message){
        if (message == null || message == ""){
            message = "默认消息：你呀没填信息";
        }
        logger.info("testSendMq  start");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("centent",message);
        String sendMq = null;
        try {
            sendMq = testSendMq.testSendMq(jsonObject.toJSONString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (sendMq == null || sendMq == ""){
            sendMq = "消费失败啊";
        }
        return new Result(sendMq);
    }
}
