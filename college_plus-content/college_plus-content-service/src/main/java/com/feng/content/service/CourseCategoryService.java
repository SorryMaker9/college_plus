package com.feng.content.service;

import com.feng.content.model.dto.CourseCategoryTreeDto;

import java.util.List;

/**
 * @description: 课程分类操作相关service
 * @author: SorryMaker
 * @create: 2023-02-23 17:01
 **/
public interface CourseCategoryService {
    /**
     * 课程分类查询
     * @param id
     * @return
     */
    List<CourseCategoryTreeDto> queryTreeNodes(String id);
}