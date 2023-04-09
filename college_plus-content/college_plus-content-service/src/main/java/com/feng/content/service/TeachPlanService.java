package com.feng.content.service;

import com.feng.content.model.dto.BindTeachPlanMediaDto;
import com.feng.content.model.dto.SaveTeachplanDto;
import com.feng.content.model.dto.TeachPlanDto;
import com.feng.content.model.po.TeachplanMedia;

import java.util.List;

/**
 * @description:
 * @author: SorryMaker
 * @create: 2023-02-24 18:29
 **/
public interface TeachPlanService {
    /**
     *
     * @param courseId
     * @return
     */
    public List<TeachPlanDto> findTeachPlayTree(Long courseId);

    /**
     * @description 保存课程计划(新增/修改)
     * @param dto
     * @return void
     * @author sorrymaker
     * @date 2022/10/10 15:07
     */
    public void saveTeachplan(SaveTeachplanDto dto);

    public TeachplanMedia associationMedia(BindTeachPlanMediaDto bindTeachPlanMediaDto);
}