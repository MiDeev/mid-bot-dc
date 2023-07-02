package ru.mideev.midbot.util;

import net.dv8tion.jda.api.entities.Message;

public class HandlerUtil {

    public static String getAnswer(Message current, Message message) {

        String[] mass = current.getContentRaw().split(" ");
        String[] dist = new String[mass.length - 1];
        System.arraycopy(mass, 1, dist, 0, mass.length - 1);

        return String.join(" ", dist);
    }

}
