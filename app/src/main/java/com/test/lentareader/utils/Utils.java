package com.test.lentareader.utils;

import android.os.Build;
import android.text.Html;
import android.text.Spanned;

class Utils {
    static Spanned fromHtml(String aHtml) {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
                ? Html.fromHtml(aHtml, Html.FROM_HTML_MODE_LEGACY)
                : Html.fromHtml(aHtml);
    }
}
