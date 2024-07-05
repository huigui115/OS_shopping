package com.example.myapplication.util;

import ohos.agp.colors.RgbColor;
import ohos.agp.components.AttrHelper;
import ohos.agp.components.DirectionalLayout;
import ohos.agp.components.Text;
import ohos.agp.components.element.ShapeElement;
import ohos.agp.utils.TextAlignment;
import ohos.agp.window.dialog.ToastDialog;
import ohos.app.Context;

/**
 * The Toast
 */
public class ToastUtil {
    /**
     * 1000ms
     */
    public static final int TOAST_SHORT = 1000;

    /**
     * 2000ms
     */
    public static final int TOAST_LONG = 2000;

    public static void makeToast(Context context, String text, int duration) {

        ToastDialog toastDialog = new ToastDialog(context);
        toastDialog

                .setDuration(duration)
                .setText(text)
                .setDuration(duration)
                .show();

    }
}
