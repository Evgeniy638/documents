package com.evgeniy638.documents.modules;

public enum Role {
    STUDENT("STUDENT"),
    ADMIN("ADMIN");

    private final String text;

    Role(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
