package ru.mideev.midbot.command;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import ru.mideev.midbot.Main;
import ru.mideev.midbot.dao.UsersDao;
import ru.mideev.midbot.entity.User;
import ru.mideev.midbot.util.DateUtil;
import ru.mideev.midbot.util.LevelUtil;
import ru.mideev.midbot.util.UtilLang;

import java.awt.*;
import java.util.Locale;

public class LevelCommand extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getInteraction().getCommandString().toLowerCase(Locale.ROOT).startsWith(UtilLang.PREFIX + "level") || !event.getChannel().getId().equals("941458443749978122"))
            return;

        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(Color.decode(UtilLang.DEFAULT_EMBED_COLOR));

        OptionMapping optionMapping = event.getInteraction().getOption("участник");

        Member member;


        if (optionMapping != null) {
            member = optionMapping.getAsMember();
        } else {
            member = event.getMember();
        }

        User user = Main.DATABASE.getJdbi().withExtension(UsersDao.class, dao -> dao.findUserOrCreate(member.getIdLong()));

        eb.setAuthor("Статистика участника: " + UtilLang.memberTagFormat(member), null,  member.getEffectiveAvatarUrl());
        eb.setDescription("**Уровень:** " + user.getLevel() + " **|** " + "**Опыт:** " + user.getExp() + " / " + LevelUtil.getExperience(user.getLevel() + 1) + " | " + "**Голосовой:** " + DateUtil.msToDate(user.getVoice()));
        eb.setFooter("Команду запросил: " + event.getUser().getName());

        event.replyEmbeds(eb.build()).queue();
    }
}
