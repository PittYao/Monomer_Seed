package com.bugprovider.seed.modules.ums.service.excel;

import com.bugprovider.seed.modules.ums.dto.UploadData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author BugProvider
 * @date 2022/1/14
 * @apiNote
 */
@Slf4j
@Service
public class UploadServiceImpl implements UploadService {

    @Override
    public void save(List<UploadData> uploadData) {
        log.info("save data ....");
    }
}
