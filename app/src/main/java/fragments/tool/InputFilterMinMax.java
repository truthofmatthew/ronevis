package fragments.tool;

import android.text.InputFilter;
import android.text.Spanned;

public class InputFilterMinMax implements InputFilter {
    private final int min;
    private final int max;

    public InputFilterMinMax(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public InputFilterMinMax(String min, String max) {
        this.min = Integer.parseInt(min);
        this.max = Integer.parseInt(max);
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        try {
            int input;
            String newVal = dest.toString() + source.toString();
            if (newVal.length() == 1 && newVal.charAt(0) == '-') {
                input = min;
            } else {
                newVal = dest.toString().substring(0, dstart) + dest.toString().substring(dend, dest.toString().length());
                newVal = newVal.substring(0, dstart) + source.toString() + newVal.substring(dstart, newVal.length());
                input = Integer.parseInt(newVal);
            }
            if (isInRange(min, max, input))
                return null;
        } catch (NumberFormatException ignored) {
//            AcraLSender acraLSender = new AcraLSender();
//            acraLSender.TrySend(ApplicationLoader.appInstance(), ignored,"InputFilterMinMax_40");
        }
        return "";
    }

    private boolean isInRange(int a, int b, int c) {
        return b > a ? c >= a && c <= b : c >= b && c <= a;
    }
}