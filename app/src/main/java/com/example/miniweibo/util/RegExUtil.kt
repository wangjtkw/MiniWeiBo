package com.example.miniweibo.util

import java.util.regex.Pattern

object RegExUtil {
    fun parseSourceTitle(source: String): String {
        val p = Pattern.compile("<a[^>]*>([^<]*)</a>")
        val m = p.matcher(source)
        return if (m.find()) {
            m.group(1) ?: ""
        } else {
            ""
        }
    }

    fun parseSourceUrl(source: String): String {
        val mailreg = "<a(?:\\s+.)*?\\s+href=\"([^\"]*?)\""
        val p = Pattern.compile(mailreg)
        val m = p.matcher(source)
        return if (m.find()) {
            m.group(1) ?: ""
        } else {
            ""
        }
    }

}