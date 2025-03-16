package com.example.removie.document.kobis;

public enum KOBIS {

    KOBIS_BASE_URI_FOR_IMG("KOBIS 관련 정보로 제거하였습니다"),
    KOBIS_MAIN_URI("KOBIS 관련 정보로 제거하였습니다"),
    KOBIS_MAIN_CINEMA_URI("KOBIS 관련 정보로 제거하였습니다"),
    KOBIS_MOVIE_DETAIL_INFO_URI("KOBIS 관련 정보로 제거하였습니다");

    private final String value;

    KOBIS(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}


