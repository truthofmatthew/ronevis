package com.eggheadgames.siren;

public enum SirenVersionCheckType {
    IMMEDIATELY(0),
    DAILY(1),
    WEEKLY(7);
    private final int value;

    SirenVersionCheckType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
