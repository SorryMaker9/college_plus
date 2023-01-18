package com.feng.content.mapper;

import com.feng.content.model.po.CourseBase;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 课程基本信息 Mapper 接口
 * </p>
 *
 * @author feng
 */
@Repository
public interface CourseBaseMapper extends BaseMapper<CourseBase> {

}
