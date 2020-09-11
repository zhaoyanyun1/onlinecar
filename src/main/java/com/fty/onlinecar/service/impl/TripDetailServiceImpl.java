package com.fty.onlinecar.service.impl;

import com.fty.onlinecar.dao.TripDetailMapper;
import com.fty.onlinecar.entity.CouponDetail;
import com.fty.onlinecar.entity.IntegralDetail;
import com.fty.onlinecar.entity.TripDetail;
import com.fty.onlinecar.entity.Users;
import com.fty.onlinecar.service.*;
import com.fty.onlinecar.base.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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
    public void updateTripState(TripDetail tripDetail) {
        List<Map<String, Object>> list = this.findPeersPassenger(tripDetail.getId());
        if(tripDetail.getState().equals("3")){
            for (Map<String, Object> map: list) {
                TripDetail tripDetail1 = this.findById(map.get("id"));
                tripDetail1.setState("3");
                this.update(tripDetail1);



                //Todo 积分计算
                Users driver = usersService.findById(tripDetail1.getDriverId());
                Users passenger = usersService.findById(tripDetail1.getUserId());
                integralDetailService.addIntegral(passenger,tripDetail1.getAllSeatNum(),"乘车");
                balanceDetailService.lessen(driver,tripDetail1.getAllSeatNum().toString(),"乘客确认同行");

            }

        }else if(tripDetail.getState().equals("2")){
            for (Map<String, Object> map: list) {
                TripDetail tripDetail1 = this.findById(map.get("id"));
                //查询乘客可用优惠券
                List<Map<String, Object>> couponlist = couponDetailService.findAvailableByUserId(tripDetail1.getUserId());
                if(couponlist !=null && couponlist.size()>0){
                    CouponDetail couponDetail = couponDetailService.findById(couponlist.get(0).get("id"));
                    couponDetail.setState("2");
                    couponDetail.setTripId(String.valueOf(tripDetail.getId()));
                    couponDetailService.update(couponDetail);
                }
            }

        }else if(tripDetail.getState().equals("1")){
            TripDetail pTripDetail = this.findById(tripDetail.getpId());
            TripDetail newTripDetail = this.findById(tripDetail.getId());
            pTripDetail.setSurplusSeatNum(pTripDetail.getSurplusSeatNum()-newTripDetail.getAllSeatNum());
            this.update(pTripDetail);
        }
        this.update(tripDetail);
    }

    @Override
    public TripDetail findCurTripByDriver(Map<String,Object> params) {
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
    public Map<String, Object> getById(String id) {
        return tripDetailMapper.getById(id);
    }
}
