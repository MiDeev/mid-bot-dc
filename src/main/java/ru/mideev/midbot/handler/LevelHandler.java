package ru.mideev.midbot.handler;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import ru.mideev.midbot.Main;
import ru.mideev.midbot.dao.UsersDao;
import ru.mideev.midbot.entity.User;
import ru.mideev.midbot.util.LevelUtil;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Consumer;

import static ru.mideev.midbot.util.UtilLang.EMBED_COLOR;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LevelHandler extends ListenerAdapter {

    Map<Long, Long> cooldown = new HashMap<>();
    Random random = new Random();

    static final long ROLE_ID_5 = 975518433821212692L;
    static final long ROLE_ID_10 = 975837708972855326L;
    static final long ROLE_ID_15 = 975474836782587945L;
    static final long ROLE_ID_30 = 975318511549300776L;
    static final long ROLE_ID_50 = 975318500648304650L;
    static final long ROLE_ID_75 = 975318487448834088L;
    static final long ROLE_ID_100 = 975322744050831390L;

    Map<Long, Consumer<MessageReceivedEvent>> levelRewards = new HashMap<>() {
        {
            put(5L, event -> addRoleToMember(event, ROLE_ID_5, 5));
            put(10L, event -> addRoleToMember(event, ROLE_ID_10, 10));
            put(15L, event -> addRoleToMember(event, ROLE_ID_15, 15));
            put(30L, event -> addRoleToMember(event, ROLE_ID_30, 30));
            put(50L, event -> addRoleToMember(event, ROLE_ID_50, 50));
            put(75L, event -> addRoleToMember(event, ROLE_ID_75, 75));
            put(100L, event -> addRoleToMember(event, ROLE_ID_100, 100));
        }
    };

    private void addRoleToMember(MessageReceivedEvent event, long roleId, int level) {
        EmbedBuilder lv = new EmbedBuilder();
        lv.setColor(Color.decode(EMBED_COLOR));

        Role role = event.getGuild().getRoleById(roleId);
        Member member = event.getMember();
        String message = String.format("%s достиг %d уровня и получил роль %s", member.getAsMention(), level, role.getAsMention());
        lv.setDescription(message);

        TextChannel channel = event.getGuild().getTextChannelById("941458443749978122");

        member.getGuild().addRoleToMember(member, role).queue();
        channel.sendMessageEmbeds(lv.build()).queue();
    }


    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;
        long snowflake = event.getAuthor().getIdLong();
        long cooldown = this.cooldown.getOrDefault(snowflake, 0L);

        if (cooldown > System.currentTimeMillis())
            return;

        this.cooldown.put(snowflake, System.currentTimeMillis() + 600);

        Main.DATABASE.getJdbi().useExtension(UsersDao.class, dao -> {
            User user = dao.findUserOrCreate(snowflake);

            if (user.getExp() >= LevelUtil.getExperience(user.getLevel() + 1)) {
                user.setLevel(user.getLevel() + 1);
                if (event.getMember() != null) {
                    levelRewards.getOrDefault(user.getLevel(), event1 -> {
                    }).accept(event);
                }
            }

            user.setExp(user.getExp() + random.nextInt(3));
            user.setNickname(event.getAuthor().getName());
            dao.saveUser(user);
        });
    }
}
