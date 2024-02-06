package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 课程实体
 *
 * @author wangsen
 * @date 2024/02/02
 */
@Data
@TableName("course")
public class Course implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * img图片链接
     */
    private String img;
    /**
     * 课程名称
     */
    private String name;
    /**
     * 内容
     */
    private String content;
    /**
     * 类型
     */
    private String type;
    /**
     * 价格
     */
    private Double price;
    /**
     * 视频
     */
    private String video;
    /**
     * 文件
     */
    private String file;
    /**
     * 折扣
     */
    private Double discount;
    /**
     * 推荐
     */
    private String recommend;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    private String creator = "default";

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    /**
     * 更新人
     */
    private String updater = "default";
}
