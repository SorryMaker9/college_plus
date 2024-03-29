package com.feng.media.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.feng.media.model.po.MediaProcess;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author itcast
 */
@Repository
public interface MediaProcessMapper extends BaseMapper<MediaProcess> {
    @Select("select * from media_process t where t.id % #{sharedTotal} = #{sharedIndex} and (t.status = 1 or t.status = 3) and t.fail_count < 3 limit #{count}")
    List<MediaProcess> selectListBySharedIndex(@Param("sharedTotal") int sharedTotal, @Param("sharedIndex") int sharedIndex, @Param("count") int count);

    /**
     * 开启任务
     * @param id
     * @return
     */
    @Update("update media_process m set m.status = '4' where (m.status = '1' or m.status = '3') and m.fail_count < 3 and m.id = #{id}")
    int startTask(@Param("id")Long id);
}
