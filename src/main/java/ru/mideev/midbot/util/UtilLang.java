package ru.mideev.midbot.util;

import net.dv8tion.jda.api.OnlineStatus;

import java.util.HashMap;
import java.util.Map;

public class UtilLang {

    private static final Map<OnlineStatus, String> ONLINE_STATUS_MAPPINGS = new HashMap<>();

    static {
        ONLINE_STATUS_MAPPINGS.put(OnlineStatus.IDLE, "<:idle:949719753763717170> Нет на месте");
        ONLINE_STATUS_MAPPINGS.put(OnlineStatus.ONLINE, "<:online:949719753822437468> В сети");
        ONLINE_STATUS_MAPPINGS.put(OnlineStatus.OFFLINE, "<:offline:949719753390428161> Не в сети");
        ONLINE_STATUS_MAPPINGS.put(OnlineStatus.DO_NOT_DISTURB, "<:dnd:949719753784701048> Не беспокоить");
        ONLINE_STATUS_MAPPINGS.put(OnlineStatus.INVISIBLE, "<:offline:949719753390428161> Невидимка");
        ONLINE_STATUS_MAPPINGS.put(OnlineStatus.UNKNOWN, "Неизвестно");
    }

    public static String onlineStatusToString(OnlineStatus status) {
        return ONLINE_STATUS_MAPPINGS.getOrDefault(status, "Неизвестно");
    }
}
