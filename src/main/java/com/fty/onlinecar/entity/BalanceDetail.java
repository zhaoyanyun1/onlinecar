package com.fty.onlinecar.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "balance_detail")
public class BalanceDetail implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "drivers_id")
    private String driversId;

    private String money;

    private String type;

    private String state;

    @Column(name = "crate_time")
    private Date crateTime;

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
     * @return drivers_id
     */
    public String getDriversId() {
        return driversId;
    }

    /**
     * @param driversId
     */
    public void setDriversId(String driversId) {
        this.driversId = driversId;
    }

    /**
     * @return money
     */
    public String getMoney() {
        return money;
    }

    /**
     * @param money
     */
    public void setMoney(String money) {
        this.money = money;
    }

    /**
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type
     */
    public void setType(String type) {
        this.type = type;
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
     * @return crate_time
     */
    public Date getCrateTime() {
        return crateTime;
    }

    /**
     * @param crateTime
     */
    public void setCrateTime(Date crateTime) {
        this.crateTime = crateTime;
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