package com.example.removie_read_server.enums;

import lombok.Getter;

@Getter
public enum AWS {
    AWS_S3_URL("민감한 정보는 제거하였습니다.");

    private final String value;

    AWS(String value) {
        this.value = value;
    }
}
