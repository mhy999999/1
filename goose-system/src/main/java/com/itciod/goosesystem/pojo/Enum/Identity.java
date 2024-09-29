package com.itciod.goosesystem.pojo.Enum;

public enum Identity {
    NORMAL("普通用户"),
    ADMIN("管理员");


    private final String description;

    Identity(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }




}
