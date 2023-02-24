package com.feng.content;

import com.feng.base.model.PageParams;
import com.feng.content.mapper.CourseBaseMapper;
import com.feng.content.mapper.CourseCategoryMapper;
import com.feng.content.model.dto.CourseCategoryTreeDto;
import com.feng.content.model.dto.QueryCourseParamsDto;
import com.feng.content.model.po.CourseBase;
import com.feng.content.service.CourseBaseInfoService;
import com.feng.content.service.CourseCategoryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @description:
 * @author: SorryMaker
 * @create: 2023-01-16 15:18
 **/
@SpringBootTest
public class ContentApiApplicationTest {
    @Autowired
    private CourseBaseMapper courseBaseMapper;
    @Autowired
    private CourseBaseInfoService courseBaseInfoService;

    @Autowired
    private CourseCategoryService courseCategoryService;
    @Test
    public void testCourseBaseMapper(){
        CourseBase courseBase = courseBaseMapper.selectById(22);
        Assertions.assertNotNull(courseBase);
    }
    @Test
    public void testCourseBaseInfoService(){
        PageParams pageParams = new PageParams();
        System.out.println(courseBaseInfoService.queryCourseBaseList(pageParams, new QueryCourseParamsDto()));
    }
    @Test
    public void testCourseCategoryMapper(){
        List<CourseCategoryTreeDto> courseCategoryTreeDtos = courseCategoryService.queryTreeNodes("1");
        System.out.println(courseCategoryTreeDtos);
    }
}
