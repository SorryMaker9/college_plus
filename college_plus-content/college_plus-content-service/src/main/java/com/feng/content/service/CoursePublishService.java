package com.feng.content.service;

import com.feng.content.model.dto.CoursePreviewDto;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;

/**
 * @description: 课程发布接口
 * @author: SorryMaker
 * @create: 2023-04-05 17:03
 **/
public interface CoursePublishService {
    /**
     * 获取课程预览信息
     *
     * @param courseId
     * @return
     */
    public CoursePreviewDto getCoursePreviewInfo(Long courseId);

    /**
     * 提交审核
     * @param companyId
     * @param courseId
     */
    public void commitAudit(Long companyId, Long courseId);

    /**
     * 课程发布
     * @param companyId
     * @param courseId
     */
    public void publish(Long companyId,Long courseId);

    /**
     * 课程静态化
     * @param courseId
     * @return
     */
    public File generateCourseHtml(Long courseId);

    /**
     * 上传课程静态化页面
     * @param courseId
     * @param file
     */
    public void uploadCourseHtml(Long courseId, File file);
}
