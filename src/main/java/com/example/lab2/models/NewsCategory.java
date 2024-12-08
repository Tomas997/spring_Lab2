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

    public static NewsCategory fromDisplayName(String displayName) {
        for (NewsCategory category : values()) {
            if (category.getDisplayName().equalsIgnoreCase(displayName)) {
                return category;
            }
        }
        throw new IllegalArgumentException("Invalid category name: " + displayName);
    }
}
