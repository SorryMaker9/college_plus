package com.feng.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.feng.base.exception.CollegePlusException;
import com.feng.content.mapper.CourseTeacherMapper;
import com.feng.content.mapper.TeachPlanMapper;
import com.feng.content.mapper.TeachplanMediaMapper;
import com.feng.content.model.dto.BindTeachPlanMediaDto;
import com.feng.content.model.dto.SaveTeachplanDto;
import com.feng.content.model.dto.TeachPlanDto;
import com.feng.content.model.po.TeachPlan;
import com.feng.content.model.po.TeachplanMedia;
import com.feng.content.service.TeachPlanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @description:
 * @author: SorryMaker
 * @create: 2023-02-24 18:30
 **/
@Slf4j
@Service
public class TeachPlanServiceImpl implements TeachPlanService {
    @Autowired
    private TeachPlanMapper teachPlanMapper;

    @Autowired
    private TeachplanMediaMapper teachplanMediaMapper;

    @Override
    public List<TeachPlanDto> findTeachPlayTree(Long courseId) {
        return teachPlanMapper.selectTreeNodes(courseId);
    }

    //新增、修改
    @Transactional
    @Override
    public void saveTeachplan(SaveTeachplanDto dto) {
        Long id = dto.getId();
        TeachPlan teachPlan = teachPlanMapper.selectById(id);
        if (id == null) {
            teachPlan = new TeachPlan();
            BeanUtils.copyProperties(dto, teachPlan);
            //找到同级课程计划的数量
            int count = getTeachPlanCount(dto.getCourseId(), dto.getParentid());
            //新增课程计划的值
            teachPlan.setOrderby(count + 1);
            //计算下默认 order by
            teachPlanMapper.insert(teachPlan);
        } else {
            BeanUtils.copyProperties(dto, teachPlan);
            //修改更新
            teachPlanMapper.updateById(teachPlan);
        }

        //
    }

    @Override
    @Transactional
    public TeachplanMedia associationMedia(BindTeachPlanMediaDto bindTeachPlanMediaDto) {
        Long teachPlanId = bindTeachPlanMediaDto.getTeachPlanId();
        TeachPlan teachPlan = teachPlanMapper.selectById(teachPlanId);
        if (teachPlan == null) {
            CollegePlusException.cast("教学计划不存在");
        }
        Integer grade = teachPlan.getGrade();
        if (grade != 2) {
            CollegePlusException.cast("只允许第二级教学计划绑定媒资");
        }
        Long courseId = teachPlan.getCourseId();

        //先删除原有记录
        teachplanMediaMapper.delete(new LambdaQueryWrapper<TeachplanMedia>()
                .eq(TeachplanMedia::getTeachplanId, bindTeachPlanMediaDto.getTeachPlanId()));

        TeachplanMedia teachplanMedia = new TeachplanMedia();
        teachplanMedia.setCourseId(courseId);
        teachplanMedia.setMediaFilename(bindTeachPlanMediaDto.getFileName());
        teachplanMedia.setTeachplanId(bindTeachPlanMediaDto.getTeachPlanId());
        BeanUtils.copyProperties(teachplanMedia,bindTeachPlanMediaDto);
        teachplanMediaMapper.insert(teachplanMedia);
        //再添加新纪录
        return teachplanMedia;
    }

    public int getTeachPlanCount(Long courseId, Long parentId) {
        LambdaQueryWrapper<TeachPlan> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TeachPlan::getCourseId, courseId);
        queryWrapper.eq(TeachPlan::getParentid, parentId);
        Integer count = teachPlanMapper.selectCount(queryWrapper);
        return count.intValue();
    }
}