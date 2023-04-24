package com.feng.content;


import com.feng.content.model.dto.CoursePreviewDto;
import com.feng.content.service.CoursePublishService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Objects;

/**
 * @description:
 * @author: SorryMaker
 * @create: 2023-04-17 16:28
 **/
@SpringBootTest
public class FreeMarkerTest {
//    @Autowired
//    private CoursePublishService coursePublishService;
//
//    @Test
//    public void testGenerateHtmlByTemplate() throws IOException, TemplateException {
//
//        Configuration configuration = new Configuration(Configuration.getVersion());
//
//        //拿到classpath的路径
//        String classpath = this.getClass().getResource("/").getPath();
//        //指定模板目录
//        configuration.setDirectoryForTemplateLoading(new File(classpath+"/templates/"));
//        configuration.setDefaultEncoding("utf-8");
//        //得到模板
//        Template template = configuration.getTemplate("course_template.ftl");
//
//        //准备数据
//        CoursePreviewDto coursePreviewInfo = coursePublishService.getCoursePreviewInfo(121L);
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("model",coursePreviewInfo);
//        //Template template 模板
//        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
//
//        //使用流
//        InputStream inputStream = IOUtils.toInputStream(html, "utf-8");
//        //输出文件
//        FileOutputStream outputStream = new FileOutputStream(new File("F://aaa//120.html"));
//
//        IOUtils.copy(inputStream,outputStream);
//    }

}