package com.fty.onlinecar.dao;

import com.fty.onlinecar.base.dao.BaseDao;
import com.fty.onlinecar.entity.TripDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface TripDetailMapper extends BaseDao<TripDetail> {
    List<Map<String,Object>> driverTriplist();

    TripDetail findCurTripByDriver(@Param("params") Map<String,Object> params);
    TripDetail findCurTripByPassenger(@Param("params") Map<String,Object> params);

    List<Map<String, Object>> findPeersPassenger(Integer tripId);
}