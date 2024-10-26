package com.uhaiin.config;

import io.minio.MinioClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "minio")
public class MinIOConfig {
	private String accessKey;
	private String secretKey;
	private String url;
	private String bucketName;

    @Bean
    MinioClient minioClient() {
		return MinioClient.builder().endpoint(url).credentials(accessKey, secretKey).build();
	}
}