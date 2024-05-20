package ru.mideev.midbot.util;

import net.dv8tion.jda.api.entities.Member;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class DateUtil {
    public static String joinedDate(Member member) {
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

        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;
        long diffDays = diff / (24 * 60 * 60 * 1000);

        StringBuilder sb = new StringBuilder();

        sb.append(diffDays + " " + UtilLang.pluralsRu("день", "дня", "дней", (int) diffDays) + ", ");
        sb.append(diffHours + " " + UtilLang.pluralsRu("час", "часа", "часов", (int) diffHours) + ", ");
        sb.append(diffMinutes + " " + UtilLang.pluralsRu("минуту", "минуты", "минут", (int) diffMinutes) + ", ");
        sb.append(diffSeconds + " " + UtilLang.pluralsRu("секунду", "секунды", "секунд", (int) diffSeconds) + "");
        return sb.toString();
    }

    public static String createdDate(Member member) {
        String ti = Objects.requireNonNull(member).getTimeCreated()
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

        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;
        long diffDays = diff / (24 * 60 * 60 * 1000);

        StringBuilder du = new StringBuilder();

        du.append(diffDays + " " + UtilLang.pluralsRu("день", "дня", "дней", (int) diffDays) + ", ");
        du.append(diffHours + " " + UtilLang.pluralsRu("час", "часа", "часов", (int) diffHours) + ", ");
        du.append(diffMinutes + " " + UtilLang.pluralsRu("минуту", "минуты", "минут", (int) diffMinutes) + ", ");
        du.append(diffSeconds + " " + UtilLang.pluralsRu("секунду", "секунды", "секунд", (int) diffSeconds) + "");
        return du.toString();
    }
}
