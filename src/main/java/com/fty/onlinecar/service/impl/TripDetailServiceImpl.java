package com.fty.onlinecar.service.impl;

import cn.hutool.core.date.DateUtil;
import com.fty.onlinecar.dao.TripDetailMapper;
import com.fty.onlinecar.entity.*;
import com.fty.onlinecar.response.ResultEnum;
import com.fty.onlinecar.service.*;
import com.fty.onlinecar.base.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import com.fty.onlinecar.utils.JSONUtils;
import com.fty.onlinecar.response.Result;
import com.fty.onlinecar.response.ResultGenerator;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * Created by wanghuiwen on 2020/08/13.
 */
@Service
@Transactional
public class TripDetailServiceImpl extends AbstractService<TripDetail> implements TripDetailService {
    @Resource
    private TripDetailMapper tripDetailMapper;

    @Resource
    private UsersService usersService;

    @Resource
    private IntegralDetailService integralDetailService;

    @Resource
    private BalanceDetailService balanceDetailService;

    @Resource
    private CouponDetailService couponDetailService;
    @Resource
    private CouponService couponService;


    @Override
    public Result list(String search, String order, Integer page, Integer size){
        Map<String, Object> params = JSONUtils.json2map(search);
        Map<String, Object> orderParams = JSONUtils.json2map(order);
        for (String key : orderParams.keySet()) {
                if (orderParams.get(key) != null && orderParams.get(key).equals("ascending")) orderParams.put(key, "asc");
                if (orderParams.get(key) != null && orderParams.get(key).equals("descending")) orderParams.put(key, "desc");
            }
        PageHelper.startPage(page, size);
        List<Map<String, Object>> res = tripDetailMapper.baseList(params, orderParams);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(res);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    @Override
    public List<Map<String, Object>> driverTriplist(String search) {
        Map<String, Object> params = JSONUtils.json2map(search);

        String times = "";
        if(null !=params.get("date") && !params.get("date").equals("")){
            times = params.get("date").toString();
            if(null !=params.get("times") && !params.get("times").equals("")){
                times = times+" "+params.get("times").toString();
            }else {
                times = times+" 00:00:00";
            }
        }else{
            String newDate = DateUtil.format(new Date(),"yyyy-MM-dd");
            if(null !=params.get("times") && !params.get("times").equals("")){
                times = newDate+" "+params.get("times").toString();
            }else {

            }
        }

        params.put("times",times);

        List<Map<String, Object>> res = tripDetailMapper.driverTriplist(params);
        return res;
    }

    @Override
    public void confirmTrip(TripDetail tripDetail, TripDetail pTripDetail) {
        //成功确认行程
        tripDetail.setState("5");
        tripDetail.setTripsDirection(pTripDetail.getTripsDirection());
        tripDetail.setDriverId(pTripDetail.getDriverId());
//        pTripDetail.setSurplusSeatNum(pTripDetail.getSurplusSeatNum()-tripDetail.getAllSeatNum());
        this.update(pTripDetail);
        tripDetail.setDepartureTime(pTripDetail.getDepartureTime());
        this.save(tripDetail);



    }

    @Override
    public Result updateTripState(TripDetail tripDetail) {
        List<Map<String, Object>> list = this.findPeersPassenger(tripDetail.getId());
        if(tripDetail.getState().equals("3")){
            for (Map<String, Object> map: list) {
                TripDetail tripDetail1 = this.findById(map.get("id"));



                if(!tripDetail1.getState().equals("4") && !tripDetail1.getState().equals("5") && !tripDetail1.getState().equals("6") && !tripDetail1.getState().equals("7")){
                    //Todo 积分计算
                    Users driver = usersService.findById(tripDetail1.getDriverId());
                    Users passenger = usersService.findById(tripDetail1.getUserId());
                    integralDetailService.addIntegral(passenger,tripDetail1.getAllSeatNum(),"乘车");
                    balanceDetailService.lessen(driver,tripDetail1.getAllSeatNum().toString(),"乘客确认同行");


                    tripDetail1.setState("3");
                    this.update(tripDetail1);

                    //判断乘客是不是第一次乘车
                    Map<String, Object> params = new HashMap<>();
                    params.put("userId",passenger.getId());
                    params.put("state","3");
                    params.put("curTripId",tripDetail1.getId());
                    List<Map<String,Object>> history = this.findHistory(params);
                    if(history.isEmpty()){

                        //判断乘客有没有邀请人
                        if(passenger.getInvitees() !=null && !passenger.getInvitees().equals("")){
                            Users invitees = usersService.findById(passenger.getInvitees());
                            //判断邀请人类型是乘客
                            if(invitees.getType()==2){
                                integralDetailService.addIntegral(invitees,1,"邀请乘客乘车");
                                balanceDetailService.lessen(driver,"1","乘客邀请人乘客");
                            }

                        }

                    }


                }




            }

        }else if(tripDetail.getState().equals("2")){
            for (Map<String, Object> map: list) {

                TripDetail tripDetail1 = this.findById(map.get("id"));
                if(!tripDetail1.getState().equals("4") && !tripDetail1.getState().equals("5") && !tripDetail1.getState().equals("6") && !tripDetail1.getState().equals("7")){

                    Map<String, Object> params = new HashMap<>();
                    params.put("tripId",tripDetail1.getId());
                    params.put("state","2");
                    params.put("userId",tripDetail1.getUserId());
                    List<Map<String, Object>> couponDetails = couponDetailService.passengerCouponlist(params);
                    if(!couponDetails.isEmpty()){
                        if(couponDetails.size()>1){

                        }else{
                            CouponDetail couponDetail =couponDetailService.findById(couponDetails.get(0).get("id"));

                            couponDetail.setState("3");
                            couponDetail.setUpdateTime(new Date());
                            couponDetailService.update(couponDetail);

                            Users driver = usersService.findById(tripDetail1.getDriverId());
                            Coupon coupon = couponService.findById(couponDetail.getCouponId());
                            String balance = driver.getBalance();
                            balance = String.valueOf(Integer.parseInt(balance)+Integer.parseInt(coupon.getMoney()));
                            driver.setBalance(balance);
                            usersService.update(driver);
                        }
                    }


                    tripDetail1.setState("2");
                    this.update(tripDetail1);
                }

            }

        }else if(tripDetail.getState().equals("1")){
            TripDetail pTripDetail = this.findById(tripDetail.getpId());
            TripDetail newTripDetail = this.findById(tripDetail.getId());
            if(pTripDetail.getSurplusSeatNum() < newTripDetail.getAllSeatNum()){
                return ResultGenerator.genSeatLowResult();
            }
            pTripDetail.setSurplusSeatNum(pTripDetail.getSurplusSeatNum()-newTripDetail.getAllSeatNum());
            this.update(pTripDetail);
        }
        this.update(tripDetail);
        return ResultGenerator.genSuccessResult();
    }

    @Override
    public List<TripDetail> findCurTripByDriver(Map<String,Object> params) {
        return tripDetailMapper.findCurTripByDriver(params);
    }

    @Override
    public Map<String,Object> findCurTripByPassenger(Map<String,Object> params) {
        return tripDetailMapper.findCurTripByPassenger(params);
    }

    @Override
    public List<Map<String, Object>> findPeersPassenger(Integer tripId) {
        return tripDetailMapper.findPeersPassenger(tripId);
    }

    @Override
    public List<Map<String, Object>> findHistory(Map<String, Object> params) {
        return tripDetailMapper.findHistory(params);
    }

    @Override
    public Map<String, Object> getById(String id) {
        return tripDetailMapper.getById(id);
    }
}
