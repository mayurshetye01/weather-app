package com.gloresoft.weatherapp.backend.config;

public enum Roles {
    ADMIN ("ROLE_ADMIN"),
    USER ("ROLE_USER");

    private String value;

    public String value() {
        return this.value;
    }

    Roles(String value) {
        this.value = value;
    }
}
