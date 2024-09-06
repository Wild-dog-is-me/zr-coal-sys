package com.zr.common.cos;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Odin
 * @Date: 2024/9/6 23:38
 * @Description:
 */

@Data
@Configuration
@ConfigurationProperties("tencent.cos")
public class CosConfig {
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;
    private String assetsPath;
}
