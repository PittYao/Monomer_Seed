package com.bugprovider.seed.common.service;

import java.util.List;

/**
 * redis操作Service
 */
public interface RedisService {

    /**
     * 保存属性
     */
    void set(String key, String value, long time);

    /**
     * 保存属性
     */
    void set(String key, String value);

    /**
     * 获取属性
     */
    String get(String key);

    /**
     * 删除属性
     */
    Boolean del(String key);

    /**
     * 批量删除属性
     */
    Long del(List<String> keys);

    /**
     * 设置过期时间
     */
    Boolean expire(String key, long time);

    /**
     * 获取过期时间
     */
    Long getExpire(String key);

    /**
     * 判断是否有该属性
     */
    Boolean hasKey(String key);


}