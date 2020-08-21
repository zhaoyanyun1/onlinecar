package com.fty.onlinecar.service.impl;

import com.fty.onlinecar.dao.TripDetailMapper;
import com.fty.onlinecar.entity.IntegralDetail;
import com.fty.onlinecar.entity.TripDetail;
import com.fty.onlinecar.entity.Users;
import com.fty.onlinecar.service.BalanceDetailService;
import com.fty.onlinecar.service.IntegralDetailService;
import com.fty.onlinecar.service.TripDetailService;
import com.fty.onlinecar.base.service.AbstractService;
import com.fty.onlinecar.service.UsersService;
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
    public List<Map<String, Object>> driverTriplist() {
        List<Map<String, Object>> res = tripDetailMapper.driverTriplist();
        return res;
    }

    @Override
    public void confirmTrip(TripDetail tripDetail, TripDetail pTripDetail) {
        //成功确认行程
        tripDetail.setState("1");
        pTripDetail.setSurplusSeatNum(pTripDetail.getSurplusSeatNum()-1);
        this.update(pTripDetail);
        tripDetail.setDepartureTime(pTripDetail.getDepartureTime());
        this.save(tripDetail);

        //Todo 积分计算
        Users driver = usersService.findById(pTripDetail.getDriverId());
        Users passenger = usersService.findById(tripDetail.getUserId());
        if(passenger.getInvitees()!=null && !passenger.getInvitees().equals("")){
            Users invitees = usersService.findBy("invitation_code",passenger.getInvitees());
            integralDetailService.addIntegral(invitees,1,"邀请用户乘车");
            balanceDetailService.lessen(driver,"-2","乘客确认同行");

        }
        integralDetailService.addIntegral(passenger,1,"乘车");
    }

    @Override
    public TripDetail findCurTripByDriver(Map<String,Object> params) {
        return tripDetailMapper.findCurTripByDriver(params);
    }

    @Override
    public TripDetail findCurTripByPassenger(Map<String,Object> params) {
        return tripDetailMapper.findCurTripByPassenger(params);
    }

    @Override
    public List<Map<String, Object>> findPeersPassenger(Integer tripId) {
        return tripDetailMapper.findPeersPassenger(tripId);
    }
}
