package ru.mideev.midbot.command;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.utils.ImageProxy;
import org.jetbrains.annotations.NotNull;
import ru.mideev.midbot.Main;
import ru.mideev.midbot.util.UtilLang;

import java.awt.*;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class BannerCommand extends ListenerAdapter {
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        if (!event.getMessage().getContentDisplay().toLowerCase(Locale.ROOT).startsWith(UtilLang.PREFIX + "banner")) return;

        String net = event.getMessage().getContentDisplay();

        String[] args = net.split(" ");

        List<Member> list = event.getMessage().getMentions().getMembers();

        Member member = event.getMember();

        EmbedBuilder eb = new EmbedBuilder();

        if (member != null) {
            Color color = member.getColor();

            if (color != null) {
                eb.setColor(color);
            }

            eb.addField("Ошибка:", "Указан не верный аргумент.", true);
        }

        try {
            if (!list.isEmpty()) {
                member = list.get(0);
            } else if (args.length == 2) {
                member = event.getGuild().getMemberById(args[1]);
            }
        } catch (Throwable throwable) {
            event.getMessage().getChannel().asTextChannel().sendMessageEmbeds(eb.build()).queue();
            throw throwable;
        }

        if (member == null) return;

        EmbedBuilder ba = new EmbedBuilder();
        ba.setColor(new Color(141, 127, 254));

        User.Profile profile = member.getUser().retrieveProfile().complete();
        Optional<ImageProxy> banner = Optional.ofNullable(profile.getBanner());

        if (profile.getBanner() == null) {
            ba.setColor(new Color(252, 80, 80));
            ba.setDescription("**У данного участника отсутствует баннер.**");
            ba.setFooter("Команду запросил: " + event.getMember().getUser().getAsTag(), event.getMember().getEffectiveAvatarUrl());
        } else {
            ba.setDescription("**Баннер пользователя: **" + "<@" + member.getId() + ">");
            banner.ifPresent(it -> ba.setImage(it.getUrl(512)));
            ba.setFooter("Команду запросил: " + event.getMember().getUser().getAsTag(), event.getMember().getEffectiveAvatarUrl());
        }

        if (event.getChannel().getId().equals("941458443749978122") && net.startsWith(UtilLang.PREFIX + "banner") || net.startsWith(UtilLang.PREFIX + "banner")) {
            event.getMessage().getChannel().asTextChannel().sendMessageEmbeds(ba.build()).queue();
        } else if (event.getMessage().getAuthor().getId().equals("421259943123877888") && event.getMessage().getContentDisplay().startsWith(UtilLang.PREFIX + "banner")) {
            event.getMessage().getChannel().asTextChannel().sendMessageEmbeds(ba.build()).queue();
        }

        Main.DATABASE.insertCommandUsage(
                event.getMember().getIdLong(),
                event.getMessage().getContentDisplay()
        );

    }
}
