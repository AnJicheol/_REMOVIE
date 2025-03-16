package com.example.removie.document.kobis;

import com.example.removie.document.DocConnect;
import com.example.removie.document.DocConnection;
import com.example.removie.kobis.exception.DocIOException;
import com.example.removie.kobis.exception.DocResponseNullException;
import com.example.removie.retry.IORetry;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * HTML을 로드하는 객체입니다.
 *
 * @author An_Jicheol
 * @version 1.0
 */
@Component
public class KOBISDocConnect implements DocConnect {
    private final Logger logger = LoggerFactory.getLogger(KOBISDocConnect.class);

    @IORetry
    public Document responseDoc(DocConnection docConnection){
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

    private void validateDocument(Document document){
        if(document.html().isEmpty()){
            throw new DocResponseNullException("Document 비어 있음");
        }
    }
}
