package com.petproject.workflow.store.entities;

public enum StorageSector {
    _858("56:21:1808001:858"),
    _1124("56:21:1808001:1124");

    private final String formatted;

    StorageSector(String formatted) {
        this.formatted = formatted;
    }

    public static StorageSector fromString(String formatted) {
        for (StorageSector sector : values()) {
            if (sector.formatted.equals(formatted)) {
                return sector;
            }
        }
        throw new IllegalArgumentException("No StorageSector for formatted string: " + formatted);
    }

    @Override
    public String toString() {
        return formatted;
    }
}