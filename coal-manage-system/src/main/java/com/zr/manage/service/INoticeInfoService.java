package com.zr.manage.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zr.manage.domain.NoticeInfo;

/**
 * 公告信息Service接口
 *
 * @author zr
 * @date 2024-09-08
 */
public interface INoticeInfoService extends IService<NoticeInfo>
{
    /**
     * 查询公告信息
     *
     * @param id 公告信息主键
     * @return 公告信息
     */
    public NoticeInfo selectNoticeInfoById(Long id);

    /**
     * 查询公告信息列表
     *
     * @param noticeInfo 公告信息
     * @return 公告信息集合
     */
    public List<NoticeInfo> selectNoticeInfoList(NoticeInfo noticeInfo);

    /**
     * 新增公告信息
     *
     * @param noticeInfo 公告信息
     * @return 结果
     */
    public int insertNoticeInfo(NoticeInfo noticeInfo);

    /**
     * 修改公告信息
     *
     * @param noticeInfo 公告信息
     * @return 结果
     */
    public int updateNoticeInfo(NoticeInfo noticeInfo);

    /**
     * 批量删除公告信息
     *
     * @param ids 需要删除的公告信息主键集合
     * @return 结果
     */
    public int deleteNoticeInfoByIds(Long[] ids);

    /**
     * 删除公告信息信息
     *
     * @param id 公告信息主键
     * @return 结果
     */
    public int deleteNoticeInfoById(Long id);
}
