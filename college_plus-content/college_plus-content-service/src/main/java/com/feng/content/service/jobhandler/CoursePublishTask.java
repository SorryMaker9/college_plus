package com.feng.content.service.jobhandler;

import com.feng.messagesdk.model.po.MqMessage;
import com.feng.messagesdk.service.MessageProcessAbstract;
import com.feng.messagesdk.service.MqMessageService;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: SorryMaker
 * @create: 2023-04-14 12:38
 **/
@Slf4j
@Component
public class CoursePublishTask extends MessageProcessAbstract {

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
        int i = 1 / 0;
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

        mqMessageService.completedStageTwo(courseId);
    }
}