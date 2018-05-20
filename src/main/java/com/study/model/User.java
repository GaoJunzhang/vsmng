package com.study.model;

import javax.persistence.*;
import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 8902216402437436035L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;

    private String password;

    /**
     * 是否启用
     */
    private Integer enable;

    private Integer sumcount;

    private String position;

    @Column(name = "create_time")
    private String createTime;

    private String realyname;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取是否启用
     *
     * @return enable - 是否启用
     */
    public Integer getEnable() {
        return enable;
    }

    /**
     * 设置是否启用
     *
     * @param enable 是否启用
     */
    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    /**
     * @return sumcount
     */
    public Integer getSumcount() {
        return sumcount;
    }

    /**
     * @param sumcount
     */
    public void setSumcount(Integer sumcount) {
        this.sumcount = sumcount;
    }

    /**
     * @return position
     */
    public String getPosition() {
        return position;
    }

    /**
     * @param position
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /**
     * @return create_time
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    /**
     * @return realyname
     */
    public String getRealyname() {
        return realyname;
    }

    /**
     * @param realyname
     */
    public void setRealyname(String realyname) {
        this.realyname = realyname;
    }
}