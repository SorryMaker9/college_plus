package com.feng.media.service.impl;

import com.feng.media.mapper.MediaFilesMapper;
import com.feng.media.mapper.MediaProcessHistoryMapper;
import com.feng.media.mapper.MediaProcessMapper;
import com.feng.media.model.po.MediaFiles;
import com.feng.media.model.po.MediaProcess;
import com.feng.media.model.po.MediaProcessHistory;
import com.feng.media.service.MediaProcessService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    @Autowired
    private MediaFilesMapper mediaFilesMapper;

    @Autowired
    private MediaProcessHistoryMapper mediaProcessHistoryMapper;

    @Override
    public List<MediaProcess> getMediaProcessList(int sharedTotal, int sharedIndex, int count) {
        return mediaProcessMapper.selectListBySharedIndex(sharedTotal, sharedIndex, count);
    }

    @Override
    public boolean startTask(Long id) {
        return mediaProcessMapper.startTask(id) > 0;
    }

    @Override
    public void saveProcessFinishStatus(Long taskId, String status, String fileId, String url, String errorMsg) {
        MediaProcess mediaProcess = mediaProcessMapper.selectById(taskId);
        if (mediaProcess == null) {
            return;
        }
        //如果任务执行失败
        if ("3".equals(status)) {
            //更新MediaProcess表的状态
            mediaProcess.setStatus("3");
            mediaProcess.setFailCount(mediaProcess.getFailCount() + 1);
            mediaProcess.setErrormsg(errorMsg);
            mediaProcessMapper.updateById(mediaProcess);
            return;
        }
        //如果任务执行成功
        MediaFiles mediaFiles = mediaFilesMapper.selectById(fileId);
        mediaFiles.setUrl(url);
        mediaFilesMapper.updateById(mediaFiles);

        mediaProcess.setStatus("2");
        mediaProcess.setFinishDate(LocalDateTime.now());
        mediaProcess.setUrl(url);
        mediaProcessMapper.updateById(mediaProcess);

        MediaProcessHistory mediaProcessHistory = new MediaProcessHistory();
        BeanUtils.copyProperties(mediaProcess,mediaProcessHistory);
        mediaProcessHistoryMapper.insert(mediaProcessHistory);
        mediaProcessMapper.deleteById(taskId);
    }
}