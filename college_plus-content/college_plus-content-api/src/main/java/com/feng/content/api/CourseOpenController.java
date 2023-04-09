package com.feng.content.api;

import com.feng.content.model.dto.CoursePreviewDto;
import com.feng.content.service.CourseBaseInfoService;
import com.feng.content.service.CoursePublishService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: SorryMaker
 * @create: 2023-04-07 15:15
 **/
@RestController
@RequestMapping(value = "/open")
@Slf4j
@Api(value = "课程公开",tags = "课程公开查询接口")
public class CourseOpenController {
    @Autowired
    private CourseBaseInfoService courseBaseInfoService;

    @Autowired
    private CoursePublishService coursePublishService;

    @GetMapping(value = "/course/whole/{courseId}")
    public CoursePreviewDto getPreviewInfo(@PathVariable("courseId")Long courseId){
        CoursePreviewDto coursePreviewDto = coursePublishService.getCoursePreviewInfo(courseId);
        return coursePreviewDto;
    }

}