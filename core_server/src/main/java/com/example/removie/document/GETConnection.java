package com.example.removie.document;

import org.jsoup.Connection;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class GETConnection implements DocConnection {
    private final Connection connection;

    public GETConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Document response() throws IOException {
        return connection.get();
    }

    @Override
    public String getUrl() {
        return connection.request().url().toString();
    }

    public static GETConnection of(Connection connection){
        return new GETConnection(connection);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("GETConnection {\n");
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
