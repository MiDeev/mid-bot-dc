package ru.mideev.midbot.util;

import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.ClientType;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;

import java.util.HashMap;
import java.util.Map;

public class UtilLang {
    public static final String PREFIX = "/";
    public static final String DEFAULT_EMBED_COLOR = "0x5865F2";
    private static final Map<OnlineStatus, String> ONLINE_STATUS_MAPPINGS = new HashMap<>();
    private static final Map<ClientType, String> CLIENT_TYPE_MAPPINGS = new HashMap<>();

    static {
        ONLINE_STATUS_MAPPINGS.put(OnlineStatus.IDLE, "<:idle:949719753763717170> Нет на месте");
        ONLINE_STATUS_MAPPINGS.put(OnlineStatus.ONLINE, "<:online:949719753822437468> В сети");
        ONLINE_STATUS_MAPPINGS.put(OnlineStatus.OFFLINE, "<:offline:949719753390428161> Не в сети");
        ONLINE_STATUS_MAPPINGS.put(OnlineStatus.DO_NOT_DISTURB, "<:dnd:949719753784701048> Не беспокоить");
        ONLINE_STATUS_MAPPINGS.put(OnlineStatus.INVISIBLE, "<:offline:949719753390428161> Невидимка");
        ONLINE_STATUS_MAPPINGS.put(OnlineStatus.UNKNOWN, "Неизвестно");

        CLIENT_TYPE_MAPPINGS.put(ClientType.DESKTOP, "<:online_from_computer:1122495132437057556> Компьютер");
        CLIENT_TYPE_MAPPINGS.put(ClientType.MOBILE, "<:online_from_mobile:1122495133758267462> Телефон");
        CLIENT_TYPE_MAPPINGS.put(ClientType.WEB, "<:online_from_browser:1122496680005222440> Браузер");
        CLIENT_TYPE_MAPPINGS.put(ClientType.UNKNOWN, "<:online_from_unknown_device:1122495136056737822> Неизвестно");
    }

    public static String onlineStatusToString(OnlineStatus status) {
        return ONLINE_STATUS_MAPPINGS.getOrDefault(status, "<:online_from_unknown_device:1122495136056737822> Неизвестно");
    }

    public static String clientTypeToString(ClientType type) {
        return CLIENT_TYPE_MAPPINGS.getOrDefault(type, "<:online_from_unknown_device:1122495136056737822> Неизвестно");
    }

    public static String pluralsRu(String one, String two, String five, int n) {
        if (n < 0) {
            n = -n;
        }
        return n % 10 == 1 && n % 100 != 11 ? one : (n % 10 >= 2 && n % 10 <= 4 && (n % 100 < 10 || n % 100 >= 20) ? two : five);
    }

//    public static String memberTagFormat(User user, String nickname) {
//        if (nickname == null) {
//            return user.getAsTag().contains("#0000") ? user.getName() : user.getAsTag();
//        } else {
//            return user.getName() + " \n(" + nickname + ")";
//        }
//    }

    public static String userTagFormat(User user) {
            return user.getAsTag().contains("#0000") ? user.getName() : user.getAsTag();
    }

    public static String memberTagFormat(Member member) {
        return member.getUser().getAsTag().contains("#0000") ? member.getUser().getName() : member.getUser().getAsTag();
    }

    public static String ownerTagFormat(Member member) {
        return member.getGuild().getOwner().getUser().getAsTag().contains("#0000") ? member.getGuild().getOwner().getUser().getName() : member.getGuild().getOwner().getUser().getAsTag();
    }
}

