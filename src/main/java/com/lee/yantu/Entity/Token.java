package com.lee.yantu.Entity;


import lombok.Data;

@Data
public class Token {
    private Integer userId;
    private String email;

    public Token() {
    }

    public Token(Integer userId, String email) {
        this.userId = userId;
        this.email = email;
    }
}
