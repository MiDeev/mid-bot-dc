package ru.mideev.midbot.handler;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import ru.mideev.midbot.util.DataUtil;
import ru.mideev.midbot.util.UtilLang;

import java.awt.*;
import java.time.Instant;
import java.util.Comparator;
import java.util.Objects;

public class JoinHandler extends ListenerAdapter {

    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
        String name = event.getUser().getName();
        if (name.contains("!")) {
            event.getMember().modifyNickname(name.replace("!", "")).queue();
        }

        Member member = event.getMember();

        EmbedBuilder jh = new EmbedBuilder();
        jh.setColor(new Color(128, 255, 55));
        jh.setDescription("**" + UtilLang.userTagFormat(event.getUser()) + "** (<@" + event.getMember().getUser().getId() + ">)" + " присоединился к серверу.");
        jh.addField("Дата регистрации:", "<t:" + member.getTimeCreated().toEpochSecond() + ":d> " + " [<t:" + member.getTimeCreated().toEpochSecond() + ":R>]", true);

        int count = event.getGuild().getMemberCount();

        jh.addField("Теперь на сервере:", count + " " + UtilLang.pluralsRu("участник", "участника", "участников", count) + ".", false);
        jh.setFooter("ID участника: " + member.getId());
        jh.setTimestamp(event.getMember().getTimeJoined());
        event.getGuild().getTextChannels().stream().filter(textChannel -> textChannel.getId().equals("942516483223846964"))
                .forEach(textChannel -> textChannel.sendMessageEmbeds(jh.build()).queue());
    }

    @Override
    public void onGuildMemberRemove(@NotNull GuildMemberRemoveEvent event) {
        Member member = event.getMember();

        EmbedBuilder emba = new EmbedBuilder();
        emba.setColor(new Color(255, 60, 60));
        emba.addField("Приоритетная роль:", Objects.requireNonNull(member.getRoles()
                                .stream()
                                .sorted(Comparator.comparingInt(Role::getPositionRaw).reversed())
                                .findFirst()
                                .orElse(null))
                        .getAsMention()
                , false);

        String date = DataUtil.joinedDate(event.getMember());
        emba.setDescription("**" + UtilLang.userTagFormat(event.getUser()) + "** (<@" + event.getUser().getId() + ">)" + " покинул сервер.");
        emba.addField("Пробыл на сервере:", date, false);
        emba.addField("Теперь на сервере:", event.getGuild().getMemberCount() + " участников.", false);
        emba.setFooter("ID участника: " + member.getId());
        emba.setTimestamp(Instant.now());
        event.getGuild().getTextChannels().stream().filter(textChannel -> textChannel.getId().equals("942516483223846964"))
                .forEach(textChannel -> textChannel.sendMessageEmbeds(emba.build()).queue());
    }
}