package com.uhaiin.service;

import com.uhaiin.pojo.MinioPojo;
import com.uhaiin.web.ApiResponse;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * minio 存储对象服务
 * 
 * @author Jiancai.zhong
 * @date 2024-10-25 09:45:11
 */
public interface MinioService {

	/**
	 * 单一文件上传
	 * 
	 * @author Jiancai.zhong
	 * @param file 需要上传的对象
	 * @return ApiResponse<MinioPojo> 上传结果，包含上传文件名和上传后的文件URL
	 * @date 2024-10-25 09:47:30
	 */
	public ApiResponse<MinioPojo> uploadFile(@RequestParam MultipartFile file);
}
