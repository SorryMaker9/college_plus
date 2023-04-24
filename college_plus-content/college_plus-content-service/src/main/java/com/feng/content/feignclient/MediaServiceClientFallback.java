package com.feng.content.feignclient;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @description:
 * @author: SorryMaker
 * @create: 2023-04-17 18:16
 **/
public class MediaServiceClientFallback implements MediaServiceClient{

    @Override
    public String upload(MultipartFile filedata, String folder, String objectName) throws IOException {
        return null;
    }
}