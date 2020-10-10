package com.fty.onlinecar.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "coupon_detail")
public class CouponDetail implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "user_type")
    private String userType;

    @Column(name = "coupon_id")
    private Integer couponId;

    @Column(name = "trip_id")
    private Integer tripId;

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
    public Integer getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * @return user_type
     */
    public String getUserType() {
        return userType;
    }

    /**
     * @param userType
     */
    public void setUserType(String userType) {
        this.userType = userType;
    }

    /**
     * @return coupon_id
     */
    public Integer getCouponId() {
        return couponId;
    }

    /**
     * @param couponId
     */
    public void setCouponId(Integer couponId) {
        this.couponId = couponId;
    }

    /**
     * @return trip_id
     */
    public Integer getTripId() {
        return tripId;
    }

    /**
     * @param tripId
     */
    public void setTripId(Integer tripId) {
        this.tripId = tripId;
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