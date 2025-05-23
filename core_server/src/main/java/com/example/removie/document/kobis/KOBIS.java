package com.example.removie.document.kobis;

/**
 * 스크래핑 URI
 *
 * @author An_Jicheol
 * @version 2.0
 */
public enum KOBIS {

    KOBIS_BASE_URI_FOR_IMG(...blind...),
    KOBIS_MAIN_URI(...blind...),
    KOBIS_MAIN_CINEMA_URI(...blind...),
    KOBIS_MOVIE_DETAIL_INFO_URI(...blind...);

    private final String value;

    KOBIS(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}


