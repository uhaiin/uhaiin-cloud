package com.uhaiin.util;

import com.uhaiin.config.MinIOConfig;
import com.uhaiin.enums.ApiResponseEnum;
import com.uhaiin.pojo.MinioPojo;
import com.uhaiin.web.ApiResponse;
import io.minio.*;
import io.minio.http.Method;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class MinioUtil {

    @Autowired
    private MinioClient minioClient;
    @Autowired
    private MinIOConfig configuration;

    /**
     * 判断bucket是否存在，不存在则创建
     */
    public boolean existBucket(String bucketName) {
        boolean exists;
        try {
            exists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!exists) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
                exists = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            exists = false;
        }
        return exists;
    }

    /**
     * 删除bucket
     */
    public Boolean removeBucket(String bucketName) {
        try {
            minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 上传文件
     */
    public ApiResponse<MinioPojo> upload(MultipartFile file, String fileName) {
        InputStream inputStream;

        try {
            inputStream = file.getInputStream();
            minioClient.putObject(PutObjectArgs.builder().bucket(configuration.getBucketName()).object(fileName).stream(inputStream, file.getSize(), -1).contentType(file.getContentType()).build());
            inputStream.close();
            return ApiResponse.success(ApiResponseEnum.SUCCESS.message);
        } catch (Exception e) {
            log.error("上传文件失败：{}", e.getMessage(), e);
            ApiResponse<MinioPojo> apiResponse = new ApiResponse<>();
            apiResponse.setSuccess(false);
            apiResponse.setCode(ApiResponseEnum.FAILURE.code);
            apiResponse.setMessage(e.getMessage());
            return apiResponse;
        }
    }

    /**
     * 获取文件访问地址（有过期时间）
     */
    public String getExpireFileUrl(String fileName, int time, TimeUnit timeUnit) {
        try {
            return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().method(Method.GET).bucket(configuration.getBucketName()).object(fileName).expiry(time, timeUnit).build());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取文件访问地址
     */
    public String getFileUrl(String fileName) {
        try {
            return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().method(Method.GET).bucket(configuration.getBucketName()).object(fileName).build());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 下载文件
     */
    public void download(HttpServletResponse response, String fileName) {
        InputStream in = null;
        try {
            // 获取对象信息
            StatObjectResponse stat = minioClient.statObject(StatObjectArgs.builder().bucket(configuration.getBucketName()).object(fileName).build());
            response.setContentType(stat.contentType());
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            // 文件下载
            in = minioClient.getObject(GetObjectArgs.builder().bucket(configuration.getBucketName()).object(fileName).build());
            IOUtils.copy(in, response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 删除文件
     */
    public void delete(String fileName) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder().bucket(configuration.getBucketName()).object(fileName).build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
