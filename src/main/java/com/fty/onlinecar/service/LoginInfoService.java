package com.fty.onlinecar.service;
import com.fty.onlinecar.entity.LoginInfo;
import com.fty.onlinecar.base.service.Service;
import com.fty.onlinecar.response.Result;

/**
 * Created by wanghuiwen on 2020/10/09.
 */
public interface LoginInfoService extends Service<LoginInfo> {
   Result list(String search, String order, Integer page, Integer size);

   LoginInfo getOneByUserId(String userId);
}
