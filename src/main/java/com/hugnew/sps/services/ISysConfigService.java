package com.hugnew.sps.services;

import com.hugnew.core.service.IBaseService;
import com.hugnew.sps.dao.domain.SysConfig;
import com.hugnew.sps.dto.DDDetails;

import java.util.List;

/**
 * 字典业务接口
 * Created by Martin on 2016/7/01.
 */
public interface ISysConfigService extends IBaseService<SysConfig> {

    /**
     * 获取字典数据
     * @param sysKey 字典表中对应的记录key
     * @param id 字典id
     * @return dictionary
     */
    public List<DDDetails> getDictionary(String sysKey, String id);

    /**
     * 增加字典数据
     * @param sysKey 字典表中对应的记录key
     * @param ddDetails 字典数据内容
     * @param accountId the account id
     */
    public void txaddDictionary(String sysKey, DDDetails ddDetails, Long accountId);

    /**
     * 更新字典数据
     * @param sysKey 字典表中对应的记录key
     * @param ddDetails 字典数据
     * @param accountId the account id
     */
    public void txupdateDictionary(String sysKey, DDDetails ddDetails, Long accountId);

    /**
     * 删除字典数据
     * @param sysKey 字典表中对应的记录key
     * @param id 字典数据id
     * @param accountId the account id
     */
    public void deleteDictionary(String sysKey, String id, Long accountId);

    /**
     * 获取需要缓存的字典列表
     * @return
     */
    public List<SysConfig> getSysConfigList();

    /**
     * 更新系统配置
     * @param key 主键
     * @param value 值
     * @return 更新结果
     */
    public Integer updateSysConfig(String key, String value);

    /**
     * 刷新字典缓存
     */
    public void flushDictCache();
    /**
     * 根据key获取系統參數值
     */
    public String getValueByKey(String key);

}
