package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.Notice;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 操作notice相关数据接口
 *
 * @author wangsen
 * @date 2024/01/14
 */
@Mapper
public interface NoticeMapper extends BaseMapper<Notice> {

    /**
     * 查询符合条件的所有
     */
    List<Notice> selectAll(Notice notice);

}