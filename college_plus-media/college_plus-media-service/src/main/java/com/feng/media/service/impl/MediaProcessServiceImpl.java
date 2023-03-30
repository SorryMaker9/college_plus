package com.feng.media.service.impl;

import com.feng.media.mapper.MediaProcessMapper;
import com.feng.media.model.po.MediaProcess;
import com.feng.media.service.MediaProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description:
 * @author: SorryMaker
 * @create: 2023-03-29 23:16
 **/
@Service
public class MediaProcessServiceImpl implements MediaProcessService {
    @Autowired
    private MediaProcessMapper mediaProcessMapper;
    @Override
    public List<MediaProcess> getMediaProcessList(int sharedTotal, int sharedIndex, int count) {
        return mediaProcessMapper.selectListBySharedIndex(sharedTotal,sharedIndex,count);
    }
}