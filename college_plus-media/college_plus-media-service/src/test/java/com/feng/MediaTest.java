package com.feng;

import com.feng.media.mapper.MediaFilesMapper;
import io.minio.MinioClient;
import io.minio.UploadObjectArgs;
import org.junit.jupiter.api.Test;

/**
 * @description:
 * @author: SorryMaker
 * @create: 2023-03-02 14:12
 **/
public class MediaTest {
    static MinioClient minioClient =
            MinioClient.builder()
                    .endpoint("http://192.168.10.100:9000")
                    .credentials("minioadmin","minioadmin")
                    .build();
    private MediaFilesMapper mediaFilesMapper;
    @Test
    public void upload(){
        try {
            UploadObjectArgs uploadObjectArgs = UploadObjectArgs.builder()
                    .bucket("testbucket")
                    .object("jdk-8u351-docs-all.zip")
                    .filename("E:\\jdk-8u351-docs-all.zip")
                    .build();
            minioClient.uploadObject(uploadObjectArgs);
            System.out.println("上传成功了");
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("上传失败了");
        }
    }

}