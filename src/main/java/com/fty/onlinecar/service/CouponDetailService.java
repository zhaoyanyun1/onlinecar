package com.fty.onlinecar.service;
import com.fty.onlinecar.entity.CouponDetail;
import com.fty.onlinecar.base.service.Service;
import com.fty.onlinecar.response.Result;

import java.util.List;
import java.util.Map;

/**
 * Created by wanghuiwen on 2020/08/24.
 */
public interface CouponDetailService extends Service<CouponDetail> {
   Result list(String search, String order, Integer page, Integer size);

   List<Map<String, Object>> passengerCouponlist(Map<String, Object> params);
   List<Map<String, Object>> findAvailableByUserId(Integer userId);

}
