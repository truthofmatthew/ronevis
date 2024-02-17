package com.azeesoft.lib.colorpicker;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;

class BitmapsGenerator {
    public static Bitmap getHueBitmap(int width, int height) {
        Bitmap hueBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        for (int x = 0; x < width; x++) {
            float hue = 0;
            if (width > height) {
                hue = (x * 360f) / width;
            }
            for (int y = 0; y < height; y++) {
                if (width <= height) {
                    hue = (y * 360f) / height;
                }
                float[] hsv = new float[]{hue, 1, 1};
                hueBitmap.setPixel(x, y, Color.HSVToColor(hsv));
            }
        }
        return hueBitmap;
    }

    @Deprecated
    public static Bitmap getSatValBitmapFrom(BitmapDrawable bitmapDrawable, float hue, int width, int height, int skipCount) {
        Bitmap bitmap = null;
        if (bitmapDrawable.getBitmap() != null) {
            int[] pixels = new int[width * height];
            bitmap = bitmapDrawable.getBitmap();
            bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
            for (int i = 0; i < pixels.length; i++) {
                float[] hsv = new float[3];
                Color.colorToHSV(pixels[i], hsv);
                hsv[0] = hue;
                pixels[i] = Color.HSVToColor(hsv);
            }
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        }
        return bitmap;
    }

    public static Bitmap getSatValBitmap(float hue, int width, int height, int skipCount) {
        Bitmap hueBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        int[] colors = new int[width * height];
        int pix = 0;
        for (int y = 0; y < height; y += skipCount) {
            for (int x = 0; x < width; x += skipCount) {
                if (pix >= (width * height))
                    break;
                float sat = (x) / (float) width;
                float val = ((height - y)) / (float) height;
                float[] hsv = new float[]{hue, sat, val};
                int color = Color.HSVToColor(hsv);
                for (int m = 0; m < skipCount; m++) {
                    if (pix >= (width * height))
                        break;
                    if ((x + m) < width) {
                        colors[pix] = color;
                        pix++;
                    }
                }
            }
            for (int n = 0; n < skipCount; n++) {
                if (pix >= (width * height))
                    break;
                for (int x = 0; x < width; x++) {
                    colors[pix] = colors[pix - width];
                    pix++;
                }
            }
        }
        hueBitmap.setPixels(colors, 0, width, 0, 0, width, height);
        return hueBitmap;
    }
}
