package com.fty.onlinecar.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "trip_detail")
public class TripDetail implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "driver_id")
    private Integer driverId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "p_id")
    private Integer pId;

    @Column(name = "all_seat_num")
    private Integer allSeatNum;

    @Column(name = "surplus_seat_num")
    private Integer surplusSeatNum;

    @Column(name = "departure_time")
    private String departureTime;

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
     * @return driver_id
     */
    public Integer getDriverId() {
        return driverId;
    }

    /**
     * @param driverId
     */
    public void setDriverId(Integer driverId) {
        this.driverId = driverId;
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
     * @return p_id
     */
    public Integer getpId() {
        return pId;
    }

    /**
     * @param pId
     */
    public void setpId(Integer pId) {
        this.pId = pId;
    }

    /**
     * @return all_seat_num
     */
    public Integer getAllSeatNum() {
        return allSeatNum;
    }

    /**
     * @param allSeatNum
     */
    public void setAllSeatNum(Integer allSeatNum) {
        this.allSeatNum = allSeatNum;
    }

    /**
     * @return surplus_seat_num
     */
    public Integer getSurplusSeatNum() {
        return surplusSeatNum;
    }

    /**
     * @param surplusSeatNum
     */
    public void setSurplusSeatNum(Integer surplusSeatNum) {
        this.surplusSeatNum = surplusSeatNum;
    }

    /**
     * @return departure_time
     */
    public String getDepartureTime() {
        return departureTime;
    }

    /**
     * @param departureTime
     */
    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
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