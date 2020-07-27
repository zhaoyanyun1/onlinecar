package com.fty.onlinecar.base;

import org.apache.ibatis.annotations.SelectProvider;
import tk.mybatis.mapper.annotation.RegisterMapper;

import java.util.List;
import java.util.Map;

@RegisterMapper
public interface BaseList<T> {

    @SelectProvider(
            type = BaseListProvider.class,
            method = "dynamicSQL"
    )
    List<Map<String, Object>> baseList(Map<String, Object> params, Map<String, Object> order);
}
