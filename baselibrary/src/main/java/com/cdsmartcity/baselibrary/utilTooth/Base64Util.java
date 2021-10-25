package com.cdsmartcity.baselibrary.utilTooth;

import android.util.Base64;

public class Base64Util {

    /**
     * @return 编码
     */
    public static String encode(String encodedString) {
        return Base64.encodeToString(encodedString.getBytes(), Base64.DEFAULT);
    }

    /**
     * @param encodedString 解码
     * @return
     */
    public static String docode(String encodedString) {
        return new String(Base64.decode(encodedString, Base64.DEFAULT));
    }
}
