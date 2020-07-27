package com.fty.onlinecar.service;
import com.fty.onlinecar.entity.Drivers;
import com.fty.onlinecar.base.service.Service;
import com.fty.onlinecar.response.Result;

/**
 * Created by wanghuiwen on 2020/07/27.
 */
public interface DriversService extends Service<Drivers> {
   Result list(String search, String order, Integer page, Integer size);
}
