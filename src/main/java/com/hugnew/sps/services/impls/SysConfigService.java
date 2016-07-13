package com.hugnew.sps.services.impls;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.hugnew.core.common.exception.BusinessException;
import com.hugnew.core.dao.IBaseMapper;
import com.hugnew.core.service.impl.BaseService;
import com.hugnew.core.util.DateUtils;
import com.hugnew.sps.dao.SysConfigMapper;
import com.hugnew.sps.dao.domain.SysConfig;
import com.hugnew.sps.dto.DDDetails;
import com.hugnew.sps.services.ICacheService;
import com.hugnew.sps.services.ISysConfigService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 字典缓存业务
 * Created by Martin on 2016/7/01.
 */
@Service
public class SysConfigService extends BaseService<SysConfig> implements ISysConfigService {

    private static Logger logger = org.slf4j.LoggerFactory.getLogger(SysConfigService.class);
    @Autowired
    private SysConfigMapper sysConfigMapper;
    @Autowired
    private ICacheService iCacheService;

    @Override
    public IBaseMapper<SysConfig> getBaseMapper() {
        return sysConfigMapper;
    }

    @Override
    public List<DDDetails> getDictionary(String sysKey, String id) {
        List<DDDetails> list = new ArrayList<>();
        if (id != null) {
            DDDetails ddDetails = new DDDetails();
            String name = iCacheService.getDictEntryByKey(sysKey, id);
            if (name != null) {
                ddDetails.setId(id);
                ddDetails.setName(name);
                list.add(ddDetails);
            }
        } else {
            list = iCacheService.getDictEntriesByKey(sysKey);
        }
        return list;
    }

    @Override
    public void txaddDictionary(String sysKey, DDDetails ddDetails, Long accountId) {
        HashOperations<String, Object, Object> opsForHash = redisTemplate.opsForHash();
        SysConfig sysConfig = sysConfigMapper.selectByPrimaryKey(sysKey);
        if (sysConfig != null) {
            List<DDDetails> list = JSON.parseArray(sysConfig.getSysValue(), DDDetails.class);
            if (list == null || list.isEmpty()) {
                list.add(ddDetails);
                String sysValue = JSON.toJSONString(list);
                sysConfig.setSysValue(sysValue);
                sysConfig.setAccountId(accountId);
                sysConfig.setModifyTime(DateUtils.getUnixTimestamp());
                sysConfigMapper.updateByPrimaryKeySelective(sysConfig);
                //添加字典项缓存
                opsForHash.put(sysKey, ddDetails.getId().toString(), ddDetails.getName());
            } else {
                Map<String, DDDetails> idMap = new HashMap<String, DDDetails>();
                Map<String, DDDetails> nameMap = new HashMap<String, DDDetails>();
                for (DDDetails ddDetail : list) {
                    idMap.put(ddDetail.getId(), ddDetail);
                    nameMap.put(ddDetail.getName(), ddDetail);

                }
                if (!idMap.containsKey(ddDetails.getId())) {
                    list.add(ddDetails);
                    String sysValue = JSON.toJSONString(list);
                    sysConfig.setSysValue(sysValue);
                    sysConfig.setAccountId(accountId);
                    sysConfig.setModifyTime(DateUtils.getUnixTimestamp());
                    sysConfigMapper.updateByPrimaryKeySelective(sysConfig);
                    //添加字典项缓存
                    opsForHash.put(sysKey, ddDetails.getId().toString(), ddDetails.getName());
                } else {
                    throw new BusinessException("该字典id已存在");
                }
            }
        } else {
            throw new BusinessException("该字典不存在");
        }

    }

    @Override
    public void txupdateDictionary(String sysKey, DDDetails ddDetails, Long accountId) {
        HashOperations<String, Object, Object> opsForHash = redisTemplate.opsForHash();
        SysConfig sysConfig = sysConfigMapper.selectByPrimaryKey(sysKey);
        List<DDDetails> list = JSON.parseArray(sysConfig.getSysValue(), DDDetails.class);
        Map<String, DDDetails> idMap = new HashMap<String, DDDetails>();
        Map<String, DDDetails> nameMap = new HashMap<String, DDDetails>();
        for (DDDetails ddDetail : list) {
            idMap.put(ddDetail.getId(), ddDetail);
            nameMap.put(ddDetail.getName(), ddDetail);

        }
        String id = ddDetails.getId();
        if (!idMap.containsKey(id)) {
            throw new BusinessException("该字典id不存在");
        } else {
            //从数据库中获取的DDDetails
            DDDetails details = idMap.get(id);
            int index = list.indexOf(details);
            //将前台传来的值赋到后台的DDDetails
            details.setName(ddDetails.getName());
            list.set(index, details);
            String sysValue = new Gson().toJson(list);
            sysConfig.setSysValue(sysValue);
            sysConfig.setAccountId(accountId);
            sysConfig.setModifyTime(DateUtils.getUnixTimestamp());
            sysConfigMapper.updateByPrimaryKeySelective(sysConfig);
            //更新字典缓存
            opsForHash.put(sysKey, ddDetails.getId().toString(), ddDetails.getName());
        }
    }

    @Override
    public void deleteDictionary(String sysKey, String id, Long accountId) {
        HashOperations<String, Object, Object> opsForHash = redisTemplate.opsForHash();
        SysConfig sysConfig = sysConfigMapper.selectByPrimaryKey(sysKey);
        List<DDDetails> list = JSON.parseArray(sysConfig.getSysValue(), DDDetails.class);
        for (Iterator<DDDetails> it = list.iterator(); it.hasNext(); ) {
            if (it.next().getId().equals(id)) {
                it.remove();
            }
        }
        String sysValue = new Gson().toJson(list);
        sysConfig.setSysValue(sysValue);
        sysConfig.setAccountId(accountId);
        sysConfig.setModifyTime(DateUtils.getUnixTimestamp());
        sysConfigMapper.updateByPrimaryKeySelective(sysConfig);
        //删除字典项
        opsForHash.delete(sysKey, id);
    }

    @Override
    public void flushDictCache() {
        List<SysConfig> sysConfigList = getSysConfigList();
        if (sysConfigList != null && !sysConfigList.isEmpty()) {
            for (SysConfig sysConfig : sysConfigList) {
                String sysKey = sysConfig.getSysKey();
                List<DDDetails> ddDetailsList = JSON.parseArray(sysConfig.getSysValue(), DDDetails.class);
                HashOperations<String, Object, Object> opsForHash = redisTemplate.opsForHash();
                if (ddDetailsList != null && !ddDetailsList.isEmpty()) {
                    for (DDDetails ddDetails : ddDetailsList) {
                        opsForHash.put(sysKey, ddDetails.getId(), ddDetails.getName());
                    }
                }

            }
        }
    }

    @Override
    public String getValueByKey(String key) {
        SysConfig sysConfig = new SysConfig();
        sysConfig.setSysKey(key);
        List<SysConfig> list = sysConfigMapper.select(sysConfig);
        if (list != null && !list.isEmpty()) {
            return list.get(0).getSysValue();
        }
        return null;
    }

    @Override
    public List<SysConfig> getSysConfigList() {
        return sysConfigMapper.getAll();
    }

    @Override
    public Integer updateSysConfig(String key, String value) {
        return sysConfigMapper.updateSysConfig(key, value);
    }

}