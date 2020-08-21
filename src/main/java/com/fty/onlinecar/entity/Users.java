package com.fty.onlinecar.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

public class Users implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String password;

    private Integer type;

    private String name;

    private String sex;

    private Integer age;

    private String idcard;

    @Column(name = "driving_experience")
    private Integer drivingExperience;

    @Column(name = "car_num")
    private String carNum;

    @Column(name = "license_up")
    private String licenseUp;

    @Column(name = "license_down")
    private String licenseDown;

    @Column(name = "idcard_photo")
    private String idcardPhoto;

    @Column(name = "vehicle_licence")
    private String vehicleLicence;

    private String phone;

    private String integral;

    private String balance;

    @Column(name = "invitation_code")
    private String invitationCode;

    @Column(name = "Invitees")
    private String invitees;

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
     * @return type
     */
    public Integer getType() {
        return type;
    }

    /**
     * @param type
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return sex
     */
    public String getSex() {
        return sex;
    }

    /**
     * @param sex
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * @return age
     */
    public Integer getAge() {
        return age;
    }

    /**
     * @param age
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * @return idcard
     */
    public String getIdcard() {
        return idcard;
    }

    /**
     * @param idcard
     */
    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    /**
     * @return driving_experience
     */
    public Integer getDrivingExperience() {
        return drivingExperience;
    }

    /**
     * @param drivingExperience
     */
    public void setDrivingExperience(Integer drivingExperience) {
        this.drivingExperience = drivingExperience;
    }

    /**
     * @return car_num
     */
    public String getCarNum() {
        return carNum;
    }

    /**
     * @param carNum
     */
    public void setCarNum(String carNum) {
        this.carNum = carNum;
    }

    /**
     * @return license_up
     */
    public String getLicenseUp() {
        return licenseUp;
    }

    /**
     * @param licenseUp
     */
    public void setLicenseUp(String licenseUp) {
        this.licenseUp = licenseUp;
    }

    /**
     * @return license_down
     */
    public String getLicenseDown() {
        return licenseDown;
    }

    /**
     * @param licenseDown
     */
    public void setLicenseDown(String licenseDown) {
        this.licenseDown = licenseDown;
    }

    /**
     * @return idcard_photo
     */
    public String getIdcardPhoto() {
        return idcardPhoto;
    }

    /**
     * @param idcardPhoto
     */
    public void setIdcardPhoto(String idcardPhoto) {
        this.idcardPhoto = idcardPhoto;
    }

    /**
     * @return vehicle_licence
     */
    public String getVehicleLicence() {
        return vehicleLicence;
    }

    /**
     * @param vehicleLicence
     */
    public void setVehicleLicence(String vehicleLicence) {
        this.vehicleLicence = vehicleLicence;
    }

    /**
     * @return phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return integral
     */
    public String getIntegral() {
        return integral;
    }

    /**
     * @param integral
     */
    public void setIntegral(String integral) {
        this.integral = integral;
    }

    /**
     * @return balance
     */
    public String getBalance() {
        return balance;
    }

    /**
     * @param balance
     */
    public void setBalance(String balance) {
        this.balance = balance;
    }

    /**
     * @return invitation_code
     */
    public String getInvitationCode() {
        return invitationCode;
    }

    /**
     * @param invitationCode
     */
    public void setInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode;
    }

    /**
     * @return Invitees
     */
    public String getInvitees() {
        return invitees;
    }

    /**
     * @param invitees
     */
    public void setInvitees(String invitees) {
        this.invitees = invitees;
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