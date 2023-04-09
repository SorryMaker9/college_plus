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

    /**
     * 保存任务结果
     * @param taskId
     * @param status
     * @param fileId
     * @param url
     * @param errorMsg
     */
    public void saveProcessFinishStatus(Long taskId,String status,String fileId,String url,String errorMsg);

    boolean startTask(Long id);
}
