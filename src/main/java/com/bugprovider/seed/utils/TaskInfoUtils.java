package com.bugprovider.seed.utils;

import cn.hutool.core.util.IdUtil;

/**
 * 生成 消息id 工具类
 *
 */
public class TaskInfoUtils {

    private static final int TYPE_FLAG = 1000000;

    /**
     * 生成BusinessId
     * 模板类型+模板ID+当天日期
     * (固定16位)
     */
    public static Long generateBusinessId() {
        return Long.valueOf(IdUtil.fastSimpleUUID());
    }



}
