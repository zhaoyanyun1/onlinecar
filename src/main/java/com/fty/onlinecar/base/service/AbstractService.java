package com.fty.onlinecar.base.service;


import com.fty.onlinecar.base.dao.BaseDao;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Condition;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * 基于通用MyBatis Mapper插件的Service接口的实现
 */
public abstract class AbstractService<T> implements Service<T> {

    @Autowired
    protected BaseDao<T> baseDao;

    private Class<T> modelClass;    // 当前泛型真实类型的Class

    public AbstractService() {
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        modelClass = (Class<T>) pt.getActualTypeArguments()[0];
    }

    @Override
    public void save(T model) {
        baseDao.insertSelective(model);
    }

    @Override
    public void save(List<T> models) {
        baseDao.insertList(models);
    }

    @Override
    public void deleteById(Object id) {
        baseDao.deleteByPrimaryKey(id);
    }

    @Override
    public void deleteByIds(String ids) {
        baseDao.deleteByIds(ids);
    }

    @Override
    public void update(T model) {
        baseDao.updateByPrimaryKeySelective(model);
    }

    @Override
    public T findById(Object id) {
        return baseDao.selectByPrimaryKey(id);
    }

    @Override
    public T findBy(String fieldName, Object value) throws TooManyResultsException {
        try {
            T model = modelClass.newInstance();
            Field field = modelClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(model, value);
            return baseDao.selectOne(model);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public List<T> findByIds(String ids) {
        return baseDao.selectByIds(ids);
    }

    @Override
    public List<T> findByCondition(Condition condition) {
        return baseDao.selectByCondition(condition);
    }

    @Override
    public List<T> findAll() {
        return baseDao.selectAll();
    }
}
