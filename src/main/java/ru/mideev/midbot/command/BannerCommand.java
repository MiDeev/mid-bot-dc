package ru.mideev.midbot.command;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.utils.ImageProxy;
import org.jetbrains.annotations.NotNull;
import ru.mideev.midbot.util.UtilLang;

import java.awt.*;
import java.util.Optional;

import static ru.mideev.midbot.util.UtilLang.DEFAULT_EMBED_COLOR;

public class BannerCommand extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (event.getName().equals("banner")) {
            String net = event.getInteraction().getCommandString();
            Member member = event.getMember();
            OptionMapping optionMapping = event.getInteraction().getOption("участник");

            if (optionMapping != null && optionMapping.getAsMember() != null) {
                member = optionMapping.getAsMember();
            }

            EmbedBuilder ba = new EmbedBuilder();

            if (member != null) {
                Color color = member.getColor();

                if (color != null) {
                    ba.setColor(color);
                }
                ba.addField("Ошибка:", "Указан не верный аргумент.", true);
            }

            if (member == null) return;

            ba.setColor(Color.decode(DEFAULT_EMBED_COLOR));

            User.Profile profile = member.getUser().retrieveProfile().complete();
            Optional<ImageProxy> banner = Optional.ofNullable(profile.getBanner());

            if (profile.getBanner() == null) {
                ba.setColor(new Color(252, 80, 80));
                ba.setDescription("**У данного участника отсутствует баннер.**");
            } else {
                ba.setDescription("**Баннер пользователя: **" + "<@" + member.getId() + ">");
                banner.ifPresent(it -> ba.setImage(it.getUrl(512)));
            }

            ba.setFooter("Команду запросил: " + UtilLang.memberTagFormat(event.getMember()), event.getMember().getEffectiveAvatarUrl());

            if (event.getChannel().getId().equals("941458443749978122") && net.startsWith(UtilLang.PREFIX + "banner") || net.startsWith(UtilLang.PREFIX + "banner")) {
                event.replyEmbeds(ba.build()).queue();
            } else if (event.getInteraction().getMember().getId().equals("421259943123877888") && event.getInteraction().getCommandString().startsWith(UtilLang.PREFIX + "banner")) {
                event.replyEmbeds(ba.build()).queue();
            }
        }
    }
}
