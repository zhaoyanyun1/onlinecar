package com.fty.onlinecar.service.impl;

import com.fty.onlinecar.dao.BalanceDetailMapper;
import com.fty.onlinecar.entity.BalanceDetail;
import com.fty.onlinecar.entity.Users;
import com.fty.onlinecar.service.BalanceDetailService;
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
public class BalanceDetailServiceImpl extends AbstractService<BalanceDetail> implements BalanceDetailService {
    @Resource
    private BalanceDetailMapper balanceDetailMapper;

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
        List<Map<String, Object>> res = balanceDetailMapper.baseList(params, orderParams);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(res);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    @Override
    public void lessen(Users users, String money, String detail) {
        String balance =String.valueOf(Integer.parseInt(users.getBalance()) - Integer.parseInt(money));
        users.setBalance(balance);
        usersService.update(users);
        BalanceDetail balanceDetail = new BalanceDetail();
        balanceDetail.setDriversId(String.valueOf(users.getId()));
        balanceDetail.setMoney("-"+money);
        balanceDetail.setState("1");
        this.save(balanceDetail);

    }
}
