package ru.mideev.midbot.handler;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import ru.mideev.midbot.Main;
import ru.mideev.midbot.dao.UsersDao;
import ru.mideev.midbot.entity.User;
import ru.mideev.midbot.util.LevelUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Consumer;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LevelHandler extends ListenerAdapter {

    Map<Long, Long> cooldown = new HashMap<>();
    Random random = new Random();
    Map<Long, Consumer<MessageReceivedEvent>> levelRewards = new HashMap<>() {
        {
            put(5L, event -> {
                long userId = 5L;
                Role role = event.getGuild().getRoleById("975518433821212692");
                Member member = event.getGuild().getMemberById(userId);

                member.getRoles().add(role);

                String message = String.format("%s достиг 5 уровня!", member.getAsMention());
                event.getChannel().sendMessage(message).queue();
            });
        }
    };

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;
        long snowflake = event.getAuthor().getIdLong();
        long cooldown = this.cooldown.getOrDefault(snowflake, 0L);

        if (cooldown > System.currentTimeMillis())
            return;

        this.cooldown.put(snowflake, System.currentTimeMillis() + 60000);

        Main.DATABASE.getJdbi().useExtension(UsersDao.class, dao -> {
            User user = dao.findUserOrCreate(snowflake);

            if (user.getExp() >= LevelUtil.getExperience(user.getLevel() + 1)) {
                user.setLevel(user.getLevel() + 1);
                if (event.getMember() != null) {
                    levelRewards.getOrDefault(user.getLevel(), event1 -> {}).accept(event);
                }
            }

            user.setExp(user.getExp() + random.nextInt(3));
            user.setNickname(event.getAuthor().getName());
            dao.saveUser(user);
        });
    }
}
