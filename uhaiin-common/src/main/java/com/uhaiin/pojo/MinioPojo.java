package com.uhaiin.pojo;

import lombok.Data;

/**
 * 文件上传对象实体类
 *
 * @author Jiancai.zhong
 * @date 2024-10-24 04:22:37
 */
@Data
public class MinioPojo {
    private String fileName;
    private String fileUrl;
    private String savePath;
    private String content;
}
