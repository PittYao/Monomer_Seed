package com.bugprovider.seed.modules.ums.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author BugProvider
 * @date 2021/12/19
 * @apiNote excel
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DownloadData {
    private String string;
    private Date date;
    private Double doubleData;
}
