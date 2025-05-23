package com.example.removie.kobis.parser;


import com.example.removie.document.DocConnect;
import com.example.removie.document.KOBISCall;
import com.example.removie.document.kobis.KOBISPage;
import com.example.removie.movie.vo.ReleaseDate;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotBlank;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.time.DateTimeException;
import java.time.LocalDate;


/**
 * KOBIS API에서 영화의 개봉일 및 재개봉일 정보를 파싱하여 {@link ReleaseDate} 객체로 반환하는 클래스입니다.
 *
 * <p>
 * 기본 개봉일은 KOBIS 상세 페이지에서 추출하며, 일정 기준(3개월 이전 개봉 + DB 존재 여부)에 따라
 * 재개봉 여부를 판단하여 재개봉일 목록을 함께 구성합니다.
 * </p>
 *
 * <ul>
 *   <li>{@link DocConnect}를 통해 HTML 문서를 수신</li>
 *   <li>{@link KOBISPage}를 통해 요청 URL 생성</li>
 * </ul>
 *
 * @author An_Jicheol
 * @version 2.0
 */
@Component
public class KOBISMovieDateParser {
    private final static String MOVIE_DATE_CSS_QUERY = "dt:contains(개봉일) + dd";
    private final Logger logger = LoggerFactory.getLogger(KOBISMovieDateParser.class);
    private final DocConnect docConnect;
    private final KOBISPage kobisPage;


    @Autowired
    public KOBISMovieDateParser(DocConnect docConnect, KOBISPage kobisPage) {
        this.docConnect = docConnect;
        this.kobisPage = kobisPage;
    }

    /**
     * KOBIS에서 제공하는 영화 상세 페이지를 기반으로 개봉일 및 재개봉일을 파싱합니다.
     * <p>
     * 개봉일이 3개월 이전이며 DB에 재개봉 이력이 없으면 재개봉 날짜도 함께 수집합니다.
     * </p>
     * @param movieCode 영화의 고유 식별자 코드 (KOBIS 기준)
     * @return 해당 영화의 개봉일과 재개봉일 정보를 포함하는 {@link ReleaseDate}
     */
    @KOBISCall
    public @Nonnull ReleaseDate getParsingResult(@NotBlank String movieCode){
        Document document = docConnect.responseDoc(kobisPage.detailPage(movieCode));
        LocalDate date = createLocalDate(document);

        if(date == null) return new ReleaseDate(movieCode, LocalDate.now());
        return new ReleaseDate(movieCode, date);
    }

    /**
     * HTML 문서에서 개봉일 텍스트를 추출하고 {@link LocalDate}로 변환합니다.
     * <p>
     * 파싱 실패 시 {@code null}을 반환하며, 재개봉 여부 판단 기준에 사용됩니다.
     * </p>
     * @param document 개봉일 정보를 포함하는 HTML 문서
     * @return 파싱된 {@link LocalDate} 또는 실패 시 {@code null}
     */
    private LocalDate createLocalDate(Document document){
        String date = parseDateToDoc(document);

        if(date == null) return null;
        try {
            return LocalDate.parse(date);
        }

        catch (DateTimeException e ){
            logger.debug("상영 날짜 파싱 실패 : 날짜 형식이 맞지 않음 상세 내용 : {}", date);
            return null;
        }
    }

    private String parseDateToDoc(Document document){
        Element element = document.selectFirst(MOVIE_DATE_CSS_QUERY);

        if(element != null) return element.text();
        return null;
    }
}


