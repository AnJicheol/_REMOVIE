package com.example.removie.cinema.kobis;

import com.example.removie.document.DocConnect;
import com.example.removie.document.KOBISCall;
import com.example.removie.document.kobis.KOBISPage;
import com.example.removie.log.LogGroup;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotBlank;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



/**
 * 영화관 코드 파싱을 위한 HTML Elements를 생성하는 클래스입니다.
 *
 * @author An_Jicheol
 * @version 2.0
 */
@Component
public class KOBISCinemaElement {
    private final static String CINEMA_CUT_DIV_CLASS_CSS_QUERY = "div.info.info2";
    private final static String CINEMA_CUT_TABLE_CSS_QUERY = "a[onclick]";

    private static final Logger logger = LoggerFactory.getLogger(KOBISCinemaElement.class);
    private final DocConnect docConnect;
    private final KOBISPage kobisPage;

    @Autowired
    public KOBISCinemaElement(DocConnect docConnect, KOBISPage kobisPage) {
        this.docConnect = docConnect;
        this.kobisPage = kobisPage;
    }

    /**
     * 영화 코드에 해당하는 상영관 코드 정보를 담고 있는 HTML 요소들을 반환합니다.
     * 정규식 성능을 고려하여 최소단위로 각 요소들을 분리합니다.
     *
     * @param movieCode 상영관 정보를 조회할 영화의 고유 코드 (비어 있을 수 없음)
     * @return 상영관 코드가 포함된 HTML 요소 목록; 정보가 없을 경우 빈 {@code Elements} 객체 반환
     */
    @LogGroup
    @KOBISCall
    public @Nonnull Elements getCinemaCodeElements(@NotBlank String movieCode){
        Document document = docConnect.responseDoc(kobisPage.cinemaPage(movieCode));
        Elements cinemaDivElements = document.select(CINEMA_CUT_DIV_CLASS_CSS_QUERY);
        Elements cinemaCodeElements = cinemaDivElements.select(CINEMA_CUT_TABLE_CSS_QUERY);

        if(cinemaCodeElements.isEmpty()){
            logger.error("영화관 코드 파싱 실패 : Elements 비어 있음");
            logger.debug(
                    """     
                            MovieCode : {}
                            1차 파싱 cinemaDivElements : {}
                            """,
                    movieCode, cinemaDivElements
            );
        }

        return cinemaCodeElements;
    }
}
