package com.fty.onlinecar.service.impl;

import com.fty.onlinecar.dao.CouponDetailMapper;
import com.fty.onlinecar.entity.CouponDetail;
import com.fty.onlinecar.service.CouponDetailService;
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
 * Created by wanghuiwen on 2020/08/24.
 */
@Service
@Transactional
public class CouponDetailServiceImpl extends AbstractService<CouponDetail> implements CouponDetailService {
    @Resource
    private CouponDetailMapper couponDetailMapper;

    @Override
    public Result list(String search, String order, Integer page, Integer size){
        Map<String, Object> params = JSONUtils.json2map(search);
        Map<String, Object> orderParams = JSONUtils.json2map(order);
        for (String key : orderParams.keySet()) {
                if (orderParams.get(key) != null && orderParams.get(key).equals("ascending")) orderParams.put(key, "asc");
                if (orderParams.get(key) != null && orderParams.get(key).equals("descending")) orderParams.put(key, "desc");
            }
        PageHelper.startPage(page, size);
        List<Map<String, Object>> res = couponDetailMapper.baseList(params, orderParams);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(res);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    @Override
    public List<Map<String, Object>> passengerCouponlist(Map<String, Object> params) {
        return couponDetailMapper.passengerCouponlist(params);
    }

    @Override
    public List<Map<String, Object>> findAvailableByUserId(Integer userId) {
        return couponDetailMapper.findAvailableByUserId(userId);
    }
}
