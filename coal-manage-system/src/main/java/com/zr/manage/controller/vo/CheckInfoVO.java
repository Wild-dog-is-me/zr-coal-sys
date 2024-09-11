package com.zr.manage.controller.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.zr.common.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: Odin
 * @Date: 2024/9/11 23:22
 * @Description:
 */
@Data
public class CheckInfoVO {
    private Long id;

    private Long checkOrderId;

    private BigDecimal checkRevAmt;

    private String checkPayName;

    private String checkPayPhone;

    private String checkPayRemark;

    private Long checkHolderUserId;

    private String coalKind;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
