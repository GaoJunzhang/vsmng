package com.study.service;

import com.study.bean.UserMessageBean;
import com.study.model.UserMessage;

import java.util.List;

/**
 * Created by user on 2018/6/8.
 */
public interface UserMessageService extends IService<UserMessage> {
    public List<UserMessageBean> queryByUid(Integer uid);

    public void batchInsert(List<UserMessage> list);
}
