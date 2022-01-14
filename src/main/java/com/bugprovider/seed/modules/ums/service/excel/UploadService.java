package com.bugprovider.seed.modules.ums.service.excel;

import com.bugprovider.seed.modules.ums.dto.UploadData;

import java.util.List;

/**
 * @author BugProvider
 * @date 2022/1/14
 * @apiNote
 */
public interface UploadService {
    void save(List<UploadData> uploadData);
}
