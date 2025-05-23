package com.example.removie.document.kobis;

import com.example.removie.document.DocConnection;
import com.example.removie.document.POSTConnection;
import com.example.removie.kobis.KOBISCSRFToken;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotNull;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;


/**
 * HTML 로드에 필요한 Connection을 생성하는 객체입니다.
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
    public @Nonnull DocConnection cinemaPage(@NotNull String movieCode){
        return POSTConnection.of(Jsoup.connect(KOBIS.KOBIS_MAIN_CINEMA_URI.getValue()).
                ...blind...
        );
    }

    @Cacheable(value = "removieDatePage")
    public @Nonnull DocConnection removieDatePage(@NotNull String movieCode){
        return POSTConnection.of(Jsoup.connect(KOBIS.KOBIS_MAIN_CINEMA_URI.getValue()).
               ...blind...
        );
    }

    @Cacheable(value = "detailPage")
    public @Nonnull DocConnection detailPage(@NotNull String movieCode){
        return POSTConnection.of(Jsoup.connect(KOBIS.KOBIS_MOVIE_DETAIL_INFO_URI.getValue()).
                ...blind...
        );
    }

    @Cacheable(value = "mainPage")
    public @Nonnull DocConnection mainPage(){
        return POSTConnection.of(Jsoup.connect(KOBIS.KOBIS_MAIN_URI.getValue()).
                ...blind...
        );
    }


}
