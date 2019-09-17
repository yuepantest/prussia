package com.example.prussia.tools;

import android.text.Html;
import android.text.Spanned;

/**
 * author：Prussia
 * date：2018/9/6
 * describe：
 */

public class HtmlTools {
    /**
     * @param color lolor不能拥有透明度的八位色值
     */
    public static Spanned toHtml(String startStr, String changeStr, String endStr, String color) {
        Spanned spanned = Html.fromHtml(startStr + "<font color='" + color + "'>" + changeStr + "</font>" + endStr);
        return spanned;
    }
}
