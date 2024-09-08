package com.zr.manage.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zr.manage.domain.NoticeInfo;

/**
 * 公告信息Mapper接口
 *
 * @author zr
 * @date 2024-09-08
 */
public interface NoticeInfoMapper extends BaseMapper<NoticeInfo>
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
     * 删除公告信息
     *
     * @param id 公告信息主键
     * @return 结果
     */
    public int deleteNoticeInfoById(Long id);

    /**
     * 批量删除公告信息
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteNoticeInfoByIds(Long[] ids);
}
