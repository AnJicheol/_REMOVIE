package com.example.removie.aws.s3;

import com.example.removie.kobis.parser.KOBISMovieIMGUriParser;
import com.example.removie.log.LogGroup;
import com.example.removie.movie.exception.InvalidImageException;
import jakarta.annotation.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URL;


/**
 *  파싱 된 이미지를 AWS S3에 업로드 후 업로드된 이미지의 URL을 리턴합니다.
 *
 * @author An_Jicheol
 * @version 1.0
 */
@Service
public class AWSAWSMovieIMGURLServiceImpl implements AWSMovieIMGURLService {
    private final Logger logger = LoggerFactory.getLogger(AWSAWSMovieIMGURLServiceImpl.class);

    private final KOBISMovieIMGUriParser kobisMovieIMGUriParser;
    private final AWSS3Uploader AWSS3Uploader;

    @Value("${aws.s3.bucketName}")
    private String buket;
    @Value("${aws.s3.front.url}")
    private String frontUrl;

    @Autowired
    public AWSAWSMovieIMGURLServiceImpl(KOBISMovieIMGUriParser kobisMovieIMGUriParser, AWSS3Uploader AWSS3Uploader) {
        this.kobisMovieIMGUriParser = kobisMovieIMGUriParser;
        this.AWSS3Uploader = AWSS3Uploader;
    }

    @LogGroup
    @Override
    public @Nullable String uploadMovieImageAndReturnURL(String movieCode) {
        String imgURLData = kobisMovieIMGUriParser.getParsingResult(movieCode);
        if(imgURLData == null) return null;

        try (ByteArrayOutputStream byteArrayOutputStream = createImgByteStream(createURL(imgURLData))){
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(buket)
                    .key(movieCode)
                    .build();

            RequestBody requestBody = RequestBody.fromInputStream(
                    new ByteArrayInputStream(byteArrayOutputStream.toByteArray()),
                    byteArrayOutputStream.size()
            );

            AWSS3Uploader.uploadIMG(requestBody, putObjectRequest);


        } catch (InvalidImageException e) {
            logger.error("이미지 형식이 맞지 않음: MovieCode : {}", movieCode);
            logger.debug("디테일 : {}", e.getMessage(), e);
            return null;
        } catch (IOException e) {
            logger.error("이미지 불러 오기 실패: {}", e.getMessage(), e);
            return null;
        }

        return String.format(frontUrl, movieCode);
    }

    private URL createURL(String imgURL) throws IOException {
        return URI.create(imgURL).toURL();
    }

    /**
     * 이미지를 검증을 수행합니다.
     *
     * @param url 이미지 url
     * @return 이미지 byte
     * @throws IOException 이미지 형식 오류
     */
    private ByteArrayOutputStream createImgByteStream(URL url) throws IOException{
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(imgLoad(url), "jpg", byteArrayOutputStream);

        return byteArrayOutputStream;
    }

    private BufferedImage imgLoad(URL url) throws InvalidImageException, IOException {
        BufferedImage image = ImageIO.read(url);

        if (image == null || image.getWidth() <= 0 || image.getHeight() <= 0) {
            throw new InvalidImageException("이미지 형식이 맞지 않음 : 이미지 NULL");
        }
        return image;
    }
}
