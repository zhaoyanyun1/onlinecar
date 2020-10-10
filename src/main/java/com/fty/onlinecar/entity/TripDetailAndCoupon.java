package com.fty.onlinecar.entity;

public class TripDetailAndCoupon {

    private TripDetail tripDetail;
    private CouponDetail coupon;
    private String [] dates;
    private String returnTime;
    private String isReturn;

    public String getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(String returnTime) {
        this.returnTime = returnTime;
    }

    public String getIsReturn() {
        return isReturn;
    }

    public void setIsReturn(String isReturn) {
        this.isReturn = isReturn;
    }

    public String[] getDates() {
        return dates;
    }

    public void setDates(String[] dates) {
        this.dates = dates;
    }

    public TripDetail getTripDetail() {
        return tripDetail;
    }

    public void setTripDetail(TripDetail tripDetail) {
        this.tripDetail = tripDetail;
    }

    public CouponDetail getCoupon() {
        return coupon;
    }

    public void setCoupon(CouponDetail coupon) {
        this.coupon = coupon;
    }
}
