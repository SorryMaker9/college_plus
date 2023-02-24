package com.feng.content.model.dto;

import com.feng.content.model.po.CourseCategory;
import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author: SorryMaker
 * @create: 2023-02-23 15:37
 **/
@Data
public class CourseCategoryTreeDto extends CourseCategory {
    List childrenTreeNodes;
}