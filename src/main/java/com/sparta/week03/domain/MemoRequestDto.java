package com.sparta.week03.domain;

import lombok.Getter;


@Getter
public class MemoRequestDto {
    private String title;
    private String username;
    private String contents;
    private String pw;
}
