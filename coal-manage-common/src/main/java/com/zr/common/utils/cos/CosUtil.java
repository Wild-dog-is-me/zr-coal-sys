package com.zr.common.utils.cos;

import cn.hutool.core.lang.UUID;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.region.Region;
import com.zr.common.cos.CosConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Odin
 * @Date: 2024/9/6 23:44
 * @Description:
 */

@Slf4j
@Service
public class CosUtil {

    @Resource
    private CosConfig cosConfig;

    public Map<String, String> uploadImage(MultipartFile file) {
        String secretId = cosConfig.getAccessKeyId();
        String secretKey = cosConfig.getAccessKeySecret();
        String bucket = cosConfig.getBucketName();

        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        ClientConfig clientConfig = new ClientConfig(new Region("ap-nanjing"));

        // 创建客户端
        COSClient cosClient = new COSClient(cred, clientConfig);

        // 构建存储路径
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String time = now.format(dateTimeFormatter);

        // 加上uuid防止文件名重复
        String fileName = UUID.fastUUID() + file.getOriginalFilename();
        String path = time + "/" + fileName;

        HashMap<String,String> resMap = new HashMap();
        try {
            // 上传文件
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, path, file.getInputStream(), new ObjectMetadata());
            cosClient.putObject(putObjectRequest);
            resMap.put("fileUrl", cosConfig.getAssetsPath()+fileName);
            resMap.put("fileName", fileName);
        } catch (IOException e) {
            log.info("文件{}上传失败{}", file.getOriginalFilename(), e.getMessage());
            throw new RuntimeException(e);
        } finally {
            // 关闭客户端
            cosClient.shutdown();
        }
        return resMap;
    }
}
