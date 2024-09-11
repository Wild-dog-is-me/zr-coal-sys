package com.zr.manage.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zr.manage.domain.FileInfo;

/**
 * 文件Service接口
 *
 * @author zr
 * @date 2024-09-11
 */
public interface IFileInfoService extends IService<FileInfo>
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
     * 批量删除文件
     *
     * @param ids 需要删除的文件主键集合
     * @return 结果
     */
    public int deleteFileInfoByIds(Long[] ids);

    /**
     * 删除文件信息
     *
     * @param id 文件主键
     * @return 结果
     */
    public int deleteFileInfoById(Long id);
}
