package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.Notice;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface NoticeService extends IService<Notice> {

    void add(Notice notice);

    void deleteById(Integer id);

    void deleteBatch(List<Integer> ids);

    Notice selectById(Integer id);

    List<Notice> selectAll(Notice notice);

    PageInfo<Notice> selectPage(Notice notice, Integer pageNum, Integer pageSize);

}
