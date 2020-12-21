package com.fty.onlinecar.service;

import com.fty.onlinecar.entity.LoginInfo;
import com.fty.onlinecar.base.service.Service;
import com.fty.onlinecar.response.Result;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by wanghuiwen on 2020/10/09.
 */
public interface LoginInfoService extends Service<LoginInfo> {
   Result list(String search, String order, Integer page, Integer size);

   LoginInfo getOneByUserId(String userId);

   List<LoginInfo> findByUserId(@Param("userId") String userId);
}
