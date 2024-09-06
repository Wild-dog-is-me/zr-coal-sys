package com.zr.manage.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zr.common.exception.base.BaseException;
import com.zr.common.utils.cos.CosUtil;
import com.zr.manage.domain.FileEntity;
import com.zr.manage.mapper.FileEntityMapper;
import com.zr.manage.service.FileEntityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Author: Odin
 * @Date: 2024/9/7 00:19
 * @Description:
 */

@Slf4j
@Service
public class FileEntityServiceImpl extends ServiceImpl<FileEntityMapper, FileEntity> implements FileEntityService {

    @Resource
    private CosUtil cosUtil;

    @Override
    public void upload(MultipartFile file) {
        log.info("上传文件:{}", file.getOriginalFilename());
        Map<String, String> resMap = cosUtil.uploadImage(file);
        try {
            this.save(new FileEntity().setFileName(resMap.get("fileName")).setFileUrl(resMap.get("fileUrl")));
        } catch (Exception e) {
            log.info("上传文件失败:{}", e.getMessage());
            throw new BaseException("上传文件失败");
        }
    }
}
