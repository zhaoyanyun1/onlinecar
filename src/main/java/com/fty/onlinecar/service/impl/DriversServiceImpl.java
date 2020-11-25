package com.fty.onlinecar.service.impl;

import com.fty.onlinecar.dao.DriversMapper;
import com.fty.onlinecar.entity.Drivers;
import com.fty.onlinecar.response.Table;
import com.fty.onlinecar.service.DriversService;
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
 * Created by wanghuiwen on 2020/07/27.
 */
@Service
@Transactional
public class DriversServiceImpl extends AbstractService<Drivers> implements DriversService {
    @Resource
    private DriversMapper driversMapper;

    @Override
    public List<Map<String, Object>> list(String search, String order, Integer page, Integer size){
        Map<String, Object> params = JSONUtils.json2map(search);
        Map<String, Object> orderParams = JSONUtils.json2map(order);
        for (String key : orderParams.keySet()) {
                if (orderParams.get(key) != null && orderParams.get(key).equals("ascending")) orderParams.put(key, "asc");
                if (orderParams.get(key) != null && orderParams.get(key).equals("descending")) orderParams.put(key, "desc");
            }
        PageHelper.startPage(page, size);
        params.put("state","1");
        List<Map<String, Object>> res = driversMapper.baseList(params, orderParams);
//        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(res);
        return res;
    }
}
