package ru.mideev.midbot.util;

import net.dv8tion.jda.api.entities.Member;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class DateUtil {

    public static String formatDate(Member member) {
        String ti = Objects.requireNonNull(member).getTimeJoined()
                .atZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime()
                .format(DateTimeFormatter.ofPattern("yyyy-dd-MM HH:mm:ss"));

        SimpleDateFormat format = new SimpleDateFormat("yyyy-dd-MM HH:mm:ss");

        long timeUp;

        try {
            timeUp = format.parse(ti).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

        long diff = System.currentTimeMillis() - timeUp;

        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;
        long diffDays = diff / (24 * 60 * 60 * 1000);

        return diffDays + " дней, " +
                diffHours + " часов, " +
                diffMinutes + " минут, " +
                diffSeconds + " секунд";
    }
}
