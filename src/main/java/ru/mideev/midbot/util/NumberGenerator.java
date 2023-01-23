package ru.mideev.midbot.util;

public class NumberGenerator {
    public static String pad(int num) {
        String numStr = String.valueOf(num);

        if (numStr.length() < 4) {
            StringBuilder builder = new StringBuilder();

            for (int i = 4 - numStr.length(); i > 0; --i) {
                builder.append("0");
            }

            builder.append(numStr);

            return builder.toString();
        } else {
            return numStr;
        }
    }
}