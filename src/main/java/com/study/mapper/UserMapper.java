package com.study.mapper;

import com.study.model.User;
import com.study.util.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper extends MyMapper<User> {
    public void batchUpdateCount(@Param("list") List<User> list);

    int totalUser();

}