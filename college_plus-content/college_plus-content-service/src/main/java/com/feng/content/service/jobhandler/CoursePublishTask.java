package com.feng.content.service.jobhandler;

import com.feng.base.exception.CollegePlusException;
import com.feng.content.feignclient.CourseIndex;
import com.feng.content.feignclient.SearchServiceClient;
import com.feng.content.mapper.CoursePublishMapper;
import com.feng.content.model.dto.CoursePreviewDto;
import com.feng.content.model.po.CoursePublish;
import com.feng.content.service.CoursePublishService;
import com.feng.messagesdk.model.po.MqMessage;
import com.feng.messagesdk.service.MessageProcessAbstract;
import com.feng.messagesdk.service.MqMessageService;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @description:
 * @author: SorryMaker
 * @create: 2023-04-14 12:38
 **/
@Slf4j
@Component
public class CoursePublishTask extends MessageProcessAbstract {

    @Autowired
    private CoursePublishService coursePublishService;

    @Autowired
    private SearchServiceClient searchServiceClient;

    @Autowired
    private CoursePublishMapper coursePublishMapper;

    @XxlJob("CoursePublishJobHandler")
    public void coursePublishJobHandler() throws Exception {
        int shardIndex = XxlJobHelper.getShardIndex();
        int shardTotal = XxlJobHelper.getShardTotal();
        process(shardIndex,shardTotal,"course_publish",30,60);
    }

    //执行课程发布任务的逻辑
    @Override
    public boolean execute(MqMessage mqMessage) {
        //从mqMessage拿到课程id
        Long courseId = Long.parseLong(mqMessage.getBusinessKey1());
        //课程静态化上传minio

        generateCourseHtml(mqMessage, courseId);
        //向elasticsearch写索引数据

        saveCourseIndex(mqMessage, courseId);
        //向Redis写缓存


        //返回true,表示任务已完成
        return true;
    }

    private void generateCourseHtml(MqMessage mqMessage, Long courseId) {
        Long taskId = mqMessage.getId();
        MqMessageService mqMessageService = this.getMqMessageService();
        //任务幂等性处理

        int stageOne = mqMessageService.getStageOne(taskId);
        if (stageOne > 0) {
            log.debug("课程静态化任务完成,无需处理...");
            return;
        }
        //开始进行课程静态化
        File file = coursePublishService.generateCourseHtml(courseId);
        if(file == null){
            CollegePlusException.cast("生成静态页面为空");
        }
        coursePublishService.uploadCourseHtml(courseId,file);
        //任务处理完成
        mqMessageService.completedStageOne(taskId);
    }

    //保存课程索引信息
    private void saveCourseIndex(MqMessage mqMessage, Long courseId) {
        Long taskId = mqMessage.getId();
        MqMessageService mqMessageService = this.getMqMessageService();
        //任务幂等性处理
        int stageTwo = mqMessageService.getStageTwo(taskId);
        if (stageTwo > 0) {
            log.debug("课程索引信息已写入,无需执行...");
            return;
        }

        //查询课程
        CoursePublish coursePublish = coursePublishMapper.selectById(courseId);

        CourseIndex courseIndex = new CourseIndex();
        BeanUtils.copyProperties(coursePublish,courseIndex);
        Boolean add = searchServiceClient.add(courseIndex);

        if(!add){
            CollegePlusException.cast("远程调用搜索添加课程索引失败");
        }

        mqMessageService.completedStageTwo(courseId);
    }
}