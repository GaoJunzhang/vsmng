package com.study.service;

import com.github.pagehelper.PageInfo;
import com.study.model.Media;

public interface MediaService extends IService<Media> {

    public PageInfo<Media> selectByPage(Media media, int start, int length);

    public Media findByName(String name);


    public int insertMedia(Media media);

   /* public int sumPalyCount(Integer uid);

    public int updateMedia(Integer playcount, Integer id);*/


}
