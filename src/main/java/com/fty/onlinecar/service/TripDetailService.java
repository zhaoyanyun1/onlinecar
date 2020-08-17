package com.fty.onlinecar.service;
import com.fty.onlinecar.entity.TripDetail;
import com.fty.onlinecar.base.service.Service;
import com.fty.onlinecar.response.Result;

import java.util.List;
import java.util.Map;

/**
 * Created by wanghuiwen on 2020/08/13.
 */
public interface TripDetailService extends Service<TripDetail> {
   Result list(String search, String order, Integer page, Integer size);

   List<Map<String,Object>> tripDetailService();

   void confirmTrip(TripDetail tripDetail,TripDetail pTripDetail);
}
