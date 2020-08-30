package com.fty.onlinecar.service;
import com.fty.onlinecar.entity.IntegralDetail;
import com.fty.onlinecar.base.service.Service;
import com.fty.onlinecar.entity.Users;
import com.fty.onlinecar.response.Result;

/**
 * Created by wanghuiwen on 2020/08/20.
 */
public interface IntegralDetailService extends Service<IntegralDetail> {
   Result list(String search, String order, Integer page, Integer size);

   void addIntegral(Users users,Integer num, String detail);
   void lessIntegral(Users users,Integer num, String detail);
}
