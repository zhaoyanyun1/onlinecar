package com.fty.onlinecar.service.impl;

import com.fty.onlinecar.dao.TixianMapper;
import com.fty.onlinecar.entity.Tixian;
import com.fty.onlinecar.response.Table;
import com.fty.onlinecar.service.TixianService;
import com.fty.onlinecar.base.service.AbstractService;
import com.github.pagehelper.Page;
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
 * Created by wanghuiwen on 2020/12/21.
 */
@Service
@Transactional
public class TixianServiceImpl extends AbstractService<Tixian> implements TixianService {
    @Resource
    private TixianMapper tixianMapper;

    @Override
    public Page<Map<String, Object>> list(String search, String order, Integer page, Integer size){
        Map<String, Object> params = JSONUtils.json2map(search);
        Map<String, Object> orderParams = JSONUtils.json2map(order);
        for (String key : orderParams.keySet()) {
                if (orderParams.get(key) != null && orderParams.get(key).equals("ascending")) orderParams.put(key, "asc");
                if (orderParams.get(key) != null && orderParams.get(key).equals("descending")) orderParams.put(key, "desc");
            }
        PageHelper.startPage(page, size);
        List<Map<String, Object>> res = tixianMapper.list(params, orderParams);
        Page<Map<String, Object>> pageList = (Page<Map<String, Object>>) res;
        return pageList;
    }
}
