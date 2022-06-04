package ru.mideev.midbot;

import ru.mideev.midbot.command.*;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.Compression;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import ru.mideev.midbot.database.Database;

import javax.security.auth.login.LoginException;

public class Main {

    public static final Database DATABASE = new Database(
            System.getenv("MYSQL_HOST"),
            Integer.parseInt(System.getenv("MYSQL_PORT")),
            System.getenv("MYSQL_USERNAME"),
            System.getenv("MYSQL_PASSWORD"),
            System.getenv("MYSQL_DATABASE")
    );

    public static void main(String[] args) throws LoginException {
        DATABASE.init();

        JDABuilder.createDefault(System.getenv("ETOKEN"))
                .addEventListeners(new News(), new Help(), new Rules(), new ServerInfo(), new JoinListener(), new NicknameListener(), new TestCommand(), new UserInfo(), new Information(), new ClearCommand(), new CommandCountCommand())
                .enableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE, CacheFlag.ACTIVITY)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .setChunkingFilter(ChunkingFilter.ALL)
                .setBulkDeleteSplittingEnabled(false)
                .setCompression(Compression.NONE)
                .enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_PRESENCES)
                .setStatus(OnlineStatus.DO_NOT_DISTURB)
                .setActivity(Activity.of(Activity.ActivityType.WATCHING,".help"))
                .build();
    }

}
