package com.sparta.vlog.dto;

import lombok.Builder;

public class DeleteResultDto {

    private String msg;
    private int statusCode;

    @Builder
    public DeleteResultDto(String msg, int statusCode) {
        this.msg = msg;
        this.statusCode = statusCode;
    }
}
