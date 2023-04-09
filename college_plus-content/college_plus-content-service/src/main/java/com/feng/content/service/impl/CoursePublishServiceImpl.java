package com.feng.content.service.impl;

import com.alibaba.fastjson.JSON;
import com.feng.base.exception.CollegePlusException;
import com.feng.base.utils.StringUtil;
import com.feng.content.mapper.CourseBaseMapper;
import com.feng.content.mapper.CourseMarketMapper;
import com.feng.content.mapper.CoursePublishMapper;
import com.feng.content.mapper.CoursePublishPreMapper;
import com.feng.content.model.dto.CourseBaseInfoDto;
import com.feng.content.model.dto.CoursePreviewDto;
import com.feng.content.model.dto.TeachPlanDto;
import com.feng.content.model.po.CourseBase;
import com.feng.content.model.po.CourseMarket;
import com.feng.content.model.po.CoursePublish;
import com.feng.content.model.po.CoursePublishPre;
import com.feng.content.service.CourseBaseInfoService;
import com.feng.content.service.CoursePublishService;
import com.feng.content.service.TeachPlanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @description:
 * @author: SorryMaker
 * @create: 2023-04-05 17:10
 **/
@Service
@Slf4j
public class CoursePublishServiceImpl implements CoursePublishService {
    @Autowired
    private CourseBaseInfoService courseBaseInfoService;

    @Autowired
    private TeachPlanService teachPlanService;

    @Autowired
    private CourseBaseMapper courseBaseMapper;

    @Autowired
    private CourseMarketMapper courseMarketMapper;

    @Autowired
    private CoursePublishPreMapper coursePublishPreMapper;

    @Autowired
    private CoursePublishMapper coursePublishMapper;

    @Override
    public CoursePreviewDto getCoursePreviewInfo(Long courseId) {
        CoursePreviewDto coursePreviewDto = new CoursePreviewDto();
        //课程基本信息,营销信息
        CourseBaseInfoDto courseBaseInfo = courseBaseInfoService.getCourseBaseInfo(courseId);
        //课程计划信息
        List<TeachPlanDto> teachPlayTree = teachPlanService.findTeachPlayTree(courseId);
        coursePreviewDto.setCourseBase(courseBaseInfo);
        coursePreviewDto.setTeachplans(teachPlayTree);
        return coursePreviewDto;
    }

    @Override
    @Transactional
    public void commitAudit(Long companyId, Long courseId) {
        CourseBaseInfoDto courseBaseInfo = courseBaseInfoService.getCourseBaseInfo(courseId);
        if (courseBaseInfo == null) {
            CollegePlusException.cast("课程找不到");
        }
        String auditStatus = courseBaseInfo.getAuditStatus();

        if ("202003".equals(auditStatus)) {
            CollegePlusException.cast("课程已提交,请等待审核");
        }
        String pic = courseBaseInfo.getPic();
        if (StringUtil.isEmpty(pic)) {
            CollegePlusException.cast("请求上传课程图片");
        }
        List<TeachPlanDto> teachPlayTree = teachPlanService.findTeachPlayTree(courseId);
        if(teachPlayTree == null || teachPlayTree.size() == 0){
            CollegePlusException.cast("请编写课程计划");
        }
        CoursePublishPre coursePublishPre = new CoursePublishPre();
        BeanUtils.copyProperties(courseBaseInfo,coursePublishPre);
        coursePublishPre.setCompanyId(companyId);

        CourseMarket courseMarket = courseMarketMapper.selectById(courseId);
        String courseMarketJson = JSON.toJSONString(courseMarket);
        coursePublishPre.setMarket(courseMarketJson);

        String teachPlanTreeJson = JSON.toJSONString(teachPlayTree);
        coursePublishPre.setTeachplan(teachPlanTreeJson);

        coursePublishPre.setStatus("202003");
        coursePublishPre.setCreateDate(LocalDateTime.now());

        CoursePublishPre coursePublishPreObj = coursePublishPreMapper.selectById(courseId);
        if(coursePublishPreObj == null){
            coursePublishPreMapper.insert(coursePublishPre);
        } else {
            coursePublishPreMapper.updateById(coursePublishPre);
        }
        CourseBase courseBase = courseBaseMapper.selectById(courseId);
        courseBase.setAuditStatus("202003");

        courseBaseMapper.updateById(courseBase);
    }

    @Transactional
    @Override
    public void publish(Long companyId, Long courseId) {
        CoursePublishPre coursePublishPre = coursePublishPreMapper.selectById(courseId);
        if(coursePublishPre == null){
            CollegePlusException.cast("课程没有审核记录,无法发布");
        }
        String status = coursePublishPre.getStatus();
        if(!"202004".equals(status)){
            CollegePlusException.cast("课程没有审核通过不允许发布");
        }

        CoursePublish coursePublish = new CoursePublish();
        BeanUtils.copyProperties(coursePublishPre,coursePublish);

        CoursePublish coursePublishObj = coursePublishMapper.selectById(courseId);
        if(coursePublishObj == null){
            coursePublishMapper.insert(coursePublish);
        } else {
            coursePublishMapper.updateById(coursePublish);
        }


        coursePublishPreMapper.deleteById(courseId);
    }
}