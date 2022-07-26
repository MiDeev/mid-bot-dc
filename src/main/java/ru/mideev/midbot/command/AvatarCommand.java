package ru.mideev.midbot.command;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import ru.mideev.midbot.Main;
import ru.mideev.midbot.util.UtilLang;

import java.awt.*;
import java.util.List;
import java.util.Locale;

public class AvatarCommand extends ListenerAdapter {
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        if (!event.getMessage().getContentDisplay().toLowerCase(Locale.ROOT).startsWith(UtilLang.PREFIX + "ava")) return;

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

        EmbedBuilder av = new EmbedBuilder();
        av.setColor(new Color(141, 127, 254));

        av.setDescription("**Аватар пользователя: **" + "<@" + member.getId() + ">");

        if (member.getUser().getAvatar() == null) {
            av.setImage(member.getUser().getEffectiveAvatarUrl());
        } else av.setImage(member.getUser().getAvatar().getUrl(512));

        av.setFooter("Команду запросил: " + event.getMember().getUser().getAsTag(), event.getMember().getEffectiveAvatarUrl());

        if (event.getChannel().getId().equals("941458443749978122") && net.startsWith(UtilLang.PREFIX + "ava") || net.startsWith(UtilLang.PREFIX + "avatar")) {
            event.getMessage().getChannel().asTextChannel().sendMessageEmbeds(av.build()).queue();
        } else if (event.getMessage().getAuthor().getId().equals("421259943123877888") && event.getMessage().getContentDisplay().startsWith(UtilLang.PREFIX + "ava")) {
            event.getMessage().getChannel().asTextChannel().sendMessageEmbeds(av.build()).queue();
        }

        Main.DATABASE.insertCommandUsage(
                event.getMember().getIdLong(),
                event.getMessage().getContentDisplay()
        );

    }
}
