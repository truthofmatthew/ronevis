package fragments.views;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

class TextState extends View.BaseSavedState {
    public static final Creator<TextState> CREATOR
            = new Creator<TextState>() {
        public TextState createFromParcel(Parcel in) {
            return new TextState(in);
        }

        public TextState[] newArray(int size) {
            return new TextState[size];
        }
    };
    String text;

    TextState(Parcelable superState) {
        super(superState);
    }

    private TextState(Parcel in) {
        super(in);
        text = (String) in.readValue(null);
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);
        out.writeValue(text);
    }

    @Override
    public String toString() {
        return "HorizontalWheelView.SavedState{"
                + Integer.toHexString(System.identityHashCode(this))
                + " angle=" + text + "}";
    }
}
