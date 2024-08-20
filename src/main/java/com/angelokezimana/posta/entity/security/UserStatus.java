package com.angelokezimana.posta.entity.security;

public enum UserStatus {
    ACTIVE("active"),
    LOCKED("locked"),
    PENDING("pending"),
    INACTIVE("inactive");

    final String value;

    UserStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
