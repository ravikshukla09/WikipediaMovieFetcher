package com.thinkanalytics.rks.wiki;

public class Utils {
    public static String cleanText(String text) {
        return text.replaceAll("\\[(.*?)\\]", "").replaceAll("\\\"", "");
    }

    public static String cleanStringDate(String text) {
        return text.replace("(", "").replace(")", "");
    }
}
