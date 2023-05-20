package ru.mideev.midbot.handler;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import ru.mideev.midbot.Main;
import ru.mideev.midbot.dao.UsersDao;
import ru.mideev.midbot.entity.User;
import ru.mideev.midbot.util.LevelUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LevelHandler extends ListenerAdapter {

    Map<Long, Long> cooldown = new HashMap<>();
    Random random = new Random();

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        long snowflake = event.getAuthor().getIdLong();
        long cooldown = this.cooldown.getOrDefault(snowflake, 0L);

        if (cooldown > System.currentTimeMillis())
            return;

        this.cooldown.put(snowflake, System.currentTimeMillis() + 60000);

        Main.DATABASE.getJdbi().useExtension(UsersDao.class, dao -> {
            User user = dao.findUserOrCreate(snowflake);

            if (user.getExp() >= LevelUtil.getExperience(user.getLevel() + 1)) {
                user.setLevel(user.getLevel() + 1);
            }

            user.setExp(user.getExp() + random.nextInt(3));
            dao.saveUser(user);
        });
    }
}
