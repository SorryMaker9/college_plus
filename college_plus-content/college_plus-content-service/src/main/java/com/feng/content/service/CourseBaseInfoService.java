package com.feng.content.service;

import com.feng.base.model.PageParams;
import com.feng.base.model.PageResult;
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
     * @param pageParams 分页参数
     * @param queryCourseParamsDto 查询条件
     * @return
     */
    PageResult<CourseBase> queryCourseBaseList(PageParams pageParams, QueryCourseParamsDto queryCourseParamsDto);
}
