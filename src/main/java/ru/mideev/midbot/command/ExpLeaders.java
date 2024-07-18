package ru.mideev.midbot.command;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import ru.mideev.midbot.Main;
import ru.mideev.midbot.dao.UsersDao;
import ru.mideev.midbot.util.DateUtil;
import ru.mideev.midbot.util.UtilLang;

import java.awt.*;
import java.util.List;
import java.util.Locale;

public class ExpLeaders extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getInteraction().getCommandString().toLowerCase(Locale.ROOT).startsWith(UtilLang.PREFIX + "xpleaders") || !event.getChannel().getId().equals("941458443749978122"))
            return;

        EmbedBuilder eb = new EmbedBuilder();

        OptionMapping optionMapping = event.getInteraction().getOption("страница");

        int page;

        int totalUsers = Main.DATABASE.getJdbi().withExtension(UsersDao.class, UsersDao::getTotalUsers);
        int pageSize = 10;
        int totalPages = totalUsers % pageSize == 0 ? totalUsers / pageSize : totalUsers / pageSize + 1;

        if (optionMapping != null) {
            page = Math.max(1, Math.min(optionMapping.getAsInt(), totalPages));
            eb.setAuthor("Страница " + page + " из " + totalPages + " | ");
        } else {
            page = 1;
            eb.setAuthor("Страница 1 из " + totalPages);
        }
        eb.setTitle("Топ участников по опыту:");
        eb.setColor(Color.decode(UtilLang.DEFAULT_EMBED_COLOR));

        int offset = 10 * (page - 1);

        List<ru.mideev.midbot.entity.User> users = Main.DATABASE.getJdbi().withExtension(UsersDao.class, dao -> dao.getExpLeaders(offset));

        int position = 1;

        for (ru.mideev.midbot.entity.User user : users) {
            JDA jda = Main.jda;

            long voiceTime = user.getVoice();
            if (voiceTime > 0) {
                String output = " | " + "**Голосовой:** " + DateUtil.msToDate(voiceTime);
            }

            User member = jda.getUserById(user.getSnowflake());
            if (member == null) {
                String fieldTitle = "#" + (position + offset) + " - " + user.getNickname();
                String fieldContent = "Уровень: " + user.getLevel() + " | " + "Опыт: " + user.getExp();

                if (voiceTime > 0) {
                    fieldContent += " | " + "Голосовой: " + DateUtil.msToDate(user.getVoice());
                }

                eb.addField(fieldTitle, fieldContent, false);
                position++;
                continue;
            }

            if (member.isBot()) {
                continue;
            }

            String fieldTitle = "#" + (position + offset) + " - " + member.getEffectiveName();
            String fieldContent = "Уровень: " + user.getLevel() + " | " + "Опыт: " + user.getExp();

            if (voiceTime > 0) {
                fieldContent += " | " + "Голосовой: " + DateUtil.msToDate(user.getVoice());
            }

            eb.addField(fieldTitle, fieldContent, false);
            position++;
        }

        eb.setFooter("Всего участников с опытом: " + totalUsers);

        event.replyEmbeds(eb.build()).queue();
    }
}

