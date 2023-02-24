package com.feng.content.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.feng.content.model.dto.CourseCategoryTreeDto;
import com.feng.content.model.po.CourseCategory;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 课程分类 Mapper 接口
 * </p>
 *
 * @author
 */
@Repository
public interface CourseCategoryMapper extends BaseMapper<CourseCategory> {

    List<CourseCategoryTreeDto> selectTreeNodes(String id);
}
