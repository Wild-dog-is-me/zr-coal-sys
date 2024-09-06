package com.zr.web.controller.common;

import com.zr.manage.service.FileEntityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.annotation.Resource;

/**
 * @Author: Odin
 * @Date: 2024/9/7 00:41
 * @Description:
 */

@RestControllerAdvice
public class BaseCtl {

    @Resource
    public FileEntityService fileEntityService;

}
