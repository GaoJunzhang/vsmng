package com.study.controller;

import com.github.pagehelper.PageInfo;
import com.study.bean.MonthBean;
import com.study.bean.UsereMediaBean;
import com.study.model.User;
import com.study.service.UserMediaService;
import com.study.util.VTools;
import org.apache.shiro.SecurityUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/userMedias")
public class UserMediaController {
    @Resource
    private UserMediaService userMediaService;

    @RequestMapping
    public Map<String, Object> getAll(UsereMediaBean usereMediaBean, String draw,
                                      @RequestParam(required = false) String startTime,
                                      @RequestParam(required = false) String endTime,
                                      @RequestParam(required = false, defaultValue = "1") int start,
                                      @RequestParam(required = false, defaultValue = "10") int length) {
        Map<String, Object> map = new HashMap<>();
        if (!StringUtils.isEmpty(endTime)) {
            endTime += " 23:59:59";
        }
        PageInfo<UsereMediaBean> pageInfo = userMediaService.queryUserMediaByUname(usereMediaBean.getUsername(), startTime, endTime, start, length);
        System.out.println("pageInfo.getTotal():" + pageInfo.getTotal());
        map.put("draw", draw);
        map.put("recordsTotal", pageInfo.getTotal());
        map.put("recordsFiltered", pageInfo.getTotal());
        map.put("data", pageInfo.getList());
        return map;
    }

    @RequestMapping(value = "/myUserMediaData", method = RequestMethod.GET)
    public Map myUserMediaData(@RequestParam(required = false) String year) {
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute("userSession");
        int sumPlayCount = userMediaService.sumPlayCount(user.getId());
        if (StringUtils.isEmpty(year)) {
            year = VTools.getSysYear();
        }
        List<MonthBean> list = userMediaService.statisticsByYear(year);
        Map map = new HashMap();
        map.put("sumCount", user.getSumcount());
        map.put("sumPlayCount", sumPlayCount);
        map.put("statisList", list);
        return map;
    }

    @RequestMapping(value = "/myUserMedias", method = RequestMethod.GET)
    public Map myUserMedias(String draw, @RequestParam(required = false) String startTime,
                            @RequestParam(required = false) String endTime,
                            @RequestParam(required = false, defaultValue = "1") int start,
                            @RequestParam(required = false, defaultValue = "10") int length) {
        Map<String, Object> map = new HashMap<>();
        if (!StringUtils.isEmpty(endTime)) {
            endTime += " 23:59:59";
        }
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute("userSession");
        PageInfo<UsereMediaBean> pageInfo = userMediaService.queryUserMediaByUname(user.getUsername(), startTime, endTime, start, length);
        System.out.println("pageInfo.getTotal():" + pageInfo.getTotal());
        map.put("draw", draw);
        map.put("recordsTotal", pageInfo.getTotal());
        map.put("recordsFiltered", pageInfo.getTotal());
        map.put("data", pageInfo.getList());
        return map;
    }
}
