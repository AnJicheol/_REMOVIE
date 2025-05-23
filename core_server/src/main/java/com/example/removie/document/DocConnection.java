package com.example.removie.document;

import org.jsoup.nodes.Document;
import java.io.IOException;

/**
 * Jsoup 기반 HTTP 연결 인터페이스입니다.
 *
 * <p>
 * HTML 문서를 요청을 생성하고, 요청 URL등 주요 정보를 정의합니다.
 * 다양한 HTTP 방식(GET, POST 등)에 따라 구현체가 분리될 수 있습니다.
 * <p>
 *
 * 해당 인터페이스에 구현체를 사용하는 객체는 메소드에 {@link KOBISCall}를 사용해야합니다.
 *
 * @author An_Jicheol
 * @version 2.0
 */
public interface DocConnection {

    Document response() throws IOException;
    String getUrl();
}
