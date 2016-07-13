package com.hugnew.sps.dao;

import com.hugnew.core.dao.IBaseMapper;
import com.hugnew.sps.dao.domain.SysConfig;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Martin on 2016/7/01.
 */
public interface SysConfigMapper extends IBaseMapper<SysConfig> {

    public Integer updateSysConfig(@Param("key") String key, @Param("sysValue") String sysValue);

    public List<SysConfig> getAll();

}