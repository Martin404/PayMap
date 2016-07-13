package com.hugnew.core.dao;

import com.github.abel533.mapper.MapperProvider;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * Created by Martin on 2016/7/01.
 */
public interface IBaseMapper<T> {

    @SelectProvider(type = MapperProvider.class, method = "dynamicSQL")
    T selectOne(T record);

    @SelectProvider(type = MapperProvider.class, method = "dynamicSQL")
    List<T> select(T record);

    @SelectProvider(type = MapperProvider.class, method = "dynamicSQL")
    int selectCount(T record);

    @SelectProvider(type = MapperProvider.class, method = "dynamicSQL")
    T selectByPrimaryKey(Object key);

    @InsertProvider(type = MapperProvider.class, method = "dynamicSQL")
    int insert(T record);

    @InsertProvider(type = MapperProvider.class, method = "dynamicSQL")
    int insertSelective(T record);

    @DeleteProvider(type = MapperProvider.class, method = "dynamicSQL")
    int delete(T record);

    @DeleteProvider(type = MapperProvider.class, method = "dynamicSQL")
    int deleteByPrimaryKey(Object key);

    @UpdateProvider(type = MapperProvider.class, method = "dynamicSQL")
    int updateByPrimaryKey(T record);

    @UpdateProvider(type = MapperProvider.class, method = "dynamicSQL")
    int updateByPrimaryKeySelective(T record);

    @SelectProvider(type = MapperProvider.class, method = "dynamicSQL")
    int selectCountByExample(Object example);

    @DeleteProvider(type = MapperProvider.class, method = "dynamicSQL")
    int deleteByExample(Object example);

    @SelectProvider(type = MapperProvider.class, method = "dynamicSQL")
    List<T> selectByExample(Object example);

    @UpdateProvider(type = MapperProvider.class, method = "dynamicSQL")
    int updateByExampleSelective(@Param("record") T record, @Param("example") Object example);

    @UpdateProvider(type = MapperProvider.class, method = "dynamicSQL")
    int updateByExample(@Param("record") T record, @Param("example") Object example);

    List<T> getAllByPage(RowBounds rowBounds);

}
