package com.bugprovider.seed.modules.ums.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author BugProvider
 * @date 2021/12/19
 * @apiNote
 */
@Getter
@Setter
@EqualsAndHashCode
public class UploadData {
    private String string;
    private Date date;
    private Double doubleData;
}