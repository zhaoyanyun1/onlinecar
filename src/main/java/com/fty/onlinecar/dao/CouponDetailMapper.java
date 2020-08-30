package com.fty.onlinecar.dao;

import com.fty.onlinecar.base.dao.BaseDao;
import com.fty.onlinecar.entity.CouponDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface CouponDetailMapper extends BaseDao<CouponDetail> {

    List<Map<String, Object>> passengerCouponlist(@Param("params") Map<String, Object> params);
    List<Map<String, Object>> findAvailableByUserId(Integer userId);
}