package com.example.removie.kobis;


import com.example.removie.kobis.exception.KOBISAPIException;
import com.example.removie.kobis.wrapper.MovieDataWrapper;
import com.example.removie.movie.vo.MovieData;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * 영화 데이터를 생성하는 클래스입니다.
 *
 * @author An_Jicheol
 * @version 2.0
 */
@Service
public class KOBISMovieDataService implements MovieDataService {
    private final Logger logger = LoggerFactory.getLogger(KOBISMovieDataService.class);
    @Value("${kobis.api.key}")
    private String kobisApiKey;
    private final RestClient restClient;

    @Autowired
    public KOBISMovieDataService(RestClient restClient) {
        this.restClient = restClient;
    }

    /**
     * 영화 코드에 해당하는 영화 상세 정보를 KOBIS API를 통해 조회합니다.
     *
     * @param movieCode KOBIS에서 발급한 영화 고유 코드
     * @return {@link MovieData} 객체 (영화 제목, 장르 등 포함)
     * @throws KOBISAPIException API 호출 실패 시 발생
     */
    @Override
    public @Nonnull MovieData getMovieData(@NotBlank String movieCode){
        return responseApi(movieCode);
    }


    /**
     * @param movieCode KOBIS API에서 조회할 영화의 고유 식별자 (필수)
     * @return API 응답에서 추출된 {@link MovieData} 객체
     * @throws KOBISAPIException 외부 API 호출 실패, 응답 누락, 파싱 오류 등의 경우 발생
     */
    private MovieData responseApi(String movieCode) {
        try {

            String uri = buildKobisApiUri(movieCode);
            MovieDataWrapper wrapper = fetchMovieDataWrapper(uri);
            validateResponse(wrapper, movieCode);

            return wrapper.getMovieData();

        } catch (Exception e) {
            logger.error("API 호출 실패 movieCode: {}", movieCode, e);
            throw new KOBISAPIException("Error fetching movie data: " + e.getMessage());
        }
    }

    private String buildKobisApiUri(String movieCode) {
        return UriComponentsBuilder.newInstance()
                ...blind...
    }

    private MovieDataWrapper fetchMovieDataWrapper(String uri) {
        return restClient.get()
                .uri(uri)
                .retrieve()
                .body(MovieDataWrapper.class);
    }

    /**
     * @param wrapper 외부 API 응답을 감싸는 객체
     * @param movieCode 검사 대상 영화 코드 (로깅 용도)
     * @throws KOBISAPIException 응답이 null이거나 유효하지 않은 경우 발생
     */
    private void validateResponse(MovieDataWrapper wrapper, String movieCode) {
        if (wrapper == null || wrapper.getMovieData() == null) {
            logger.error("API 호출 실패 Body 비어 있음 movieCode: {}", movieCode);
            throw new KOBISAPIException("Body is null");
        }
    }

}
