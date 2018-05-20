package com.study.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.study.bean.MonthBean;
import com.study.bean.UsereMediaBean;
import com.study.mapper.UserMediaMapper;
import com.study.model.UserMedia;
import com.study.service.UserMediaService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;

@Service("userMediaService")
public class UserMediaServiceImpl extends BaseService<UserMedia> implements UserMediaService {
    @Resource
    private UserMediaMapper userMediaMapper;

    public int sumPalyCount(Integer uid) {
        return userMediaMapper.sumPalyCount(uid);
    }

    public List<UserMedia> listByUid(Integer uid) {
        return userMediaMapper.listByUid(uid);
    }

    public PageInfo<UsereMediaBean> queryUserMediaByUname(String username, String startTime, String endTime, int start, int length) {

        int page = start / length + 1;
        PageHelper.startPage(page, length);
        List<UsereMediaBean> usereMediaBeanList = userMediaMapper.queryUserMediaByUname(username, startTime, endTime);
        return new PageInfo<>(usereMediaBeanList);
    }

    public int sumPlayCount(Integer uid) {
        return userMediaMapper.sumPalyCount(uid);
    }

    public List<MonthBean> statisticsByYear(String year) {
        return userMediaMapper.statisticsByYear(year);
    }
}
