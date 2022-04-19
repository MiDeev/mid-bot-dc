package ru.mideev.midbot;

import ru.mideev.midbot.commands.*;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.Compression;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import net.dv8tion.jda.internal.JDAImpl;

import javax.security.auth.login.LoginException;

public class Main {
    public static void main(String[] args) throws LoginException {
        JDAImpl jda = (JDAImpl) JDABuilder.createDefault(System.getenv("ETOKEN"))
                .addEventListeners(new News(), new Help(), new Rules(), new ServerInfo(), new JoinListener(), new NicknameListener(), new TestCommand(), new UserInfo())
                .enableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE, CacheFlag.ACTIVITY)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .setChunkingFilter(ChunkingFilter.ALL)
                .setBulkDeleteSplittingEnabled(false)
                .setCompression(Compression.NONE)
                .enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_PRESENCES)
                .setStatus(OnlineStatus.DO_NOT_DISTURB)
                .setActivity(Activity.of(Activity.ActivityType.WATCHING,"/help"))
                .build();
    }

}
