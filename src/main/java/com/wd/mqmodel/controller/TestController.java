package com.wd.mqmodel.controller;

import com.alibaba.fastjson.JSONObject;
import com.wd.mqmodel.common.pojo.Result;
import com.wd.mqmodel.service.mq.test.TestSendMq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestSendMq testSendMq;

    @RequestMapping("/sendmq")
    public Result testSendMq(String message){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("centent",message);
        Boolean sendMq = testSendMq.testSendMq(jsonObject.toJSONString());
        return new Result(sendMq ? "发送成功" : "发送失败");
    }
}
