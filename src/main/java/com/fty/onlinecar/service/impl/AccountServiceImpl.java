package com.fty.onlinecar.service.impl;

import com.fty.onlinecar.dao.AccountMapper;
import com.fty.onlinecar.entity.Account;
import com.fty.onlinecar.service.AccountService;
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
 * Created by wanghuiwen on 2020/08/04.
 */
@Service
@Transactional
public class AccountServiceImpl extends AbstractService<Account> implements AccountService {
    @Resource
    private AccountMapper accountMapper;

    @Override
    public Result list(String search, String order, Integer page, Integer size){
        Map<String, Object> params = JSONUtils.json2map(search);
        Map<String, Object> orderParams = JSONUtils.json2map(order);
        for (String key : orderParams.keySet()) {
                if (orderParams.get(key) != null && orderParams.get(key).equals("ascending")) orderParams.put(key, "asc");
                if (orderParams.get(key) != null && orderParams.get(key).equals("descending")) orderParams.put(key, "desc");
            }
        PageHelper.startPage(page, size);
        List<Map<String, Object>> res = accountMapper.baseList(params, orderParams);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(res);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
