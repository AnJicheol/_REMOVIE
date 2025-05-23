package com.example.removie.document;

import org.jsoup.nodes.Document;


/**
 * {@code DocConnect}는 HTML 문서를 가져오기 위한 통합 인터페이스입니다.
 * 파싱 작업에서 발생하는 트래픽을 모니터링 하기 위해 설계하였습니다.
 *
 * <p>
 * {@link DocConnection} 구현체를 통해 네트워크 통신을 수행하고,
 * 결과로 반환된 {@link Document} 객체를 리턴합니다.
 * <p>
 *
 * 해당 인터페이스 구현체를 통해 호출하는 객체는 모니터링을 위해 {@link KOBISCall} 를 사용해야합니다.
 *
 * @author An_Jicheol
 * @version 2.0
 */
public interface DocConnect {

    /**
     * {@link DocConnection}을 사용해 HTML 문서를 요청하고, 해당 {@link Document}를 반환합니다.
     *
     * @param docConnection HTTP 요청을 위한 연결 객체
     * @return 응답받은 Jsoup {@link Document} 객체
     */
    Document responseDoc(DocConnection docConnection);
}
