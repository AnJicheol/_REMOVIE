package com.example.removie.document.kobis;

import com.example.removie.document.DocConnection;
import com.example.removie.document.POSTConnection;
import com.example.removie.kobis.KOBISCSRFToken;
import lombok.NonNull;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;


/**
 * HTML 로드에 필요한 Connection을 생성하는 객체입니다
 *
 * @author An_Jicheol
 * @version 1.0
 */
@Component
public class KOBISPage {
    private final KOBISCSRFToken kobiscsrfToken;

    public KOBISPage(KOBISCSRFToken kobiscsrfToken) {
        this.kobiscsrfToken = kobiscsrfToken;
    }

    @Cacheable(value = "cinemaPage")
    public DocConnection cinemaPage(@NonNull String movieCode){
        return POSTConnection.of(Jsoup.connect(KOBIS.KOBIS_MAIN_CINEMA_URI.getValue()).
                method(Connection.Method.POST).
                KOBIS 관련 정보로 제거하였습니다.
                data("CSRFToken", kobiscsrfToken.getCSRFToken())
        );
    }

    @Cacheable(value = "removieDatePage")
    public DocConnection removieDatePage(@NonNull String movieCode){
        return POSTConnection.of(Jsoup.connect(KOBIS.KOBIS_MAIN_CINEMA_URI.getValue()).
                method(Connection.Method.POST).
                KOBIS 관련 정보로 제거하였습니다.
                data("CSRFToken", kobiscsrfToken.getCSRFToken())
        );
    }

    @Cacheable(value = "detailPage")
    public DocConnection detailPage(String movieCode){
        return POSTConnection.of(Jsoup.connect(KOBIS.KOBIS_MOVIE_DETAIL_INFO_URI.getValue()).
                method(Connection.Method.POST).
                KOBIS 관련 정보로 제거하였습니다.
                data("CSRFToken", kobiscsrfToken.getCSRFToken())
        );
    }

    @Cacheable(value = "mainPage")
    public DocConnection mainPage(){
        return POSTConnection.of(Jsoup.connect(KOBIS.KOBIS_MAIN_URI.getValue()).
                method(Connection.Method.POST).
                KOBIS 관련 정보로 제거하였습니다.
                data("CSRFToken", kobiscsrfToken.getCSRFToken()).
                maxBodySize(0)
        );
    }


}
