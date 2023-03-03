package com.feng.media.service;

import com.feng.base.model.PageParams;
import com.feng.base.model.PageResult;
import com.feng.media.model.dto.QueryMediaParamsDto;
import com.feng.media.model.dto.UploadFileParamsDto;
import com.feng.media.model.dto.UploadFileResultDto;
import com.feng.media.model.po.MediaFiles;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @description 媒资文件管理业务类
 * @author Mr.M
 * @date 2022/9/10 8:55
 * @version 1.0
 */
public interface MediaFileService {

 /**
  * @description 媒资文件查询方法
  * @param pageParams 分页参数
  * @param queryMediaParamsDto 查询条件
  * @return com.feng.base.model.PageResult<com.feng.media.model.po.MediaFiles>
  * @author Mr.M
  * @date 2022/9/10 8:57
 */
 public PageResult<MediaFiles> queryMediaFiles(Long companyId,PageParams pageParams, QueryMediaParamsDto queryMediaParamsDto);


 /**
  * @description 上传文件的通用接口
  * @param companyId  机构id
  * @param uploadFileParamsDto  文件信息
  * @param bytes  文件字节数组
  * @param folder 桶下边的子目录
  * @param objectName 对象名称
  * @return com.feng.media.model.dto.UploadFileResultDto
  * @author Mr.M
  * @date 2022/10/13 15:51
 */
 public UploadFileResultDto uploadFile(Long companyId, UploadFileParamsDto uploadFileParamsDto, byte[] bytes,String folder,String objectName);

 /**
  *
  * @param companyId
  * @param fileId
  * @param uploadFileParamsDto
  * @param bucket
  * @param objectName
  * @return
  */
 public MediaFiles addMediaFilesToDb(Long companyId, String fileId, UploadFileParamsDto uploadFileParamsDto, String bucket, String objectName);

}
