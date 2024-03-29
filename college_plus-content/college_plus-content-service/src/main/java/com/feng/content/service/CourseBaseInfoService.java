package com.feng.content.service;

import com.feng.base.model.PageParams;
import com.feng.base.model.PageResult;
import com.feng.content.model.dto.AddCourseDto;
import com.feng.content.model.dto.CourseBaseInfoDto;
import com.feng.content.model.dto.EditCourseDto;
import com.feng.content.model.dto.QueryCourseParamsDto;
import com.feng.content.model.po.CourseBase;

/**
 * @description: 课程管理接口service
 * @author: SorryMaker
 * @create: 2023-01-16 16:51
 **/
public interface CourseBaseInfoService {
    /**
     * 课程查询
     *
     * @param pageParams           分页参数
     * @param queryCourseParamsDto 查询条件
     * @return
     */
    PageResult<CourseBase> queryCourseBaseList(PageParams pageParams, QueryCourseParamsDto queryCourseParamsDto);

    /**
     * 新增课程
     *
     * @param companyId
     * @param addCourseDto
     * @return
     */
    public CourseBaseInfoDto createCourseBase(Long companyId, AddCourseDto addCourseDto);

    public CourseBaseInfoDto getCourseBaseInfo(Long courseId);

    public CourseBaseInfoDto updateCourseBaseInfo(Long companyId, EditCourseDto dto);
}
