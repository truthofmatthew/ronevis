package com.eggheadgames.siren;

@SuppressWarnings("WeakerAccess")
public interface ISirenListener {
    void onShowUpdateDialog();

    void onLaunchGooglePlay();

    void onSkipVersion();

    void onCancel();

    void onDetectNewVersionWithoutAlert(String message);

    void onError(Exception e);
}
