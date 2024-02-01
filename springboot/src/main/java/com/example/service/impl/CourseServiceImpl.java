package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.Course;
import com.example.mapper.CourseMapper;
import com.example.service.CourseService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 课程信息表(Course)表服务实现类
 *
 * @author wangsen
 * @date 2024/02/02
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    private final CourseMapper courseMapper;

    @Autowired
    public CourseServiceImpl(CourseMapper courseMapper) {
        this.courseMapper = courseMapper;
    }

    /**
     * 新增
     */
    public void add(Course course) {

    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {

    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {

    }

    /**
     * 根据ID查询
     */
    public Course selectById(Integer id) {
        return null;
    }

    /**
     * 查询所有
     */
    public List<Course> selectAll(Course course) {
        return null;
    }

    /**
     * 分页查询
     */
    public PageInfo<Course> selectPage(Course course, Integer pageNum, Integer pageSize) {
        return null;
    }
}
