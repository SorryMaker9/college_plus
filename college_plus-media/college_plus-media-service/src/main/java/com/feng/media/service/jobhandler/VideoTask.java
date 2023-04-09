package com.feng.media.service.jobhandler;

import com.feng.base.utils.Mp4VideoUtil;
import com.feng.media.model.po.MediaProcess;
import com.feng.media.service.MediaFileService;
import com.feng.media.service.MediaProcessService;
import com.feng.media.service.impl.MediaProcessServiceImpl;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 任务处理类
 *
 * @author xuxueli 2019-12-11 21:52:51
 */
@Component
@Slf4j
public class VideoTask {

    @Autowired
    private MediaProcessService mediaProcessService;

    @Value("${videoprocess.ffmpegpath}")
    String ffmpeg_path;
    private static Logger logger = LoggerFactory.getLogger(VideoTask.class);

    @Autowired
    private MediaFileService mediaFileService;

    /**
     * 2、视频处理任务
     */
    @XxlJob("videoJobHandler")
    public void videoJobHandler() throws Exception {
        int sharedIndex = XxlJobHelper.getShardIndex();//序号
        int sharedTotal = XxlJobHelper.getShardTotal();//总数

        //确定CPU核心数
        int processors = Runtime.getRuntime().availableProcessors();
        //查询待处理的任务
        List<MediaProcess> mediaProcessList = mediaProcessService.getMediaProcessList(sharedTotal, sharedIndex, processors);
        //创建一个线程池
        int size = mediaProcessList.size();
        log.debug("取到的视频处理任务数:" + size);
        if (size <= 0) {
            return;
        }
        ExecutorService executorService = Executors.newFixedThreadPool(size);
        CountDownLatch countDownLatch = new CountDownLatch(size);
        //将任务加入线程池
        mediaProcessList.forEach(mediaProcess -> {
            executorService.execute(() -> {
                //任务执行逻辑
                try {
                    Long taskId = mediaProcess.getId();
                    boolean b = mediaProcessService.startTask(taskId);
                    if (!b) {
                        log.debug("抢占任务失败,任务Id:{}", taskId);
                        return;
                    }
                    String fileId = mediaProcess.getFileId();
                    //进行视频转码
                    String bucket = mediaProcess.getBucket();
                    String objectName = mediaProcess.getFilePath();
                    //下载视频
                    File file = mediaFileService.downloadFileFromMinIO(bucket, objectName);
                    if (file == null) {
                        log.debug("下载视频出错,任务id:{},bucket:{},objectName:{}", taskId, bucket, objectName);
                        mediaProcessService.saveProcessFinishStatus(taskId, "3", fileId, null, "下载视频到本地失败");
                        return;
                    }

                    String video_path = file.getAbsolutePath();
                    String mp4_name = fileId + ".mp4";
                    File mp4File = null;
                    File file1 = new File("F:\\temp");
                    try {
                        mp4File = File.createTempFile("minio", ".mp4",file1);
                    } catch (IOException e) {
                        log.debug("创建临时文件异常,{}", e.getMessage());
                        mediaProcessService.saveProcessFinishStatus(taskId, "3", fileId, null, "创建临时文件异常");
                        return;
                    }
                    String mp4_path = mp4File.getAbsolutePath();
                    Mp4VideoUtil videoUtil = new Mp4VideoUtil(ffmpeg_path, video_path, mp4_name, mp4_path);
                    String result = videoUtil.generateMp4();
                    if (!"success".equals(result)) {
                        log.debug("视频转码失败,原因:{},bucket:{},objectName:{}", result, bucket, objectName);
                        mediaProcessService.saveProcessFinishStatus(taskId, "3", fileId, null, result);
                        return;
                    }
                    if ("success".equals(result)) {
                        //
                        String substring = objectName.substring(0, objectName.lastIndexOf("."));
                        String newObjectName = substring + ".mp4";
                        try {
                            mediaFileService.addMediaFilesToMinio(mp4_path, bucket, newObjectName);
                        } catch (Exception e) {
                            log.debug("上传文件失败,taskId:{},", taskId);
                            mediaProcessService.saveProcessFinishStatus(taskId, "3", fileId, null, "上传文件失败");
                            return;
                        }

                        String url = "/"+bucket+"/"+objectName;
                        mediaProcessService.saveProcessFinishStatus(taskId, "2", fileId, url, "创建临时文件异常");
                    }
                }finally {
                    countDownLatch.countDown();
                }
            });
        });
        //阻塞
        countDownLatch.await(30, TimeUnit.MINUTES);
    }

    private String getFilePath(String fileMd5, String fileExt) {
        return fileMd5.substring(0, 1) + "/" + fileMd5.substring(1, 2) + "/" + fileMd5 + "/" + fileMd5 + fileExt;
    }
}
