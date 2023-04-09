package com.feng.content.api;

import com.feng.content.model.dto.BindTeachPlanMediaDto;
import com.feng.content.model.dto.SaveTeachplanDto;
import com.feng.content.model.dto.TeachPlanDto;
import com.feng.content.service.TeachPlanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description:
 * @author: SorryMaker
 * @create: 2023-02-24 17:39
 **/
@RestController
@Api(value = "课程计划管理相关的接口", tags = "课程计划管理相关的接口")
@Slf4j
public class TeachPlanController {

    @Autowired
    private TeachPlanService teachPlanService;

    @GetMapping("/teachplan/{courseId}/tree-nodes")
    public List<TeachPlanDto> getTreeNodes(@PathVariable Long courseId) {
        return teachPlanService.findTeachPlayTree(courseId);
    }
    @PostMapping("/teachplan")
    public void saveTeachPlan(@RequestBody SaveTeachplanDto dto){
        teachPlanService.saveTeachplan(dto);
    }
    @ApiOperation("课程计划和媒资信息绑定")
    @PostMapping(value = "teachplan/association/media")
    public void associationMedia(@RequestBody BindTeachPlanMediaDto bindTeachPlanMediaDto){
        teachPlanService.associationMedia(bindTeachPlanMediaDto);

    }
}