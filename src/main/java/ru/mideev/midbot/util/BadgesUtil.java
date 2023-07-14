package ru.mideev.midbot.util;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.utils.ImageProxy;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class BadgesUtil extends ListenerAdapter {

    public static final String STAFF = "<:discord_staff_badge:1128227563790143559>";
    public static final String PARTNER = "<:discord_partner_badge:1128227561315504219>";
    public static final String HYPESQUAD = "<:hypesquad_events_badge:1128227628936089700>";
    public static final String BUG_HUNTER_LEVEL_1 = "<:discord_bug_hunter_badge:1128227558438228079>";
    public static final String HYPESQUAD_BRAVERY = "<:hypesquad_bravery_badge:1128227571541225494>";
    public static final String HYPESQUAD_BRILLIANCE = "<:hypesquad_brilliance_badge:1128227574477242418>";
    public static final String HYPESQUAD_BALANCE = "<:hypesquad_balance_badge:1128227570522013726>";
    public static final String EARLY_SUPPORTER = "<:early_supporter:1128227565241385011>";
    public static final String BUG_HUNTER_LEVEL_2 = "<:golden_discord_bug_hunter_badge:1128227569062379620>";
    public static final String VERIFIED_BOT = "<:verified_bot:1117169241536344175>";
    public static final String VERIFIED_DEVELOPER = "<:verified_bot_developer_badge:1128227566717779978>";
    public static final String CERTIFIED_MODERATOR = "<:moderator_programms_alumni_badge:1128227578994511883>";
    public static final String BOT_HTTP_INTERACTIONS = "<:support_commands_badge:1117076944589164645>";
    public static final String ACTIVE_DEVELOPER = "<:active_developer_badge:1128227552536825917>";
    public static final String UPDATED_NICKNAME = "<:nickname_badge:1128227556869537802>";
    public static final String BOOSTER_SUBSCRIBER = "<:nitro_subscriber_badge:1128227581909536818>";
    public static final String NITRO_BOOSTER = "<:server_boosting_badge:1128227667867607042>";

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

        User.Profile profile = member.getUser().retrieveProfile().complete();
        Optional<ImageProxy> banner = Optional.ofNullable(profile.getBanner());

        if (banner.isPresent()) {
            response.append(BOOSTER_SUBSCRIBER);
        }
        if (member.isBoosting()) {
            response.append(NITRO_BOOSTER);
        }
        if (member.getUser().getAsTag().contains("0000")) {
            response.append(UPDATED_NICKNAME);
        }
        return response.toString();
    }

}


