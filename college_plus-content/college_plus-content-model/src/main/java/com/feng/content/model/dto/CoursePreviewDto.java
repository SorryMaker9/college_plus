package com.feng.content.model.dto;

import com.feng.content.model.po.CourseBase;
import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author: SorryMaker
 * @create: 2023-04-05 16:58
 **/
@Data
public class CoursePreviewDto {
    //课程基本信息 课程营销信息
    private CourseBaseInfoDto courseBase;

    //课程计划信息
    private List<TeachPlanDto> teachplans;
    //课程师资信息...
}