package com.example.removie.document;


import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotNull;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Jsoup의 {@link Connection} 객체를 기반으로 하는 POST 방식 HTTP 연결 구현체입니다.
 *
 * <p>
 * 요청 시 타임아웃은 60초로 설정되며, {@code response()} 호출 시 Jsoup {@link Document}를 반환합니다.
 * 요청에 사용된 파라미터 정보는 {@link #toString()}을 통해 확인할 수 있습니다.
 *
 * @author An_Jicheol
 * @version 2.0
 */
public class POSTConnection implements DocConnection {
    private final Connection connection;

    public POSTConnection(Connection connection) {
        this.connection = connection;

    }

    @Override
    public @Nonnull Document response() throws IOException {
        return connection.timeout(60000).post();
    }

    @Override
    public String getUrl() {
        return connection.request().url().toString();
    }


    public static POSTConnection of(@NotNull Connection connection){
        return new POSTConnection(connection);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("POSTConnection {\n");
        sb.append("  URL: ").append(getUrl()).append("\n");

        sb.append("  }\n");
        sb.append("  Data: {\n");

        for (Connection.KeyVal keyVal : connection.request().data()) {
            sb.append("    ").append(keyVal.key()).append(": ").append(keyVal.value()).append("\n");
        }
        sb.append("  }\n");

        sb.append("}");
        return sb.toString();
    }
}
