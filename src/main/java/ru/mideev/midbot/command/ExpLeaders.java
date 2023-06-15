package ru.mideev.midbot.command;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import ru.mideev.midbot.Main;
import ru.mideev.midbot.dao.UsersDao;
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
        eb.setTitle("Топ участников по опыту:");
        eb.setColor(Color.decode(UtilLang.DEFAULT_EMBED_COLOR));

        OptionMapping optionMapping = event.getInteraction().getOption("страница");

        int page;

        if (optionMapping != null) {
            page = optionMapping.getAsInt();
        } else {
            page = 1;
        }

        int offset = 10 * (page - 1);

        List<ru.mideev.midbot.entity.User> users = Main.DATABASE.getJdbi().withExtension(UsersDao.class, dao -> dao.getExpLeaders(offset));

        int position = 1;

        for (ru.mideev.midbot.entity.User user : users) {
            JDA jda = Main.jda;
            System.out.println(user.getSnowflake());

            User member = jda.getUserById(user.getSnowflake());
            if (member == null) {
                eb.addField("#" + (position + offset) + " - " + user.getNickname(), "Уровень: " + user.getLevel() + " | " + "Опыт: " + user.getExp(), false);
                position++;
                continue;
            }

            if (member.isBot()) {
                continue;
            }

            eb.addField("#" + (position + offset) + " - " + member.getEffectiveName(), "Уровень: " + user.getLevel() + " | " + "Опыт: " + user.getExp(), false);

            position++;
        }

        eb.setFooter("Команду запросил: " + UtilLang.userTagFormat(event.getUser()), event.getMember().getEffectiveAvatarUrl());

        event.replyEmbeds(eb.build()).queue();
    }
}

