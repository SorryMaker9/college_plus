package com.feng.content;

import com.feng.base.model.PageParams;
import com.feng.content.mapper.CourseBaseMapper;
import com.feng.content.model.dto.QueryCourseParamsDto;
import com.feng.content.model.po.CourseBase;
import com.feng.content.service.CourseBaseInfoService;
import org.apache.commons.lang.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
}
