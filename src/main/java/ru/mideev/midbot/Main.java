package ru.mideev.midbot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.Compression;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.reflections.Reflections;
import ru.mideev.midbot.command.*;
import ru.mideev.midbot.command.admin.*;
import ru.mideev.midbot.command.admin.other.*;
import ru.mideev.midbot.database.Database;
import ru.mideev.midbot.handler.*;
import ru.mideev.midbot.util.BadgesUtil;

import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
    public static final Database DATABASE = new Database(
            System.getenv("MYSQL_HOST"),
            Integer.parseInt(System.getenv("MYSQL_PORT")),
            System.getenv("MYSQL_USERNAME"),
            System.getenv("MYSQL_PASSWORD"),
            System.getenv("MYSQL_DATABASE")
    );

    private static final ScheduledExecutorService SCHEDULED_EXECUTOR_SERVICE = Executors.newSingleThreadScheduledExecutor();

    public static JDA jda;

    public static void main(String[] args) {
        DATABASE.init();
        jda = JDABuilder.createDefault(System.getenv("TOKEN"))
                .enableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE, CacheFlag.ACTIVITY, CacheFlag.CLIENT_STATUS)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .setChunkingFilter(ChunkingFilter.ALL)
                .setBulkDeleteSplittingEnabled(false)
                .setCompression(Compression.NONE)
                .enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_PRESENCES, GatewayIntent.MESSAGE_CONTENT)
                .setStatus(OnlineStatus.DO_NOT_DISTURB)
                .setActivity(Activity.of(Activity.ActivityType.WATCHING, "/help"))
                .build();

        Reflections reflections = new Reflections("ru.mideev.midbot");

        Set<Class<? extends ListenerAdapter>> classes = reflections.getSubTypesOf(ListenerAdapter.class);

        for (Class<? extends ListenerAdapter> clazz : classes) {
            try {
                ListenerAdapter listener = clazz.getDeclaredConstructor().newInstance();
                jda.addEventListener(listener);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        SCHEDULED_EXECUTOR_SERVICE.schedule(() -> {
            Guild guild = jda.getGuildById("941320640420532254");
            guild.retrieveInvites().complete().forEach(x -> {
                Member member = guild.getMember(x.getInviter());

                if (x.getUses() >= 2) {
                    guild.addRoleToMember(member, guild.getRoleById("984478425416888440")).submit();
                }
            });
        }, 1, TimeUnit.MINUTES);
    }
}
