package com.study.controller;

import com.study.model.Media;
import com.study.model.User;
import com.study.model.UserMedia;
import com.study.result.JsonResult;
import com.study.result.ResultCode;
import com.study.service.MediaService;
import com.study.service.UserMediaService;
import com.study.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;

@RestController
@RequestMapping(value = "/api/web")
public class ApiWebController {
    @Resource
    private MediaService mediaService;

    @Resource
    private UserMediaService userMediaService;

    @Resource
    private UserService userService;

    //插入媒体数据，如果媒体存在则+1播放次数
    @RequestMapping(value = "/postUserMedia")
    public Object postUserMedia(@Param("mediaName") String mediaName, HttpServletRequest request) {
        JsonResult jsonResult;
        String username = request.getAttribute("username")+"";
        try {
            User user = userService.selectByUsername(username);
            if (user.getSumcount() <= 0) {
                jsonResult = new JsonResult(ResultCode.SYS_ERROR, "该账号未授权播放", true);
                return jsonResult;
            }
            int spalycount =userMediaService.sumPalyCount(user.getId());
            if (user.getSumcount()<= spalycount){
                jsonResult = new JsonResult(ResultCode.SYS_ERROR, "该账号可播放次数为0", true);
                return jsonResult;
            }
            Media media = mediaService.findByName(mediaName);
            if (media == null){
                media = new Media();
                media.setName(mediaName);
                media.setCreatetime(new Timestamp(System.currentTimeMillis()));
                media.setUid(user.getId());
                mediaService.insertMedia(media);
            }
            UserMedia userMedia = new UserMedia();
            userMedia.setUid(user.getId());
            userMedia.setMid(media.getId());
            userMedia.setPlaytime(new Timestamp(System.currentTimeMillis()));
            userMediaService.save(userMedia);
        } catch (Exception e) {
            e.printStackTrace();
            jsonResult = new JsonResult(ResultCode.UNKNOWN_ERROR, "失败", true);
            return jsonResult;
        }
        jsonResult = new JsonResult(ResultCode.SUCCESS, "成功", true);
        return jsonResult;
    }


}