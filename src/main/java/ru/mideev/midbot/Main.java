package ru.mideev.midbot;

import io.javalin.Javalin;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import ru.mideev.midbot.command.*;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.Compression;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import ru.mideev.midbot.command.admin.ClearCommand;
import ru.mideev.midbot.command.admin.CommandCountCommand;
import ru.mideev.midbot.command.HelpCommand;
import ru.mideev.midbot.command.util.News;
import ru.mideev.midbot.command.util.Roles;
import ru.mideev.midbot.command.util.Rules;
import ru.mideev.midbot.database.Database;
import ru.mideev.midbot.handler.*;
import ru.mideev.midbot.util.SlashCommands;

import javax.security.auth.login.LoginException;
import java.util.Optional;
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

    public static void main(String[] args) throws LoginException {
        Javalin.create().start(Integer.parseInt(Optional.ofNullable(System.getenv("PORT")).orElse("80"))).get("/", ctx -> ctx.result("Hello World"));

        DATABASE.init();

        JDA jda = JDABuilder.createDefault(System.getenv("ETOKEN"))
                .addEventListeners(new FallbackHandler(), new OfferAnswerHandler(), new SlashCommands(), new BannerCommand(), new AvatarCommand(), new News(), new HelpCommand(), new Rules(), new ServerInfo(), new JoinHandler(), new IdeaAnswerHandler.NicknameListener(), new TestCommand(), new UserInfo(), new Information(), new ClearCommand(), new CommandCountCommand(), new IdeaHandler(), new IdeaAnswerHandler(), new OfferHandler(), new Roles())
                .enableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE, CacheFlag.ACTIVITY, CacheFlag.CLIENT_STATUS)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .setChunkingFilter(ChunkingFilter.ALL)
                .setBulkDeleteSplittingEnabled(false)
                .setCompression(Compression.NONE)
                .enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_PRESENCES)
                .setStatus(OnlineStatus.DO_NOT_DISTURB)
                .setActivity(Activity.of(Activity.ActivityType.WATCHING, ".help"))
                .build();


        Guild guild1 = jda.getGuildById("941320640420532254");

        if (guild1 != null) {
            guild1.upsertCommand("ui", "Показывает информацию об участнике.").queue();
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
