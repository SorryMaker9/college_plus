package com.feng.media.service;

import com.feng.media.model.po.MediaProcess;

import java.util.List;

/**
 * @description:任务chuli
 * @author: SorryMaker
 * @create: 2023-03-29 23:14
 **/
public interface MediaProcessService {
    public List<MediaProcess> getMediaProcessList(int sharedTotal, int sharedIndex, int count);
}
