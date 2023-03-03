package com.feng.content.model.dto;

import com.feng.content.model.po.TeachPlan;
import com.feng.content.model.po.TeachplanMedia;
import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author: SorryMaker
 * @create: 2023-02-24 17:37
 **/
@Data
public class TeachPlanDto extends TeachPlan {
    //关联的媒资信息
    TeachplanMedia teachplanMedia;

    //子目录
    List<TeachPlanDto> teachPlanTreeNodes;

}