package com.fty.onlinecar.service;
import com.fty.onlinecar.entity.BalanceDetail;
import com.fty.onlinecar.base.service.Service;
import com.fty.onlinecar.entity.Users;
import com.fty.onlinecar.response.Result;

/**
 * Created by wanghuiwen on 2020/08/20.
 */
public interface BalanceDetailService extends Service<BalanceDetail> {
   Result list(String search, String order, Integer page, Integer size);

   void lessen(Users users,int money,String detail);
}
