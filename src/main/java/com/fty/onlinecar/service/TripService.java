package com.fty.onlinecar.service;
import com.fty.onlinecar.entity.Trip;
import com.fty.onlinecar.base.service.Service;
import com.fty.onlinecar.response.Result;

import java.util.List;
import java.util.Map;

/**
 * Created by wanghuiwen on 2020/08/03.
 */
public interface TripService extends Service<Trip> {
   List<Map<String, Object>> list(String search, String order, Integer page, Integer size);
}
