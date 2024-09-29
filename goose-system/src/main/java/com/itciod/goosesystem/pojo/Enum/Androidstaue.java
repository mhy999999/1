package com.itciod.goosesystem.pojo.Enum;

public enum Androidstaue {
    NOT_ACTIVATED("未激活"),
    ACTIVATED("已激活");


    private final String description;

    Androidstaue(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
