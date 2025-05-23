package com.example.removie.cinema.kobis;

import com.example.removie.cinema.exception.CinemaParsingFailException;
import com.example.removie.cinema.CinemaInfoProvider;
import com.example.removie.log.LogGroup;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotBlank;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 영화관 정보 파싱하는 클래스입니다.
 *
 * @author An_Jicheol
 * @version 2
 */
@Component
public class KOBISCinemaDataParser {
    private final static Pattern CINEMA_CODE_REGEX_PATTERN = Pattern.compile("onclick=\"fn_theaNmClick\\(this, '.*?', '(.*?)', '.*?'\\)");
    private static final Logger logger = LoggerFactory.getLogger(KOBISCinemaDataParser.class);

    private final CinemaInfoProvider cinemaInfoProvider;

    @Autowired
    public KOBISCinemaDataParser(CinemaInfoProvider cinemaInfoProvider) {
        this.cinemaInfoProvider = cinemaInfoProvider;
    }

    /**
     * HTML 요소에서 영화관 정보를 추출합니다.
     * 정규식 패턴을 사용해 영화관 관련 정보를 파싱하고,
     *
     * @param element 영화관 정보가 포함된 HTML 요소 (비어 있으면 안 됨)
     * @return 추출된 영화관 정보
     * @throws CinemaParsingFailException 정규식 패턴과 일치하지 않을 경우 발생
     */
    public @Nonnull String extractCinemaData(@NotBlank Element element) {
        Matcher matcher = getMatcher(element);
        verifiedMatcher(matcher, element);

        String cinemaData = cinemaInfoProvider.getMovieInfo(matcher.group(1));
        verifiedCinemaData(cinemaData, element);
        return cinemaData;
    }

    private Matcher getMatcher(@NotBlank Element element){
        return CINEMA_CODE_REGEX_PATTERN.matcher(element.toString());
    }

    /**
     * 정규식 매칭 결과(Matcher)가 유효한지 검사합니다.
     * 매칭에 실패할 경우 로그를 남기고 예외를 발생시킵니다.
     *
     * @param matcher 정규식 매칭 결과
     * @param element 검사 대상인 HTML 요소
     * @throws CinemaParsingFailException 정규식 매칭 실패 시 발생
     */
    private void verifiedMatcher(Matcher matcher, Element element){
        if (!matcher.find()) {
            log("패턴 매칭 실패", element);
            throw new CinemaParsingFailException("상영관 코드 정규식 파싱 실패 : 패턴 매칭 실패");
        }
    }

    private void verifiedCinemaData(String cinemaData, Element element){
        if (cinemaData == null) {
            log("영화관 코드가 유효하지 않음", element);
        }
    }

    @LogGroup
    private void log(String message , Element element){
        logger.error("상영관 파싱 실패 : {} ", message);
        logger.debug("Element : {}", element);
    }
}
