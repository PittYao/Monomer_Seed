package com.bugprovider.seed.common.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 打点信息枚举
 *
 */
@Getter
@ToString
@AllArgsConstructor
public enum AnchorState {

    RECEIVE(10, "'用户模块成功接收请求'"),

    ;


    private Integer code;
    private String description;

}
