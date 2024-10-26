package com.uhaiin.service.impl;

import com.uhaiin.enums.ApiResponseEnum;
import com.uhaiin.pojo.MinioPojo;
import com.uhaiin.service.MinioService;
import com.uhaiin.util.MinioUtil;
import com.uhaiin.web.ApiResponse;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

/**
 * minio 存储对象实现类
 *
 * @author Jiancai.zhong
 * @date 2024-10-25 09:46:05
 */
@Service
public class MinioServiceImpl implements MinioService {

    @Resource
    private MinioUtil minioUtil;

    /**
     * 单一文件上传
     *
     * @param file 需要上传的对象
     * @return ApiResponse<MinioPojo> 上传结果，包含上传文件名和上传后的文件URL
     * @author Jiancai.zhong
     * @date 2024-10-25 09:47:30
     */
    @Override
    public ApiResponse<MinioPojo> uploadFile(MultipartFile file) {
        ApiResponse<MinioPojo> apiResponse;
        MinioPojo minioPojo = new MinioPojo();

        String fileName = file.getOriginalFilename();
        apiResponse = minioUtil.upload(file, fileName);

        if (Objects.equals(apiResponse.getCode(), ApiResponseEnum.FAILURE.code)) {
            return apiResponse;
        }

        String fileUrl = minioUtil.getFileUrl(fileName);

        minioPojo.setFileName(fileName);
        minioPojo.setFileUrl(fileUrl);

        apiResponse.setData(minioPojo);
        apiResponse.setMessage("success upload");

        return apiResponse;
    }

}
