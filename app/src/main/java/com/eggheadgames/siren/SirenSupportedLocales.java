package com.eggheadgames.siren;

@SuppressWarnings("unused")
public enum SirenSupportedLocales {
    IR("fa"),
    EN("en");
    private final String locale;

    SirenSupportedLocales(String locale) {
        this.locale = locale;
    }

    public String getLocale() {
        return locale;
    }
}
