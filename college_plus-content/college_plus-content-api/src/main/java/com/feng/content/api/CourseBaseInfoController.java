package com.feng.content.api;

import com.feng.base.exception.ValidationGroups;
import com.feng.base.model.PageParams;
import com.feng.base.model.PageResult;
import com.feng.content.model.dto.*;
import com.feng.content.model.po.CourseBase;
import com.feng.content.model.po.CourseMarket;
import com.feng.content.service.CourseBaseInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Mr.M
 * @version 1.0
 * @description TODO
 * @date 2022/10/7 16:22
 */
@Api(value = "课程管理接口", tags = "课程管理接口")
@Slf4j
@RestController
public class CourseBaseInfoController {

    @Autowired
    private CourseBaseInfoService courseBaseInfoService;

    @ApiOperation("课程查询接口")
    @PostMapping("/course/list")
    public PageResult<CourseBase> list(PageParams params, @RequestBody QueryCourseParamsDto queryCourseParamsDto) {
        PageResult<CourseBase> courseBasePageResult = courseBaseInfoService.queryCourseBaseList(params, queryCourseParamsDto);
        return courseBasePageResult;
    }

    @ApiOperation("新增课程")
    @PostMapping(value = "/course")
    public CourseBaseInfoDto createCourseBase(@RequestBody @Validated(ValidationGroups.Insert.class) AddCourseDto addCourseDto){
        //获取当前用户所属培训机构的id
        Long companyId = 22L;

        //调用Service
        CourseBaseInfoDto courseBase = courseBaseInfoService.createCourseBase(companyId, addCourseDto);
        return courseBase;
    }

    @ApiOperation("根据课程ID查询课程基础信息")
    @GetMapping(value = "/course/{courseId}")
    public CourseBaseInfoDto getCourseBaseById(@PathVariable Long courseId){

        return courseBaseInfoService.getCourseBaseInfo(courseId);
    }

    @ApiOperation("根据课程ID修改课程基础信息")
    @PutMapping(value = "/course")
    public CourseBaseInfoDto ModifyCourseBaseById(@RequestBody EditCourseDto editCourseDto){
        Long companyId = 1232141425L;
        return courseBaseInfoService.updateCourseBaseInfo(companyId,editCourseDto);
    }


}
