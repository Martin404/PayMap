package com.hugnew.sps.services;

import com.hugnew.core.common.exception.SystemException;
import com.hugnew.sps.dto.DDDetails;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 缓存业务接口
 * Created by Martin on 2016/7/01.
 */
public interface ICacheService {

    /**
     * 获取指定缓存对象,不能用作获取缓存List对象-List用getListCacheByKey方法
     * @param key   the key
     * @param clazz the clazz 不能传集合类型
     * @return cache by key
     */
    public <T> T getCacheByKey(String key, Class<T> clazz);

    /**
     * 获取指定缓存列表对象,不能用作获取非列表对象
     * @param key   the key
     * @param clazz the clazz List中对象的类型
     * @return the list cache by key
     */
    <T> List<T> getListCacheByKey(String key, Class<T> clazz);

    /**
     * 设置缓存
     * @param key the key
     * @param obj the obj
     */
    public <T> void setCacheByKey(String key, T obj);

    /**
     * 获取指定字典的字典项列表
     * @param dictKey the dict key
     * @return dict entries by key
     */
    public List<DDDetails> getDictEntriesByKey(String dictKey);

    /**
     * 获取 dict map by key.
     * @param dictKey the dict key
     * @return the dict map by key
     */
    Map<String, String> getDictMapByKey(String dictKey);

    /**
     * 获取指定字典的字典项值
     * @param dictKey  the dict key
     * @param entryKey the entry key
     * @return dict entry by key
     */
    public String getDictEntryByKey(String dictKey, Object entryKey);


    /**
     * 将字符串保存到缓存的HashTable中
     * @param tableName the table name
     * @param key       the key
     * @param value     the value
     */
    public <T> void setToHashTable(String tableName, String key, T value);

    /**
     * 从缓存的HashTable中获取字符串
     * @param tableName the table name
     * @param key       the key
     * @return the from hash table
     */
    public <T> T getFromHashTable(String tableName, String key) throws SystemException;

    /**
     * 根据tableName获取指定hash类型的数据集合
     * @param tableName
     * @param <T>
     * @return
     */
    <T> Set<String> getAllKeysFromHashTable(String tableName);

    /**
     * 根据tableName和key集合获取指定hash类型的数据集合
     * @param tableName
     * @param keys
     * @param <T>
     * @return
     * @throws SystemException
     */
    public <T> List<T> getFromHashTable(String tableName, List<String> keys) throws SystemException;

    /**
     * JSON序列化存储指定hash类型的数据
     * @param tableName
     * @param key
     * @param value
     * @param <T>
     */
    public <T> void setToHashTableUseJson(String tableName, String key, T value);

    /**
     * 获取指定hash类型的由JSON反序列化后的实体数据
     * @param tableName
     * @param key
     * @param clazz
     * @param <T>
     * @return
     * @throws SystemException
     */
    public <T> T getObjectFromHashTableUseJson(String tableName, String key, Class<T> clazz) throws SystemException;

    /**
     * 获取指定hash类型的由JSON反序列化后的实体数据集合
     * @param tableName
     * @param key
     * @param clazz
     * @param <T>
     * @return
     * @throws SystemException
     */
    public <T> List<T> getListFromHashTableUseJson(String tableName, String key, Class<T> clazz) throws SystemException;

    /**
     * 获取指定hash类型的由JSON反序列化后的实体数据集合
     * @param tableName
     * @param key
     * @param clazz
     * @param <T>
     * @return
     * @throws SystemException
     */
    public <T> List<T> getListFromHashTableUseJson(String tableName, List<String> key, Class<T> clazz) throws SystemException;


    /**
     * 从缓存HashTable中删除字符串
     * @param tableName the table name
     * @param keys      the keys
     */
    public void removeFromHashTable(String tableName, String... keys);

    /**
     * 从HashTable中移除关键字
     * @param tableName the table name
     * @param key       the key
     * @return the boolean
     */
    boolean keyExistInHashTable(String tableName, String key);

    /**
     * 从redis中删除key
     * @param key the key
     */
    void delKeyFromRedis(String key);

}
