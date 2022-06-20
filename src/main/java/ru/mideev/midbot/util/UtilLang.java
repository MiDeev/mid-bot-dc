package ru.mideev.midbot.util;

import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.ClientType;

import java.util.HashMap;
import java.util.Map;

public class UtilLang {

    public static final String PREFIX = ".";
    private static final Map<OnlineStatus, String> ONLINE_STATUS_MAPPINGS = new HashMap<>();
    private static final Map<ClientType, String> CLIENT_TYPE_MAPPINGS = new HashMap<>();

    static {
        ONLINE_STATUS_MAPPINGS.put(OnlineStatus.IDLE, "<:idle:949719753763717170> Нет на месте");
        ONLINE_STATUS_MAPPINGS.put(OnlineStatus.ONLINE, "<:online:949719753822437468> В сети");
        ONLINE_STATUS_MAPPINGS.put(OnlineStatus.OFFLINE, "<:offline:949719753390428161> Не в сети");
        ONLINE_STATUS_MAPPINGS.put(OnlineStatus.DO_NOT_DISTURB, "<:dnd:949719753784701048> Не беспокоить");
        ONLINE_STATUS_MAPPINGS.put(OnlineStatus.INVISIBLE, "<:offline:949719753390428161> Невидимка");
        ONLINE_STATUS_MAPPINGS.put(OnlineStatus.UNKNOWN, "Неизвестно");

        CLIENT_TYPE_MAPPINGS.put(ClientType.DESKTOP, "Компьютер");
        CLIENT_TYPE_MAPPINGS.put(ClientType.MOBILE, "Телефон");
        CLIENT_TYPE_MAPPINGS.put(ClientType.WEB, "Браузер");
        CLIENT_TYPE_MAPPINGS.put(ClientType.UNKNOWN, "Неизвестно");
    }

    public static String onlineStatusToString(OnlineStatus status) {
        return ONLINE_STATUS_MAPPINGS.getOrDefault(status, "Неизвестно");
    }

    public static String clientTypeToString(ClientType type) {
        return CLIENT_TYPE_MAPPINGS.getOrDefault(type, "Неизвестно");
    }
}
