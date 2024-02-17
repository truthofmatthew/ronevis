package fragments.graphic;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;

import java.util.ArrayList;

import activities.MainActivity;
import fragments.tool.ArrayUtils;

public class UIColorArt {
    public static int[] GetColorsAll() {
        MainActivity.mainInstance().userMagicColor = ArrayUtils.removeDuplicate(concat(UpdateUIGetColor(), convert()));
        return MainActivity.mainInstance().userMagicColor;
    }

    private static int GetColorFromInt(int color) {
        String hexColor = String.format("#%08X", color);
        return Color.parseColor(hexColor);
    }

    private static int[] UpdateUIGetColor() {
        Bitmap bmpSource = ((BitmapDrawable) MainActivity.mainInstance().MainImageBG.getDrawable()).getBitmap();
        ColorArt colorArt = new ColorArt(bmpSource);
        return new int[]{GetColorFromInt(colorArt.getBackgroundColor()), GetColorFromInt(colorArt.getPrimaryColor()),
                GetColorFromInt(colorArt.getSecondaryColor()), GetColorFromInt(colorArt.getDetailColor())};
    }

    public static int[] convert(ArrayList<Integer> IntegerList) {
        int[] intArray = new int[IntegerList.size()];
        int count = 0;
        for (int i : IntegerList) {
            intArray[count++] = i;
        }
        return intArray;
    }

    private static int[] convert() {
        int[] intArray = new int[MainActivity.mainInstance().ArraySelectedColors.size()];
        int count = 0;
        for (int i : MainActivity.mainInstance().ArraySelectedColors) {
            intArray[count++] = GetColorFromInt(i);
        }
        return intArray;
    }

    private static int[] concat(int[] a, int[] b) {
        int aLen = a.length;
        int bLen = b.length;
        int[] c = new int[aLen + bLen];
        System.arraycopy(a, 0, c, 0, aLen);
        System.arraycopy(b, 0, c, aLen, bLen);
        return c;
    }
}
