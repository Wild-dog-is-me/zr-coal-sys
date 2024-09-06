package com.zr.common.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.zr.common.utils.SecurityUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.joda.time.LocalDateTime;
import org.springframework.stereotype.Component;


/**
 * @Author: Odin
 * @Date: 2024/9/7 00:01
 * @Description:
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createTime", new LocalDateTime(), metaObject);
        this.setFieldValByName("updateTime", new LocalDateTime(), metaObject);
        this.setFieldValByName("createBy", SecurityUtils.getUsername(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", new LocalDateTime(), metaObject);
        this.setFieldValByName("updateBy", SecurityUtils.getUsername(), metaObject);
    }
}
