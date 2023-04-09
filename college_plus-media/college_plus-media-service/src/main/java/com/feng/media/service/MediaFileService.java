package com.feng.media.service;

import com.feng.base.model.PageParams;
import com.feng.base.model.PageResult;
import com.feng.base.model.RestResponse;
import com.feng.media.model.dto.QueryMediaParamsDto;
import com.feng.media.model.dto.UploadFileParamsDto;
import com.feng.media.model.dto.UploadFileResultDto;
import com.feng.media.model.po.MediaFiles;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.File;
import java.util.List;

/**
 * @author Mr.M
 * @version 1.0
 * @description 媒资文件管理业务类
 * @date 2022/9/10 8:55
 */
public interface MediaFileService {

    /**
     * @param pageParams          分页参数
     * @param queryMediaParamsDto 查询条件
     * @return com.feng.base.model.PageResult<com.feng.media.model.po.MediaFiles>
     * @description 媒资文件查询方法
     * @author Mr.M
     * @date 2022/9/10 8:57
     */
    public PageResult<MediaFiles> queryMediaFiles(Long companyId, PageParams pageParams, QueryMediaParamsDto queryMediaParamsDto);


    /**
     * @param companyId           机构id
     * @param uploadFileParamsDto 文件信息
     * @param bytes               文件字节数组
     * @param folder              桶下边的子目录
     * @param objectName          对象名称
     * @return com.feng.media.model.dto.UploadFileResultDto
     * @description 上传文件的通用接口
     * @author Mr.M
     * @date 2022/10/13 15:51
     */
    public UploadFileResultDto uploadFile(Long companyId, UploadFileParamsDto uploadFileParamsDto, byte[] bytes, String folder, String objectName);

    /**
     * @param companyId
     * @param fileId
     * @param uploadFileParamsDto
     * @param bucket
     * @param objectName
     * @return
     */
    public MediaFiles addMediaFilesToDb(Long companyId, String fileId, UploadFileParamsDto uploadFileParamsDto, String bucket, String objectName);

    /**检查文件是否存在
     * @param fileMd5
     * @return
     */
    public RestResponse<Boolean> checkFile(String fileMd5);

    /**
     * 检查分块是否存在
     *
     * @param fileMd5
     * @param chunkIndex
     * @return
     */
    public RestResponse<Boolean> checkChunk(String fileMd5, int chunkIndex);

    /**
     * 上传分块
     * @param fileMd5
     * @param chunk
     * @param bytes
     * @return
     */
    public RestResponse uploadChunk(String fileMd5,int chunk,byte[] bytes);

    /**
     * 合并分块
     * @param companyId
     * @param fileMd5
     * @param chunkTotal
     * @param uploadFileParamsDto
     * @return
     */
    public RestResponse mergeChunks(Long companyId,String fileMd5, int chunkTotal,UploadFileParamsDto uploadFileParamsDto);


    //根据桶和文件路径从minio下载文件
    public File downloadFileFromMinIO(String bucket, String objectName);

    /**
     * 上传文件
     * @param filePath
     * @param bucket
     * @param objectName
     */
    public void addMediaFilesToMinio(String filePath, String bucket, String objectName);

    /**
     * 根据媒资Id查询文件信息
     * @param mediaId
     * @return
     */
    public MediaFiles getFileById(String mediaId);

}
