package ru.mideev.midbot.command;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.jetbrains.annotations.NotNull;
import ru.mideev.midbot.util.UtilLang;

import java.awt.*;

import static ru.mideev.midbot.util.UtilLang.EMBED_COLOR;

public class AvatarCommand extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (event.getName().equals("ava")) {
            String net = event.getInteraction().getCommandString();
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

            EmbedBuilder av = new EmbedBuilder();

            av.setColor(Color.decode(EMBED_COLOR));
            av.setDescription("**Аватар пользователя: **" + "<@" + member.getId() + ">");

            if (member.getUser().getAvatar() == null) {
                av.setImage(member.getUser().getEffectiveAvatarUrl());
            } else av.setImage(member.getUser().getAvatar().getUrl(512));

            av.setFooter("Команду запросил: " + UtilLang.memberTagFormat(event.getMember()), event.getMember().getEffectiveAvatarUrl());

            if (event.getChannel().getId().equals("941458443749978122") && net.startsWith(UtilLang.PREFIX + "ava") || net.startsWith(UtilLang.PREFIX + "avatar")) {
                event.replyEmbeds(av.build()).queue();
            } else if (event.getInteraction().getMember().getId().equals("421259943123877888") && event.getInteraction().getCommandString().startsWith(UtilLang.PREFIX + "ava")) {
                event.replyEmbeds(av.build()).queue();
            }
        }
    }
}
