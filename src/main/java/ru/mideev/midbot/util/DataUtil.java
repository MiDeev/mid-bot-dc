package ru.mideev.midbot.util;

import net.dv8tion.jda.api.entities.Member;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class DataUtil {

    private static DiffData calculateDate(Member member) {
        String ti = Objects.requireNonNull(member).getTimeJoined()
                .atZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime()
                .format(DateTimeFormatter.ofPattern("yyyy-dd-MM HH:mm:ss"));

        SimpleDateFormat format = new SimpleDateFormat("yyyy-dd-MM HH:mm:ss");

        long timeUp = 0;
        try {
            timeUp = format.parse(ti).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long diff = System.currentTimeMillis() - timeUp;

        return new DiffData(diff);
    }

    public static String joinedDate(Member member) {

        return calculateDate(member).toString();
    }

    public static String createdDate(Member member) {

        return calculateDate(member).toString();
    }

    private record DiffData(long diff) {

        public long getDiffSeconds() {
            return diff / 1000 % 60;
        }

        public long getDiffMinutes() {
            return diff / (60 * 1000) % 60;
        }

        public long getDiffHours() {
            return diff / (60 * 60 * 1000) % 24;
        }

        public long getDiffDays() {
            return diff / (24 * 60 * 60 * 1000);
        }

        public String toString() {
            return getDiffDays() + " " + UtilLang.pluralsRu("день", "дня", "дней", (int) getDiffDays()) + ", " +
                    getDiffHours() + " " + UtilLang.pluralsRu("час", "часа", "часов", (int) getDiffHours()) + ", " +
                    getDiffMinutes() + " " + UtilLang.pluralsRu("минуту", "минуты", "минут", (int) getDiffMinutes()) + ", " +
                    getDiffSeconds() + " " + UtilLang.pluralsRu("секунду", "секунды", "секунд", (int) getDiffSeconds());
        }
    }
}
