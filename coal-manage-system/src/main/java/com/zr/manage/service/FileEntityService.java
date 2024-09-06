package com.zr.manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zr.manage.domain.FileEntity;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: Odin
 * @Date: 2024/9/7 00:19
 * @Description:
 */
public interface FileEntityService extends IService<FileEntity> {

    // 上传文件
    void upload(MultipartFile file);
}
