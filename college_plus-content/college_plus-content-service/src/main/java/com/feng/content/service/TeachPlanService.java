package com.feng.content.service;

import com.feng.content.model.dto.SaveTeachplanDto;
import com.feng.content.model.dto.TeachPlanDto;

import java.util.List;

/**
 * @description:
 * @author: SorryMaker
 * @create: 2023-02-24 18:29
 **/
public interface TeachPlanService {
    public List<TeachPlanDto> findTeachPlayTree(Long courseId);

    /**
     * @description 保存课程计划(新增/修改)
     * @param dto
     * @return void
     * @author Mr.M
     * @date 2022/10/10 15:07
     */
    public void saveTeachplan(SaveTeachplanDto dto);
}