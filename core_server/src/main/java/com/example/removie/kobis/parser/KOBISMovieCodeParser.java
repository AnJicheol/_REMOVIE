package com.example.removie.kobis.parser;
import com.example.removie.kobis.exception.MovieCodeFailException;
import com.example.removie.log.LogGroup;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotNull;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 *
 * 영화 식별 코드 파싱 객체입니다.
 *
 * @author An_Jicheol
 * @version 2.0
**/
@Component
public class KOBISMovieCodeParser {
    private final static Pattern MOVIE_CODE_PATTERN = Pattern.compile("mstView\\('movie','(\\w+)'\\)");
    private final static String  MOVIE_CODE_CSS_QUERY = "span.ellip.per90 a";

    private final Logger logger = LoggerFactory.getLogger(KOBISMovieCodeParser.class);

    /**
     * @param element 영화 식별 코드 파싱에 필요한 HTML 객체입니다.
     * @return 영화 식별에 사용되는 코드입니다.
     * @throws MovieCodeFailException 영화 코드 파싱 실패 시 발생하는 예외입니다.
     */
    public @Nonnull String getMovieIdentity(@NotNull Element element) {
        Element movieCodeElement = element.selectFirst(MOVIE_CODE_CSS_QUERY);
        validateCssQuery(movieCodeElement);
        return regexMovieCode(movieCodeElement);
    }

    private String regexMovieCode(Element element) {
        Matcher matcher = getMatcher(element);
        validateMatcher(matcher);
        return matcher.group(1).trim();
    }

    private Matcher getMatcher(Element element) {
        return MOVIE_CODE_PATTERN.matcher(element.toString());
    }

    private void validateMatcher(Matcher matcher) {
        if (!matcher.find()) {
            logAndThrow("정규식 파싱 실패 : 정규식 패턴 매칭 실패");
        }
    }

    private void validateCssQuery(Element movieCodeElement) {
        if (movieCodeElement == null) {
            logAndThrow("CSS_Query 파싱 실패 : Element NULL");
        }
    }

    /**
     * 에러 메시지를 로깅한 후 {@link MovieCodeFailException}을 발생시킵니다.
     *
     * @param message 에러 로그 및 예외 메시지로 사용할 문자열
     * @throws MovieCodeFailException 주어진 메시지를 포함하여 즉시 예외를 발생시킵니다.
     */
    @LogGroup
    private void logAndThrow(String message) {
        logger.error(message);
        throw new MovieCodeFailException(message);
    }
}



