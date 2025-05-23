package com.example.removie.document.kobis;

import com.example.removie.document.DocConnect;
import com.example.removie.document.DocConnection;
import com.example.removie.kobis.exception.DocIOException;
import com.example.removie.kobis.exception.DocResponseNullException;
import com.example.removie.retry.IORetry;
import jakarta.annotation.Nonnull;
import lombok.NonNull;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * KOBIS 문서 호출하는 객체입니다.
 * <p>
 * {@link DocConnection}을 통해 전달받은 HTTP 연결 객체를 실행하고,
 * Jsoup의 {@link Document} 형태로 응답을 반환합니다.
 * <p>
 *
 * 빈 HTML 문서 여부를 검증하며, 네트워크 오류나 응답 실패 시 커스텀 예외를 발생시킵니다.
 *
 * @author An_Jicheol
 * @version 1.0
 */
@Component
public class KOBISDocConnect implements DocConnect {
    private final Logger logger = LoggerFactory.getLogger(KOBISDocConnect.class);

    /**
     * Jsoup 기반 HTML 응답을 요청하고, Document를 반환합니다.
     * <p>
     * 실패 시 로그를 기록하고 {@link DocIOException}을 발생시키며,
     * 응답이 빈 문서인 경우 {@link DocResponseNullException}을 던집니다.
     *
     * @param docConnection 요청 대상 URL 및 커넥션 전략을 포함한 객체
     * @return 응답 받은 Jsoup {@link Document}
     * @throws DocIOException         IO 오류가 발생한 경우
     * @throws DocResponseNullException Document의 HTML 내용이 비어 있는 경우
     */
    @IORetry
    public @Nonnull Document responseDoc(@NonNull DocConnection docConnection){
        try {
            Document document = docConnection.response();
            validateDocument(document);
            return document;

        } catch (IOException e) {
            logger.error("영화 리스트 페이지 리스폰 실패.");
            logger.debug("요청 내용 : \n {}", docConnection);
            throw new DocIOException("영화 리스트 페이지 리스폰 실패", e);
        }
    }

    /**
     * 응답받은 Document 객체가 유효한지 검사합니다.
     * HTML이 비어 있으면 예외를 발생시킵니다.
     *
     * @param document Jsoup 문서 객체
     * @throws DocResponseNullException HTML 내용이 비어 있는 경우
     */
    private void validateDocument(Document document){
        if(document.html().isEmpty()){
            throw new DocResponseNullException("Document 비어 있음");
        }
    }
}
