package com.zr.manage.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zr.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zr.manage.mapper.FileInfoMapper;
import com.zr.manage.domain.FileInfo;
import com.zr.manage.service.IFileInfoService;

/**
 * 文件Service业务层处理
 *
 * @author zr
 * @date 2024-09-11
 */
@Service
public class FileInfoServiceImpl extends ServiceImpl<FileInfoMapper,FileInfo> implements IFileInfoService
{
    @Autowired
    private FileInfoMapper fileInfoMapper;

    /**
     * 查询文件
     *
     * @param id 文件主键
     * @return 文件
     */
    @Override
    public FileInfo selectFileInfoById(Long id)
    {
        return fileInfoMapper.selectFileInfoById(id);
    }

    /**
     * 查询文件列表
     *
     * @param fileInfo 文件
     * @return 文件
     */
    @Override
    public List<FileInfo> selectFileInfoList(FileInfo fileInfo)
    {
        return fileInfoMapper.selectFileInfoList(fileInfo);
    }

    /**
     * 新增文件
     *
     * @param fileInfo 文件
     * @return 结果
     */
    @Override
    public int insertFileInfo(FileInfo fileInfo)
    {
        fileInfo.setCreateTime(DateUtils.getNowDate());
        return fileInfoMapper.insertFileInfo(fileInfo);
    }

    /**
     * 修改文件
     *
     * @param fileInfo 文件
     * @return 结果
     */
    @Override
    public int updateFileInfo(FileInfo fileInfo)
    {
        fileInfo.setUpdateTime(DateUtils.getNowDate());
        return fileInfoMapper.updateFileInfo(fileInfo);
    }

    /**
     * 批量删除文件
     *
     * @param ids 需要删除的文件主键
     * @return 结果
     */
    @Override
    public int deleteFileInfoByIds(Long[] ids)
    {
        return fileInfoMapper.deleteFileInfoByIds(ids);
    }

    /**
     * 删除文件信息
     *
     * @param id 文件主键
     * @return 结果
     */
    @Override
    public int deleteFileInfoById(Long id)
    {
        return fileInfoMapper.deleteFileInfoById(id);
    }
}
