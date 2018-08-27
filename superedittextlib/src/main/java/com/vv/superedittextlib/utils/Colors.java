package com.vv.superedittextlib.utils;

import android.graphics.Color;

/**
 * @author pvphero
 * @date 2018/8/27
 */
public class Colors {
    public static boolean isLight(int color) {
        return Math.sqrt(
                Color.red(color) * Color.red(color) * .241 +
                        Color.green(color) * Color.green(color) * .691 +
                        Color.blue(color) * Color.blue(color) * .068) > 130;
    }
}
