package com.fty.onlinecar.service.impl;

import com.fty.onlinecar.dao.IntegralDetailMapper;
import com.fty.onlinecar.entity.IntegralDetail;
import com.fty.onlinecar.entity.Users;
import com.fty.onlinecar.service.IntegralDetailService;
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
 * Created by wanghuiwen on 2020/08/20.
 */
@Service
@Transactional
public class IntegralDetailServiceImpl extends AbstractService<IntegralDetail> implements IntegralDetailService {
    @Resource
    private IntegralDetailMapper integralDetailMapper;

    @Resource
    private UsersService usersService;

    @Override
    public Result list(String search, String order, Integer page, Integer size){
        Map<String, Object> params = JSONUtils.json2map(search);
        Map<String, Object> orderParams = JSONUtils.json2map(order);
        for (String key : orderParams.keySet()) {
                if (orderParams.get(key) != null && orderParams.get(key).equals("ascending")) orderParams.put(key, "asc");
                if (orderParams.get(key) != null && orderParams.get(key).equals("descending")) orderParams.put(key, "desc");
            }
        PageHelper.startPage(page, size);
        List<Map<String, Object>> res = integralDetailMapper.baseList(params, orderParams);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(res);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    @Override
    public void addIntegral(Users users, Integer num, String detail) {
        String integral =String.valueOf(Integer.parseInt(users.getIntegral())+1);
        users.setIntegral(integral);
        usersService.update(users);

        IntegralDetail integralDetail = new IntegralDetail();
        integralDetail.setUserId(users.getId().toString());
        integralDetail.setIntegralNum(num);
        integralDetail.setDetailed(detail);
        integralDetail.setState("1");
        this.save(integralDetail);
    }
}
