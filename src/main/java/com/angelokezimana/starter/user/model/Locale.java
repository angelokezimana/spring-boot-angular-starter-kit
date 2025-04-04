package com.angelokezimana.starter.user.model;

public enum Locale {
    ENGLISH("en"),
    FRANCAIS("fr");

    final String value;

    Locale(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
