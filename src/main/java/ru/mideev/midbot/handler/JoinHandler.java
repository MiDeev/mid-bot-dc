package ru.mideev.midbot.handler;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import ru.mideev.midbot.util.DateUtil;
import ru.mideev.midbot.util.UtilLang;

import java.awt.*;
import java.time.Instant;
import java.util.Comparator;
import java.util.Objects;

public class JoinHandler extends ListenerAdapter {

    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
        Guild guild = event.getGuild();
        Member member = event.getMember();
        Role role = event.getGuild().getRoleById("941360822427467846");
        guild.addRoleToMember(member, role).queue();

        EmbedBuilder jh = new EmbedBuilder();
        jh.setColor(Color.decode(UtilLang.GREEN_EMBED_COLOR));
        jh.setDescription("**" + UtilLang.userNameFormat(event.getUser()) + "** (<@" + event.getMember().getUser().getId() + ">)" + " присоединился к серверу.");
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
        emba.setColor(Color.decode(UtilLang.RED_EMBED_COLOR));
        emba.addField("Приоритетная роль:", Objects.requireNonNull(member.getRoles()
                                .stream()
                                .sorted(Comparator.comparingInt(Role::getPositionRaw).reversed())
                                .findFirst()
                                .orElse(null))
                        .getAsMention()
                , false);

        String date = DateUtil.joinedDate(event.getMember());
        emba.setDescription("**" + UtilLang.userNameFormat(event.getUser()) + "** (<@" + event.getUser().getId() + ">)" + " покинул сервер.");
        emba.addField("Пробыл на сервере:", date, false);
        emba.addField("Теперь на сервере:", event.getGuild().getMemberCount() + " участников.", false);
        emba.setFooter("ID участника: " + member.getId());
        emba.setTimestamp(Instant.now());
        event.getGuild().getTextChannels().stream().filter(textChannel -> textChannel.getId().equals("942516483223846964"))
                .forEach(textChannel -> textChannel.sendMessageEmbeds(emba.build()).queue());
    }
}