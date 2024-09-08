package com.zr.manage.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zr.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zr.manage.mapper.NoticeInfoMapper;
import com.zr.manage.domain.NoticeInfo;
import com.zr.manage.service.INoticeInfoService;

/**
 * 公告信息Service业务层处理
 *
 * @author zr
 * @date 2024-09-08
 */
@Service
public class NoticeInfoServiceImpl extends ServiceImpl<NoticeInfoMapper,NoticeInfo> implements INoticeInfoService
{
    @Autowired
    private NoticeInfoMapper noticeInfoMapper;

    /**
     * 查询公告信息
     *
     * @param id 公告信息主键
     * @return 公告信息
     */
    @Override
    public NoticeInfo selectNoticeInfoById(Long id)
    {
        return noticeInfoMapper.selectNoticeInfoById(id);
    }

    /**
     * 查询公告信息列表
     *
     * @param noticeInfo 公告信息
     * @return 公告信息
     */
    @Override
    public List<NoticeInfo> selectNoticeInfoList(NoticeInfo noticeInfo)
    {
        return noticeInfoMapper.selectNoticeInfoList(noticeInfo);
    }

    /**
     * 新增公告信息
     *
     * @param noticeInfo 公告信息
     * @return 结果
     */
    @Override
    public int insertNoticeInfo(NoticeInfo noticeInfo)
    {
        noticeInfo.setCreateTime(DateUtils.getNowDate());
        return noticeInfoMapper.insertNoticeInfo(noticeInfo);
    }

    /**
     * 修改公告信息
     *
     * @param noticeInfo 公告信息
     * @return 结果
     */
    @Override
    public int updateNoticeInfo(NoticeInfo noticeInfo)
    {
        noticeInfo.setUpdateTime(DateUtils.getNowDate());
        return noticeInfoMapper.updateNoticeInfo(noticeInfo);
    }

    /**
     * 批量删除公告信息
     *
     * @param ids 需要删除的公告信息主键
     * @return 结果
     */
    @Override
    public int deleteNoticeInfoByIds(Long[] ids)
    {
        return noticeInfoMapper.deleteNoticeInfoByIds(ids);
    }

    /**
     * 删除公告信息信息
     *
     * @param id 公告信息主键
     * @return 结果
     */
    @Override
    public int deleteNoticeInfoById(Long id)
    {
        return noticeInfoMapper.deleteNoticeInfoById(id);
    }
}
