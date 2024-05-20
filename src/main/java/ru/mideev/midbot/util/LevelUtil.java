package ru.mideev.midbot.util;

public class LevelUtil {
    public static long getExperience(long level) {
        return ((long) Math.sqrt(99 * level * (level / 1.4))) * 12 + 3;
    }
}
