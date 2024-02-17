/*
 * This is the source code of Telegram for Android v. 3.x.x.
 * It is licensed under GNU GPL v. 2 or later.
 * You should have received a copy of the license in this archive (see LICENSE).
 *
 * Copyright Nikolai Kudashov, 2013-2016.
 */
package fragments.MTViews;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class KeyboardLayoutFixed extends FrameLayoutFixed implements SizeNotifierFrameLayout.SizeNotifierFrameLayoutDelegate {
    private final ArrayList<View> mMatchParentChildren = new ArrayList<>(1);
    private SizeNotifierFrameLayout sizeNotifierLayout;

    public KeyboardLayoutFixed(Context context) {
        super(context);
    }

    public KeyboardLayoutFixed(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
//    public boolean isKeyboardVisible() {
//        return keyboardVisible;
//    }
//    public void openKeyboard() {
//        AndroidUtilities.showKeyboard(messageEditText);
//    }
//
//    public void closeKeyboard() {
//        AndroidUtilities.hideKeyboard(messageEditText);
//    }
//
//    public boolean hasText() {
//        return messageEditText != null && messageEditText.length() > 0;
//    }
//
//    public int getCursorPosition() {
//        if (messageEditText == null) {
//            return 0;
//        }
//        return messageEditText.getSelectionStart();
//    }
//    public void onDestroy() {
//
//        if (mWakeLock != null) {
//            try {
//                mWakeLock.release();
//                mWakeLock = null;
//            } catch (Exception e) {
//                FileLog.e("tmessages", e);
//            }
//        }
//        if (sizeNotifierLayout != null) {
//            sizeNotifierLayout.setDelegate(null);
//        }
//    }
//
//    public void onPause() {
//        isPaused = true;
//        closeKeyboard();
//    }
//
//    private void onWindowSizeChanged() {
//        int size = sizeNotifierLayout.getHeight();
//        if (!keyboardVisible) {
//            size -= emojiPadding;
//        }
//        if (delegate != null) {
//            delegate.onWindowSizeChanged(size);
//        }
//        if (topView != null) {
//            if (size < AndroidUtilities.dp(72) + ActionBar.getCurrentActionBarHeight()) {
//                if (allowShowTopView) {
//                    allowShowTopView = false;
//                    if (needShowTopView) {
//                        topView.setVisibility(View.GONE);
//                        setTopViewAnimation(0.0f);
//                    }
//                }
//            } else {
//                if (!allowShowTopView) {
//                    allowShowTopView = true;
//                    if (needShowTopView) {
//                        topView.setVisibility(View.VISIBLE);
//                        setTopViewAnimation(1.0f);
//                    }
//                }
//            }
//        }
//    }

    public KeyboardLayoutFixed(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
//        sizeNotifierLayout = this;
        sizeNotifierLayout.setDelegate(this);
        sizeNotifierLayout.addView(this);
    }

    @Override
    public void onSizeChanged(int height, boolean isWidthGreater) {
        Log.d("MTMTMTMT", "height " + height + " " + isWidthGreater);
//        if (height > AndroidUtilities.dp(50) && keyboardVisible) {
//            if (isWidthGreater) {
//                keyboardHeightLand = height;
//                ApplicationLoader.applicationContext.getSharedPreferences("emoji", 0).edit().putInt("kbd_height_land3", keyboardHeightLand).commit();
//            } else {
//                keyboardHeight = height;
//                ApplicationLoader.applicationContext.getSharedPreferences("emoji", 0).edit().putInt("kbd_height", keyboardHeight).commit();
//            }
//        }
//        if (isPopupShowing()) {
//            int newHeight = isWidthGreater ? keyboardHeightLand : keyboardHeight;
//            if (currentPopupContentType == 1 && !botKeyboardView.isFullSize()) {
//                newHeight = Math.min(botKeyboardView.getKeyboardHeight(), newHeight);
//            }
//
//            View currentView = null;
//            if (currentPopupContentType == 0) {
//                currentView = emojiView;
//            } else if (currentPopupContentType == 1) {
//                currentView = botKeyboardView;
//            }
//            if (botKeyboardView != null) {
//                botKeyboardView.setPanelHeight(newHeight);
//            }
//
//            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) currentView.getLayoutParams();
//            if (layoutParams.width != AndroidUtilities.displaySize.x || layoutParams.height != newHeight) {
//                layoutParams.width = AndroidUtilities.displaySize.x;
//                layoutParams.height = newHeight;
//                currentView.setLayoutParams(layoutParams);
//                if (sizeNotifierLayout != null) {
//                    emojiPadding = layoutParams.height;
//                    sizeNotifierLayout.requestLayout();
//                    onWindowSizeChanged();
//                }
//            }
//        }
//        if (lastSizeChangeValue1 == height && lastSizeChangeValue2 == isWidthGreater) {
//            onWindowSizeChanged();
//            return;
//        }
//        lastSizeChangeValue1 = height;
//        lastSizeChangeValue2 = isWidthGreater;
//
//        boolean oldValue = keyboardVisible;
//        keyboardVisible = height > 0;
//        if (keyboardVisible && isPopupShowing()) {
//            showPopup(0, currentPopupContentType);
//        }
//        if (emojiPadding != 0 && !keyboardVisible && keyboardVisible != oldValue && !isPopupShowing()) {
//            emojiPadding = 0;
//            sizeNotifierLayout.requestLayout();
//        }
//        if (keyboardVisible && waitingForKeyboardOpen) {
//            waitingForKeyboardOpen = false;
//            AndroidUtilities.cancelRunOnUIThread(openKeyboardRunnable);
//        }
//        onWindowSizeChanged();
    }
//    private boolean waitingForKeyboardOpen;
//    private boolean showKeyboardOnResume;
//    private int keyboardHeight;
//    private int keyboardHeightLand;
//    private boolean keyboardVisible;
//    private int emojiPadding;
//    private int lastSizeChangeValue1;
//    private boolean lastSizeChangeValue2;
//    private Runnable openKeyboardRunnable = new Runnable() {
//        @Override
//        public void run() {
//            if (messageEditText != null && waitingForKeyboardOpen && !keyboardVisible && !AndroidUtilities.usingHardwareInput) {
//                messageEditText.requestFocus();
//                AndroidUtilities.showKeyboard(messageEditText);
//                AndroidUtilities.cancelRunOnUIThread(openKeyboardRunnable);
//                AndroidUtilities.runOnUIThread(openKeyboardRunnable, 100);
//            }
//        }
//    };
}
