package ru.mideev.midbot.handler;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import ru.mideev.midbot.Main;
import ru.mideev.midbot.dao.UsersDao;
import ru.mideev.midbot.entity.User;
import ru.mideev.midbot.util.LevelUtil;

import java.awt.*;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;

import static ru.mideev.midbot.util.UtilLang.DEFAULT_EMBED_COLOR;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LevelHandler extends ListenerAdapter {

    Map<Long, Long> cooldown = new HashMap<>();
    Random random = new Random();
    ScheduledExecutorService executor = Executors.newScheduledThreadPool(0, Thread.ofVirtual().factory());
    Map<Long, ScheduledFuture<?>> futuresMap = new HashMap<>();
    Map<Long, Long> timestamps = new HashMap<>();

    static final long ROLE_ID_5 = 975518433821212692L;
    static final long ROLE_ID_10 = 975837708972855326L;
    static final long ROLE_ID_15 = 975474836782587945L;
    static final long ROLE_ID_30 = 975318511549300776L;
    static final long ROLE_ID_50 = 975318500648304650L;
    static final long ROLE_ID_75 = 975318487448834088L;
    static final long ROLE_ID_100 = 975322744050831390L;

    Map<Long, BiConsumer<Member, Guild>> levelRewards = new HashMap<>() {
        {
            put(5L, (member, guild) -> addRoleToMember(member, guild, ROLE_ID_5, 5, -1));
            put(10L, (member, guild) -> addRoleToMember(member, guild, ROLE_ID_10, 10, ROLE_ID_5));
            put(15L, (member, guild) -> addRoleToMember(member, guild, ROLE_ID_15, 15, ROLE_ID_10));
            put(30L, (member, guild) -> addRoleToMember(member, guild, ROLE_ID_30, 30, ROLE_ID_15));
            put(50L, (member, guild) -> addRoleToMember(member, guild, ROLE_ID_50, 50, ROLE_ID_30));
            put(75L, (member, guild) -> addRoleToMember(member, guild, ROLE_ID_75, 75, ROLE_ID_50));
            put(100L, (member, guild) -> addRoleToMember(member, guild, ROLE_ID_100, 100, ROLE_ID_75));
        }
    };

    private void addRoleToMember(Member member, Guild guild, long roleId, int level, long prevRoleId) {
        EmbedBuilder lv = new EmbedBuilder();
        lv.setColor(Color.decode(DEFAULT_EMBED_COLOR));

        Role role = guild.getRoleById(roleId);
        String message = String.format("%s достиг %d уровня и получил роль %s", member.getAsMention(), level, role.getAsMention());
        lv.setDescription(message);

        TextChannel channel = guild.getTextChannelById("941458443749978122");

        member.getGuild().addRoleToMember(member, role).queue();
        if (prevRoleId != -1) {
            member.getGuild().removeRoleFromMember(member, guild.getRoleById(prevRoleId)).queue();
        }
        channel.sendMessageEmbeds(lv.build()).queue();
    }

    @Override
    public void onGuildVoiceUpdate(@NotNull GuildVoiceUpdateEvent event) {
        if (event.getChannelJoined() != null) {
            timestamps.put(event.getMember().getIdLong(), System.currentTimeMillis());

            futuresMap.put(event.getMember().getIdLong(), executor.scheduleAtFixedRate(() -> Main.DATABASE.getJdbi().useExtension(UsersDao.class, dao -> {
                GuildVoiceState voiceState = event.getMember().getVoiceState();
                if (voiceState == null || voiceState.isMuted()) return;
                var activeCount = event.getChannelJoined().getMembers().stream()
                        .filter(x -> x.getVoiceState() != null && !x.getVoiceState().isMuted())
                        .count();
                if (activeCount < 2)
                    return;

                User user = dao.findUserOrCreate(event.getMember().getIdLong());

                if (user.getExp() >= LevelUtil.getExperience(user.getLevel() + 1)) {
                    user.setLevel(user.getLevel() + 1);
                    if (event.getMember() != null) {
                        levelRewards.getOrDefault(user.getLevel(), (__, ___) -> {
                        }).accept(event.getMember(), event.getGuild());
                    }
                }

                var xp = 3 + 0.5 * activeCount + (voiceState.isStream() ? (0.5 + 0.25 * activeCount) : 0);

                user.setExp((long) (user.getExp() + xp));
                user.setNickname(event.getMember().getNickname());
                dao.saveUser(user);
            }), 0, 30, TimeUnit.SECONDS));
        }

        if (event.getChannelLeft() != null) {
            long cur = System.currentTimeMillis();
            long ms = cur - Optional.ofNullable(timestamps.remove(event.getMember().getIdLong())).orElse(cur);
            Main.DATABASE.getJdbi().useExtension(UsersDao.class, dao -> {
                var user = dao.findUserOrCreate(event.getMember().getIdLong());
                user.setVoice(user.getVoice() + ms);
                dao.saveUser(user);
            });
            Optional.ofNullable(futuresMap.remove(event.getMember().getIdLong())).ifPresent(x -> x.cancel(false));
        }
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;
        long snowflake = event.getAuthor().getIdLong();
        long cooldown = this.cooldown.getOrDefault(snowflake, 0L);

        if (cooldown > System.currentTimeMillis())
            return;

        this.cooldown.put(snowflake, System.currentTimeMillis() + 30000);

        Main.DATABASE.getJdbi().useExtension(UsersDao.class, dao -> {
            User user = dao.findUserOrCreate(snowflake);

            if (user.getExp() >= LevelUtil.getExperience(user.getLevel() + 1)) {
                user.setLevel(user.getLevel() + 1);
                if (event.getMember() != null) {
                    levelRewards.getOrDefault(user.getLevel(), (__, ___) -> {
                    }).accept(event.getMember(), event.getGuild());
                }
            }

            user.setExp(user.getExp() + random.nextInt(3));
            user.setNickname(event.getAuthor().getName());
            dao.saveUser(user);
        });
    }
}
