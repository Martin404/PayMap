package com.hugnew.sps.services.impls;

import com.alibaba.fastjson.JSON;
import com.hugnew.core.common.exception.BusinessException;
import com.hugnew.core.common.exception.SystemException;
import com.hugnew.core.util.StringUtil;
import com.hugnew.sps.dao.domain.BaseDomain;
import com.hugnew.sps.dto.DDDetails;
import com.hugnew.sps.services.ICacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 缓存业务
 * Created by Martin on 2016/7/01.
 */
@Service
public class CacheService implements ICacheService {

    private static Logger logger = LoggerFactory.getLogger(CacheService.class);
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public <T> T getCacheByKey(String key, Class<T> clazz) {
        ValueOperations valueOper = redisTemplate.opsForValue();
        return JSON.parseObject((String) valueOper.get(key), clazz);
    }

    @Override
    public <T> List<T> getListCacheByKey(String key, Class<T> clazz) {
        ValueOperations valueOper = redisTemplate.opsForValue();
        return JSON.parseArray((String) valueOper.get(key), clazz);
    }

    @Override
    public <T> void setCacheByKey(String key, T obj) {
        ValueOperations valueOper = redisTemplate.opsForValue();
        valueOper.set(key, JSON.toJSONString(obj));
    }

    @Override
    public List<DDDetails> getDictEntriesByKey(String dictKey) {
        HashOperations<String, Object, Object> opsForHash = redisTemplate.opsForHash();
        Map<Object, Object> entries = opsForHash.entries(dictKey);
        List<DDDetails> ddDetailsList = new ArrayList<>();
        for (Map.Entry<Object, Object> entry : entries.entrySet()) {
            DDDetails ddDetails = new DDDetails();
            ddDetails.setId(entry.getKey().toString());
            ddDetails.setName((String) entry.getValue());
            ddDetailsList.add(ddDetails);
        }
        return ddDetailsList;
    }

    @Override
    public Map<String, String> getDictMapByKey(String dictKey) {
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();

        return opsForHash.entries(dictKey);
    }

    @Override
    public String getDictEntryByKey(String dictKey, Object entryKey) {
        HashOperations<String, String, Object> opsForHash = redisTemplate.opsForHash();
        String entryVal = (String) opsForHash.get(dictKey, entryKey.toString());
        return entryVal;

    }

    @Override
    public <T> void setToHashTable(String tableName, String key, T value) {
        HashOperations<String, String, T> hashOperations = redisTemplate.opsForHash();
        hashOperations.put(tableName, key, value);
    }

    @Override
    public <T> T getFromHashTable(String tableName, String key) throws SystemException {
        HashOperations<String, String, T> hashOperations = redisTemplate.opsForHash();

        return hashOperations.get(tableName, key);
    }

    @Override
    public <T> Set<String> getAllKeysFromHashTable(String tableName) {
        HashOperations<String, String, T> hashOperations = redisTemplate.opsForHash();
        return hashOperations.keys(tableName);
    }

    @Override
    public <T> void setToHashTableUseJson(String tableName, String key, T value) {
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        hashOperations.put(tableName, key, JSON.toJSONString(value));
    }

    @Override
    public <T> T getObjectFromHashTableUseJson(String tableName, String key, Class<T> clazz) throws SystemException {
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        String obj = hashOperations.get(tableName, key);
        if (obj != null) {
            return JSON.parseObject(obj, clazz);
        } else {
            return null;
        }
    }

    @Override
    public <T> List<T> getListFromHashTableUseJson(String tableName, String key, Class<T> clazz) throws SystemException {
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        String obj = hashOperations.get(tableName, key);
        if (obj != null) {
            return JSON.parseArray(obj, clazz);
        } else {
            return null;
        }
    }

    @Override
    public <T> List<T> getListFromHashTableUseJson(String tableName, List<String> keys, Class<T> clazz) throws SystemException {
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        List<T> result = null;
        List<String> objList = hashOperations.multiGet(tableName, keys);
        if (objList != null && !objList.isEmpty()) {
            result = new ArrayList<>();
            for (String s : objList) {
                result.add(JSON.parseObject(s, clazz));
            }
        }
        return result;
    }

    @Override
    public <T> List<T> getFromHashTable(String tableName, List<String> keys) throws SystemException {
        HashOperations<String, String, T> hashOperations = redisTemplate.opsForHash();
        return hashOperations.multiGet(tableName, keys);
    }

    @Override
    public void removeFromHashTable(String tableName, String... keys) {
        HashOperations<String, String, Object> hashOperations = redisTemplate.opsForHash();
        hashOperations.delete(tableName, keys);
    }

    @Override
    public boolean keyExistInHashTable(String tableName, String key) {
        HashOperations<String, String, Object> hashOperations = redisTemplate.opsForHash();
        return hashOperations.hasKey(tableName, key);
    }

    @Override
    public void delKeyFromRedis(String key) {
        if (StringUtil.isEmpty(key)) {
            throw new BusinessException("参数不能为空");
        }
        redisTemplate.delete(key);
    }

}
