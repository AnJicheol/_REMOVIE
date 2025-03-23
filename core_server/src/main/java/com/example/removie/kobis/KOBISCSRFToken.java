package com.example.removie.kobis;

import com.example.removie.document.kobis.KOBIS;
import com.example.removie.kobis.exception.KOBISCSRFTokenFailException;
import com.example.removie.kobis.exception.KOBISCSRFTokenNullException;
import com.example.removie.retry.IORetry;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;


import java.io.IOException;


/**
 * 파싱에 필요한 CORSTOken을 생성하는 클래스입니다.
 *
 * @author An_Jicheol
 * @version 1.0
 */
@Component
public class KOBISCSRFToken {
    private final static String KOBIS_CSRF_TOKEN_ELEMENT_CSS_QUERY = "input[name=CSRFToken]";
    private final static String KOBIS_CSRF_TOKEN_ATTR_KEY = "value";

    private final Logger logger = LoggerFactory.getLogger(KOBISCSRFToken.class);


    /**
     * @return CORSToken을 리턴합니다.
     * @throws KOBISCSRFTokenNullException 토큰 파싱에 실패 했을 경우 발생 합니다.
     * @throws KOBISCSRFTokenFailException 토큰 파싱에 실패 했을 경우 발생 합니다.
     */
    @IORetry
    @Cacheable(value = "token")
    public String getCSRFToken(){

        try {
            Document document = Jsoup.connect(KOBIS.KOBIS_MAIN_URI.getValue()).timeout(15000).get();
            Element csrfTokenE = document.selectFirst(KOBIS_CSRF_TOKEN_ELEMENT_CSS_QUERY);

            if(csrfTokenE == null){

                logger.error("페이지 변경 가능성 있음 - 토큰 Element 파싱 실패 : Element 가 NULL");
                throw new KOBISCSRFTokenNullException("토큰 Element 파싱 실패 : Element 가 NULL");
            }

            return csrfTokenE.attr(KOBIS_CSRF_TOKEN_ATTR_KEY);
        } catch (IOException e) {
            throw new KOBISCSRFTokenFailException ("CSRFToken 리스폰 실패", e);
        }
    }

}
