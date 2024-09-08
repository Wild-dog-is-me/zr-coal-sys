package com.zr.manage.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zr.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Author: Odin
 * @Date: 2024/9/6 23:53
 * @Description:
 */

@TableName("zr_file_info")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class FileEntity extends BaseEntity {

    /**
     * 文件名称
     */
    @TableField("file_name")
    private String fileName;

    /**
     * 文件路径
     */
    @TableField("file_url")
    private String fileUrl;

    /**
     * 绑定的业务ID
     */
    @TableField("object_id")
    private String objectId;

}
