package ru.mideev.midbot.util;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public class BadgesUtil extends ListenerAdapter {

    public static final String STAFF = "<:discord_staff_badge:1117076815513649342>";
    public static final String PARTNER = "<:discord_partner_badge:1117076551792599131>";
    public static final String HYPESQUAD = "<:hypesquad_events_badge:1117076987035521024>";
    public static final String BUG_HUNTER_LEVEL_1 = "<:discord_bug_hunter_badge:1117076550056157225>";
    public static final String HYPESQUAD_BRAVERY = "<:hypesquad_bravery_badge:1117076563746365500>";
    public static final String HYPESQUAD_BRILLIANCE = "<:hypesquad_brilliance_badge:1117076565457649725>";
    public static final String HYPESQUAD_BALANCE = "<:hypesquad_balance_badge:1117076774023606292>";
    public static final String EARLY_SUPPORTER = "<:early_supporter:1117076555244503040>";
    public static final String BUG_HUNTER_LEVEL_2 = "<:golden_discord_bug_hunter_badge:1117076852364824576>";
    public static final String VERIFIED_BOT = "<:verified_bot:1117169241536344175>";
    public static final String VERIFIED_DEVELOPER = "<:early_verified_developer_badge:1117076557836582952>";
    public static final String CERTIFIED_MODERATOR = "<:moderator_programms_alumni_badge:1117076569047957545>";
    public static final String BOT_HTTP_INTERACTIONS = "<:support_commands_badge:1117076944589164645>";
    public static final String ACTIVE_DEVELOPER = "<:active_developer_badge:1117076543794069564>";
    public static final String UPDATED_NICKNAME = "<:changed_nickname_badge:1117076918181838913>";

    public static String getUserBadges(Member member) {
        StringBuilder response = new StringBuilder();
        EnumSet<User.UserFlag> badges = member.getUser().getFlags();

        if (!badges.isEmpty()) {
            Map<User.UserFlag, String> badgeMap = new HashMap<>();
            badgeMap.put(User.UserFlag.HYPESQUAD_BRILLIANCE, HYPESQUAD_BRILLIANCE);
            badgeMap.put(User.UserFlag.ACTIVE_DEVELOPER, ACTIVE_DEVELOPER);
            badgeMap.put(User.UserFlag.STAFF, STAFF);
            badgeMap.put(User.UserFlag.EARLY_SUPPORTER, EARLY_SUPPORTER);
            badgeMap.put(User.UserFlag.PARTNER, PARTNER);
            badgeMap.put(User.UserFlag.HYPESQUAD, HYPESQUAD);
            badgeMap.put(User.UserFlag.BUG_HUNTER_LEVEL_1, BUG_HUNTER_LEVEL_1);
            badgeMap.put(User.UserFlag.HYPESQUAD_BRAVERY, HYPESQUAD_BRAVERY);
            badgeMap.put(User.UserFlag.HYPESQUAD_BALANCE, HYPESQUAD_BALANCE);
            badgeMap.put(User.UserFlag.BUG_HUNTER_LEVEL_2, BUG_HUNTER_LEVEL_2);
            badgeMap.put(User.UserFlag.VERIFIED_BOT, VERIFIED_BOT);
            badgeMap.put(User.UserFlag.VERIFIED_DEVELOPER, VERIFIED_DEVELOPER);
            badgeMap.put(User.UserFlag.CERTIFIED_MODERATOR, CERTIFIED_MODERATOR);
            badgeMap.put(User.UserFlag.BOT_HTTP_INTERACTIONS, BOT_HTTP_INTERACTIONS);

            for (User.UserFlag badge : badges) {
                String emoji = badgeMap.get(badge);
                if (emoji != null) {
                    response.append(emoji).append(" ");
                }
            }

        }
        if (member.getUser().getAsTag().contains("0000")) {
            response.append(UPDATED_NICKNAME);
        }
        return response.toString();
    }

}


