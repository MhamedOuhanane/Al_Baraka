package com.albaraka.albaraka.model.enums;

import lombok.Getter;

@Getter
public enum OauthProvider {

    LOCAL("Email & Password"),
    GOOGLE("Google Account"),
    FACEBOOK("Facebook Account"),
    GITHUB("GitHub Account");

    private final String descr;

    OauthProvider(String description) {
        this.descr = description;
    }
}

