package ru.mideev.midbot.command;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.jetbrains.annotations.NotNull;
import ru.mideev.midbot.util.DataUtil;
import ru.mideev.midbot.util.UtilLang;

import java.awt.*;
import java.util.Locale;

import static ru.mideev.midbot.util.UtilLang.DEFAULT_EMBED_COLOR;

public class TimeCommand extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (event.getName().equals("time")) {
            String com = event.getInteraction().getCommandString();
            Member member = event.getMember();
            OptionMapping optionMapping = event.getInteraction().getOption("участник");

            if (optionMapping != null && optionMapping.getAsMember() != null) {
                member = optionMapping.getAsMember();
            }

            EmbedBuilder eb = new EmbedBuilder();

            if (member != null) {
                Color color = member.getColor();

                if (color != null) {
                    eb.setColor(color);
                }
                eb.addField("Ошибка:", "Указан не верный аргумент.", true);
            }

            if (member == null) return;

            String JDate = DataUtil.joinedDate(member);
            String CDate = DataUtil.createdDate(member);

            EmbedBuilder ti = new EmbedBuilder();

            ti.setColor(Color.decode(DEFAULT_EMBED_COLOR));
            ti.setAuthor("Информация об участнике: " + UtilLang.memberTagFormat(member), null, member.getEffectiveAvatarUrl());
            ti.addField("Возраст аккаунта:", CDate, true);
            ti.addField("Время нахождения на сервере:", JDate, false);
            ti.setFooter("Команду запросил: " + UtilLang.memberTagFormat(event.getMember()), event.getMember().getEffectiveAvatarUrl());

            if (event.getChannel().getId().equals("941458443749978122") && com.toLowerCase(Locale.ROOT).startsWith(UtilLang.PREFIX + "time")) {
                event.replyEmbeds(ti.build()).queue();
            } else if (event.getInteraction().getCommandString().toLowerCase(Locale.ROOT).startsWith(UtilLang.PREFIX + "time")) {
                event.replyEmbeds(ti.build()).queue();
            }
        }
    }
}
