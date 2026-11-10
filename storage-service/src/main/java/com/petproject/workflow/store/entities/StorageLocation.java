package com.petproject.workflow.store.entities;

public enum StorageLocation {
    REGENERATION_PLANT("Цех регенерации"),
    RAMP("Пандус"),
    TANK_FARM("Ёмкостной парк");

    private final String formatted;

    StorageLocation(String formatted) {
        this.formatted = formatted;
    }

    public static StorageLocation fromString(String formatted) {
        for (StorageLocation location : values()) {
            if (location.formatted.equals(formatted)) {
                return location;
            }
        }
        throw new IllegalArgumentException("No StorageLocation for formatted string: " + formatted);
    }

    @Override
    public String toString() {
        return formatted;
    }
}