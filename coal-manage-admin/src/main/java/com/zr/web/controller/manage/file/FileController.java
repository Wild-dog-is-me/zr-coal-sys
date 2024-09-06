package com.zr.web.controller.manage.file;

import com.zr.common.core.domain.AjaxResult;
import com.zr.common.utils.cos.CosUtil;
import com.zr.manage.service.FileEntityService;
import com.zr.web.controller.common.BaseCtl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @Author: Odin
 * @Date: 2024/9/7 00:21
 * @Description:
 */

@Slf4j
@RestController
@RequestMapping("/api/file")
public class FileController extends BaseCtl {

    /**
     * 上传文件
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public AjaxResult uploadFile(@RequestParam("file") MultipartFile file) {
        fileEntityService.upload(file);
        return AjaxResult.success();
    }

    /**
     * 删除文件
     * @param id
     * @return
     */
    @GetMapping("/del/{id}")
    public AjaxResult delFile(@PathVariable("id") String id) {
        fileEntityService.removeById(id);
        return AjaxResult.success();
    }
}


