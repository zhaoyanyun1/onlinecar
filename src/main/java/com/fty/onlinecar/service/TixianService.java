package com.fty.onlinecar.service;
import com.fty.onlinecar.entity.Tixian;
import com.fty.onlinecar.base.service.Service;
import com.fty.onlinecar.response.Result;
import com.fty.onlinecar.response.Table;
import com.github.pagehelper.Page;

import java.util.Map;

/**
 * Created by wanghuiwen on 2020/12/21.
 */
public interface TixianService extends Service<Tixian> {
   Page<Map<String, Object>> list(String search, String order, Integer page, Integer size);
}
