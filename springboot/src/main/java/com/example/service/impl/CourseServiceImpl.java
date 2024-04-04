package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.enums.ResultCodeEnum;
import com.example.entity.Course;
import com.example.exception.CustomException;
import com.example.mapper.CourseMapper;
import com.example.service.CourseService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
    @Override
    public void add(Course course) {
        if (courseMapper.insert(course) == 0) {
            throw new CustomException("500", "新增失败");
        }
    }

    /**
     * 删除
     */
    @Override
    public void deleteById(Integer id) {
        if (courseMapper.deleteById(id) == 0) {
            throw new CustomException("500", "删除失败");
        }
    }

    /**
     * 批量删除
     */
    @Override
    public void deleteBatch(List<Integer> ids) {
        if (courseMapper.deleteBatchIds(ids) == 0) {
            throw new CustomException("500", "批量删除失败");
        }
    }

    /**
     * 根据ID查询
     */
    @Override
    public Course selectById(Integer id) {
        // 如果查询不到结果，则返回一个空course对象
        return Optional.ofNullable(courseMapper.selectById(id)).orElse(new Course());
    }

    /**
     * 查询所有
     */
    @Override
    public List<Course> selectAll(Course course) {
        // 判空
        Optional.ofNullable(course).orElseThrow(() -> new CustomException(ResultCodeEnum.PARAM_ERROR));
        LambdaQueryWrapper<Course> queryWrapper = new LambdaQueryWrapper<>();
        Optional.ofNullable(course.getId()).ifPresent(id -> queryWrapper.eq(Course::getId, id));
        Optional.ofNullable(course.getName()).ifPresent(name -> queryWrapper.like(Course::getName, name));
        queryWrapper.orderByDesc(Course::getId);
        // 查询结果
        List<Course> courses = courseMapper.selectList(queryWrapper);
        return courses.isEmpty() ? Collections.emptyList() : courses;
    }

    /**
     * 分页查询
     */
    @Override
    public PageInfo<Course> selectPage(Course course, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Course> courses = this.selectAll(course);
        // 查询不到则返回空list
        return courses.isEmpty() ? PageInfo.of(Collections.emptyList()) : PageInfo.of(courses);
    }
}
