package com.study.service;

import com.github.pagehelper.PageInfo;
import com.study.bean.MonthBean;
import com.study.bean.UsereMediaBean;
import com.study.model.UserMedia;

import java.util.List;

public interface UserMediaService extends IService<UserMedia> {
    int sumPalyCount(Integer uid);

    public List<UserMedia> listByUid(Integer uid);

    public PageInfo<UsereMediaBean> queryUserMediaByUname(String username, String startTime, String endTime, int start, int length);

    public int sumPlayCount(Integer uid);

    public List<MonthBean> statisticsByYear(String year);
}
