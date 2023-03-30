package com.feng;

import io.minio.MinioClient;
import io.minio.UploadObjectArgs;
import io.minio.errors.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @description:
 * @author: SorryMaker
 * @create: 2023-03-03 14:58
 **/
public class BigFileTest {

    @Test
    public void testChunk() throws IOException {
        //原文件
        File sourcefile = new File("E:\\picture\\bigfile_test\\nacos.mp4");

        String chunkPath = "E:\\picture\\bigfile_test\\chunk\\";
        //分块文件存储路径
        File chunkFolderPath = new File(chunkPath);
        if (!chunkFolderPath.exists()) {
            chunkFolderPath.mkdirs();
        }
        int chunkSize = 1024 * 1024 * 5;
        long chunkNum = (long) Math.ceil(sourcefile.length() * 1.0 / chunkSize);

        RandomAccessFile raf_read = new RandomAccessFile(sourcefile, "r");

        byte[] b = new byte[1024];

        for (long i = 0; i < chunkNum; i++) {
            File file = new File(chunkPath + i);
            if (file.exists()) {
                file.delete();
            }
            boolean newFile = file.createNewFile();
            if (newFile) {
                RandomAccessFile raf_write = new RandomAccessFile(file, "rw");
                int len = -1;
                while ((len = raf_read.read(b)) != -1) {
                    raf_write.write(b, 0, len);
                    if (file.length() >= chunkSize) {
                        break;
                    }
                }
                raf_write.close();
            }
        }
        raf_read.close();
    }

    @Test
    public void test_MergeFile() throws IOException {
        File sourceFile = new File("E:\\picture\\bigfile_test\\nacos.mp4");
        String chunkPath = "E:\\picture\\bigfile_test\\chunk\\";
        File chunkFolderPath = new File(chunkPath);
        if (!chunkFolderPath.exists()) {
            chunkFolderPath.mkdirs();
        }
        File mergeFile = new File("E:\\picture\\bigfile_test\\nacos_01.mp4");
        boolean newFile = mergeFile.createNewFile();
        File[] chunkFiles = chunkFolderPath.listFiles();
        List<File> chunkFileList = Arrays.asList(chunkFiles);
        Collections.sort(chunkFileList, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                return Integer.parseInt(o1.getName()) - Integer.parseInt(o2.getName());
            }
        });
        byte[] b = new byte[1024];
        RandomAccessFile raf_write = new RandomAccessFile(mergeFile, "rw");

        for (File file : chunkFileList) {
            RandomAccessFile raf_read = new RandomAccessFile(file, "r");
            int len = -1;
            while ((len = raf_read.read(b)) != -1){
                raf_write.write(b,0,len);
            }
            raf_read.close();
        }
        raf_write.close();
        //校验
        try(FileInputStream sourceFileStream = new FileInputStream(sourceFile);
            FileInputStream mergeFileStream = new FileInputStream(mergeFile)){
            String sourceMd5Hex = DigestUtils.md5Hex(sourceFileStream);
            String mergeMd5Hex = DigestUtils.md5Hex(mergeFileStream);
            if(sourceMd5Hex.equals(mergeMd5Hex)){
                System.out.println("合并成功");
            }else {
                System.out.println("合并失败");
            }
        }
    }

}