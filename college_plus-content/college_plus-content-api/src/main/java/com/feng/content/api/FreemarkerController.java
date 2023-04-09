package com.feng.content.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @description:
 * @author: SorryMaker
 * @create: 2023-04-04 20:12
 **/
@Controller
public class FreemarkerController {
    @GetMapping(value = "/testfreemarker")
    public ModelAndView test(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("name","小明");
        modelAndView.setViewName("test");
        return modelAndView;
    }
}