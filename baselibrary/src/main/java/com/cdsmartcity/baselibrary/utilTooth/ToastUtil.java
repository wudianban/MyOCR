package com.cdsmartcity.baselibrary.utilTooth;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {

    private static Toast toast;

    /**
     * @param context 上下文
     * @param content 提示内容
     */
    public static void showMessageL(Context context, String content) {
        if (toast == null) {
            toast = Toast.makeText(context.getApplicationContext(), content, Toast.LENGTH_LONG);
        } else {
            toast.setText(content);
        }
        toast.show();
    }

    /**
     * @param context 上下文
     * @param content 提示内容
     */
    public static void showMessage(Context context, String content) {
        if (toast == null) {
            toast = Toast.makeText(context.getApplicationContext(), content, Toast.LENGTH_SHORT);
        } else {
            toast.setText(content);
        }
        toast.show();
    }
}
