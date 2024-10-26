package com.uhaiin.controller;

import com.uhaiin.pojo.MinioPojo;
import com.uhaiin.service.MinioService;
import com.uhaiin.util.MinioUtil;
import com.uhaiin.web.ApiResponse;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/file")
public class MinioController {

	@Autowired
	private MinioUtil minioUtil;

	@Resource
	private MinioService minioService;

	/**
	 * 单一文件上传
	 * 
	 * @author Jiancai.zhong
	 * @param file 需要上传的对象
	 * @return ApiResponse<MinioPojo> 上传结果，包含上传文件名和上传后的文件URL
	 * @date 2024-10-25 09:47:30
	 */
	@PostMapping(value = "/upload")
	public ApiResponse<MinioPojo> uploadFile(@RequestParam MultipartFile file) {
		return minioService.uploadFile(file);
	}

	@PostMapping("/uploadMultipleFiles")
	public ApiResponse<String> uploadMultipleFiles(HttpServletRequest httpServletRequest) {
		MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) httpServletRequest;
		List<MultipartFile> files = multipartHttpServletRequest.getFiles("file");
		log.info("size :{}", files.size());
		for (MultipartFile item : files) {
			if (item.isEmpty()) {
				log.info("空文件");
			}

			String originalFilename = item.getOriginalFilename();
			log.info("fileName ==>{}", originalFilename);
		}
		return null;
	}

	/**
	 * 预览文件
	 */
	@GetMapping("/preview")
	public String preview(String fileName) {
		return minioUtil.getFileUrl(fileName);
	}

	/**
	 * 下载文件
	 */
	@GetMapping("/download")
	public void download(String fileName, HttpServletResponse response) {
		minioUtil.download(response, fileName);
	}

	/**
	 * 删除文件
	 */
	@GetMapping("/delete")
	public String delete(String fileName) {
		minioUtil.delete(fileName);
		return "删除成功";
	}
}