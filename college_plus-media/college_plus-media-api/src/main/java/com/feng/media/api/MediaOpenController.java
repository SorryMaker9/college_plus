package com.feng.media.api;


import com.feng.base.model.RestResponse;
import com.feng.base.utils.StringUtil;
import com.feng.media.model.po.MediaFiles;
import com.feng.media.service.MediaFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: SorryMaker
 * @create: 2023-04-07 15:23
 **/
@RestController
@Slf4j
@Api(value = "媒资文件管理接口",tags = "媒资文件管理接口")
public class MediaOpenController {
    @Autowired
    private MediaFileService mediaFileService;

    @ApiOperation("预览文件")
    @GetMapping("/preview/{mediaId}")
    public RestResponse<String> getPlayUrlByMediaId(@PathVariable("mediaId")String mediaId){
        MediaFiles mediaFiles = mediaFileService.getFileById(mediaId);
        if (mediaFiles == null) {
           return RestResponse.validFail("找不到视频");
        }
        if(StringUtil.isEmpty(mediaFiles.getUrl())){
            return RestResponse.validFail("视频还没有转码处理");
        }
        return RestResponse.success(mediaFiles.getUrl());
    }
}