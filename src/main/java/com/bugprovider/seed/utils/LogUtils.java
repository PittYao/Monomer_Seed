package com.bugprovider.seed.utils;

import cn.monitor4all.logRecord.bean.LogDTO;
import cn.monitor4all.logRecord.service.CustomLogListener;
import com.alibaba.fastjson.JSON;
import com.bugprovider.seed.common.domain.AnchorInfo;
import com.bugprovider.seed.common.domain.LogParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 记录日志工具
 * 继承CustomLogListener类，可直接获取logDTO日志，不依赖于配置数据管道
 */
@Slf4j
@Component
public class LogUtils extends CustomLogListener {

    /**
     * 方法切面的日志 @OperationLog 所产生
     */
    @Override
    public void createLog(LogDTO logDTO) {
        log.info("本地接收到日志 [{}]", logDTO);
        // TODO 存储日志
    }

    /**
     * 记录当前对象信息
     */
    public static void print(LogParam logParam) {
        logParam.setTimestamp(System.currentTimeMillis());
        log.info(JSON.toJSONString(logParam));
    }

    /**
     * 记录打点信息
     */
    public static void print(AnchorInfo anchorInfo) {
        anchorInfo.setTimestamp(System.currentTimeMillis());
        log.info(JSON.toJSONString(anchorInfo));
    }

    /**
     * 记录当前对象信息和打点信息
     */
    public static void print(LogParam logParam,AnchorInfo anchorInfo) {
        print(anchorInfo);
        print(logParam);
    }
}
