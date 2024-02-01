package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.Course;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 课程信息表(Course)表服务接口
 *
 * @author makejava
 * @since 2024-02-02 00:37:47
 */
public interface CourseService extends IService<Course> {

    /**
     * 添加
     *
     * @param course 课程
     */
    void add(Course course);

    /**
     * 按id删除
     *
     * @param id id
     */
    void deleteById(Integer id);

    /**
     * 删除批处理
     *
     * @param ids id
     */
    void deleteBatch(List<Integer> ids);

    /**
     * 按id选择
     *
     * @param id id
     * @return {@link Course}
     */
    Course selectById(Integer id);

    /**
     * 查询所有课程
     *
     * @param course 课程
     * @return {@link List}<{@link Course}>
     */
    List<Course> selectAll(Course course);

    /**
     * 选择页面
     *
     * @param course   课程
     * @param pageNum  页面num
     * @param pageSize 页面大小
     * @return {@link PageInfo}<{@link Course}>
     */
    PageInfo<Course> selectPage(Course course, Integer pageNum, Integer pageSize);

}
