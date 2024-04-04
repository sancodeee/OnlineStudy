package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.Notice;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 通知服务
 *
 * @author wangsen
 * @date 2024/04/04
 */
public interface NoticeService extends IService<Notice> {

    /**
     * 添加
     *
     * @param notice 请注意
     */
    void add(Notice notice);

    /**
     * 根据id删除
     *
     * @param id id
     */
    void deleteById(Integer id);

    /**
     * 批量删除
     *
     * @param ids id
     */
    void deleteBatch(List<Integer> ids);

    /**
     * 根据id查询
     *
     * @param id id
     * @return {@link Notice}
     */
    Notice selectById(Integer id);

    /**
     * 查询全部
     *
     * @param notice 请注意
     * @return {@link List}<{@link Notice}>
     */
    List<Notice> selectAll(Notice notice);

    /**
     * 分页查询
     *
     * @param notice   实体类
     * @param pageNum  页码
     * @param pageSize 页面大小
     * @return {@link PageInfo}<{@link Notice}>
     */
    PageInfo<Notice> selectPage(Notice notice, Integer pageNum, Integer pageSize);

}
