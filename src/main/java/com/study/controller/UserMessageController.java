package com.study.controller;

import com.study.bean.UserMessageBean;
import com.study.service.UserMessageService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by user on 2018/6/8.
 */
@RestController
@RequestMapping(value = "/userMessage")
public class UserMessageController {
    @Resource
    private UserMessageService userMessageService;

    @RequestMapping(value = "/userMessages",method = RequestMethod.GET)
    public List<UserMessageBean> userMessages(@RequestParam(name = "uid",required = true) int uid){
        List<UserMessageBean> userMessageBeans = userMessageService.queryByUid(uid);
        return userMessageBeans;
    }
}
