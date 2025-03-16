package com.example.removie.aws.s3;


import com.example.removie.log.LogGroup;
import com.example.removie.retry.IORetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;


/**
 * AWS S3에 이미지를 업로드하는 서비스 클래스입니다.
 *
 * @author An_Jicheol
 * @version 1.0
 */
@Service
public class AWSS3Uploader {
    private final Logger logger = LoggerFactory.getLogger(AWSS3Uploader.class);
    private final S3Client s3Client;

    @Autowired
    public AWSS3Uploader(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    /**
     * @throws SdkClientException AWS SDK와 클라이언트 간 통신 문제 발생 시 예외 처리됨
     */
    @LogGroup
    @IORetry
    public void uploadIMG(RequestBody requestBody, PutObjectRequest putObjectRequest){

        try {
            s3Client.putObject(putObjectRequest, requestBody);
            
            
        } catch (SdkClientException e) {
            logger.error("SdkClientException - AWS SDK - Client 통신 문제 발생");
            logger.error("SdkClientException 상세 메시지: {}", e.getMessage());
        }
    }
}
