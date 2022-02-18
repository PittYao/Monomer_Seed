package com.bugprovider.seed.common.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 埋点信息
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnchorInfo {

    /**
     * 具体点位
     */
    private int state;

    /**
     * 业务Id(数据追踪使用)
     */
    private Long businessId;


    /**
     * 生成时间
     */
    private long timestamp;

}
