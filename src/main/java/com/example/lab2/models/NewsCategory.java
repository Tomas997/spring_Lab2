package com.example.lab2.models;

public enum NewsCategory {
    SPORTS("Спорт"),
    POLITICS("Політика"),
    TECHNOLOGY("Технології"),
    HEALTH("Здоров'я"),
    ENTERTAINMENT("Розваги"),
    BUSINESS("Бізнес"),
    SCIENCE("Наука");

    private final String displayName;

    NewsCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
