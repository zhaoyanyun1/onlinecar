package com.fty.onlinecar.service;
import com.fty.onlinecar.entity.Drivers;
import com.fty.onlinecar.base.service.Service;
import com.fty.onlinecar.response.Result;
import com.fty.onlinecar.response.Table;

import java.util.List;
import java.util.Map;

/**
 * Created by wanghuiwen on 2020/07/27.
 */
public interface DriversService extends Service<Drivers> {
   List<Map<String, Object>> list(String search, String order, Integer page, Integer size);
}
