package com.fty.onlinecar.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "Integral_detail")
public class IntegralDetail implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "integral_num")
    private Integer integralNum;

    private String detailed;

    private String state;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    private static final long serialVersionUID = 1L;

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
     * @return user_id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return integral_num
     */
    public Integer getIntegralNum() {
        return integralNum;
    }

    /**
     * @param integralNum
     */
    public void setIntegralNum(Integer integralNum) {
        this.integralNum = integralNum;
    }

    /**
     * @return detailed
     */
    public String getDetailed() {
        return detailed;
    }

    /**
     * @param detailed
     */
    public void setDetailed(String detailed) {
        this.detailed = detailed;
    }

    /**
     * @return state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}