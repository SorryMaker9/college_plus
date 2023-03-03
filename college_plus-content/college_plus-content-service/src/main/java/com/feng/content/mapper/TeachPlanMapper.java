package com.feng.content.mapper;

import com.feng.content.model.dto.TeachPlanDto;
import com.feng.content.model.po.TeachPlan;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 课程计划 Mapper 接口
 * </p>
 *
 * @author itcast
 */
@Repository
public interface TeachPlanMapper extends BaseMapper<TeachPlan> {
    public List<TeachPlanDto> selectTreeNodes(Long courseId);

}
