package ru.mideev.midbot.util;

public class NumberGenerator {
    public static String pad(int num) {
        String numStr = String.valueOf(num);

        if (numStr.length() < 4) {

            return "0".repeat(4 - numStr.length()) +
                    numStr;
        } else {
            return numStr;
        }
    }
}