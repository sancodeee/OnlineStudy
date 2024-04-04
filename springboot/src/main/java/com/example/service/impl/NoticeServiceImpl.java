package com.example.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.enums.ResultCodeEnum;
import com.example.entity.Account;
import com.example.entity.Notice;
import com.example.exception.CustomException;
import com.example.mapper.NoticeMapper;
import com.example.service.NoticeService;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;

/**
 * 公告信息表业务处理
 **/
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {

    private final NoticeMapper noticeMapper;

    @Autowired
    public NoticeServiceImpl(NoticeMapper noticeMapper) {
        this.noticeMapper = noticeMapper;
    }

    /**
     * 新增
     */
    @Override
    public void add(Notice notice) {
        notice.setTime(DateUtil.today());
        Account currentUser = TokenUtils.getCurrentUser();
        notice.setUser(currentUser.getUsername());
        if (noticeMapper.insert(notice) == 0) {
            throw new CustomException("500", "插入失败");
        }
    }

    /**
     * 删除
     */
    @Override
    public void deleteById(Integer id) {
        if (noticeMapper.deleteById(id) == 0) {
            throw new CustomException("500", "删除失败");
        }
    }

    /**
     * 批量删除
     */
    @Override
    public void deleteBatch(List<Integer> ids) {
        if (noticeMapper.deleteBatchIds(ids) == 0) {
            throw new CustomException("500", "批量删除失败");
        }
    }

    /**
     * 根据ID查询
     */
    @Override
    public Notice selectById(Integer id) {
        Notice notice = noticeMapper.selectById(id);
        if (ObjectUtils.isEmpty(notice)) {
            throw new CustomException(ResultCodeEnum.RESOURCE_ERROR.code, ResultCodeEnum.RESOURCE_ERROR.msg);
        }
        return notice;
    }

    /**
     * 查询符合条件的所有
     */
    @Override
    public List<Notice> selectAll(Notice notice) {
        // 判空
        Optional.ofNullable(notice).orElseThrow(() -> new CustomException(ResultCodeEnum.PARAM_ERROR));
        LambdaQueryWrapper<Notice> queryWrapper = new LambdaQueryWrapper<>();
        Optional.ofNullable(notice.getId()).ifPresent(id -> queryWrapper.eq(Notice::getId, id));
        Optional.ofNullable(notice.getTime()).ifPresent(time -> queryWrapper.eq(Notice::getTime, time));
        Optional.ofNullable(notice.getTitle()).ifPresent(title -> queryWrapper.like(Notice::getTitle, title));
        Optional.ofNullable(notice.getContent()).ifPresent(content -> queryWrapper.eq(Notice::getContent, content));
        Optional.ofNullable(notice.getUser()).ifPresent(user -> queryWrapper.eq(Notice::getUser, user));
        return getBaseMapper().selectList(queryWrapper);
    }

    /**
     * 分页查询
     */
    @Override
    public PageInfo<Notice> selectPage(Notice notice, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Notice> list = noticeMapper.selectAll(notice);
        return PageInfo.of(list);
    }

}