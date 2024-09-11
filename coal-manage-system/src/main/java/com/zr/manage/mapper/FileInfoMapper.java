package com.zr.manage.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zr.manage.domain.FileInfo;

/**
 * 文件Mapper接口
 *
 * @author zr
 * @date 2024-09-11
 */
public interface FileInfoMapper extends BaseMapper<FileInfo>
{
    /**
     * 查询文件
     *
     * @param id 文件主键
     * @return 文件
     */
    public FileInfo selectFileInfoById(Long id);

    /**
     * 查询文件列表
     *
     * @param fileInfo 文件
     * @return 文件集合
     */
    public List<FileInfo> selectFileInfoList(FileInfo fileInfo);

    /**
     * 新增文件
     *
     * @param fileInfo 文件
     * @return 结果
     */
    public int insertFileInfo(FileInfo fileInfo);

    /**
     * 修改文件
     *
     * @param fileInfo 文件
     * @return 结果
     */
    public int updateFileInfo(FileInfo fileInfo);

    /**
     * 删除文件
     *
     * @param id 文件主键
     * @return 结果
     */
    public int deleteFileInfoById(Long id);

    /**
     * 批量删除文件
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFileInfoByIds(Long[] ids);
}
