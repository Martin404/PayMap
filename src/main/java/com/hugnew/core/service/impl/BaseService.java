package com.hugnew.core.service.impl;

import com.github.pagehelper.PageInfo;
import com.hugnew.core.common.exception.DBException;
import com.hugnew.core.dao.IBaseMapper;
import com.hugnew.core.service.IBaseService;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Martin on 2016/7/01.
 */
@Service
public abstract class BaseService<T> implements IBaseService<T> {

    private static Logger logger = LoggerFactory.getLogger(BaseService.class);
    @Resource
    protected RabbitTemplate amqpTemplate;
    @Autowired
    protected RedisTemplate redisTemplate;

    public abstract IBaseMapper<T> getBaseMapper();

    /**
     * 根据主键查询指定实体
     * @param id
     * @return
     */
    @Override
    public T getById(Object id) {
        return this.getBaseMapper().selectByPrimaryKey(id);
    }

    /**
     * 列表查询
     * @param entity
     * @return
     */
    @Override
    public List<T> getByEntity(T entity) {
        return this.getBaseMapper().select(entity);
    }

    /**
     * 获取分页数据
     * @param rowBounds
     * @return
     */
    @Override
    public PageInfo<T> getByPage(RowBounds rowBounds) {
        List<T> list = this.getBaseMapper().getAllByPage(rowBounds);
        return new PageInfo<T>(list);
    }

    /**
     * 保存对象，保存所有属性
     * @param entity
     * @return
     */
    @Override
    public int save(T entity) {
        return this.getBaseMapper().insert(entity);
    }

    /**
     * 更新对象中所有属性,主键不能为NULL
     * @param entity
     * @return
     */
    @Override
    public int update(T entity) {
        return this.getBaseMapper().updateByPrimaryKey(entity);
    }

    /**
     * 删除指定数据
     * @param id
     * @return
     */
    @Override
    public int delete(Object id) {
        return this.getBaseMapper().deleteByPrimaryKey(id);
    }

    /**
     * 保存对象，只保存对象中不为NULL的属性
     * @param entity
     * @return
     */
    @Override
    public int saveSelective(T entity) throws DBException {
        int result = this.getBaseMapper().insertSelective(entity);
        if (result <= 0) {
            throw new DBException("数据保存异常");
        }
        return result;
    }

    /**
     * 更新对象，只更新对象中不为NULL的属性，主键不能为NULL
     * @param entity
     * @return
     */
    @Override
    public int updateSelective(T entity) {
        return this.getBaseMapper().updateByPrimaryKeySelective(entity);
    }

}
