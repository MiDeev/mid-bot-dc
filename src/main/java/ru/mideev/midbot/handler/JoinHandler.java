package ru.mideev.midbot.handler;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import ru.mideev.midbot.util.DataUtil;

import java.awt.*;
import java.util.Comparator;

public class JoinHandler extends ListenerAdapter {

    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
        String name = event.getUser().getName();
        if (name.contains("!")) {
            event.getMember().modifyNickname(name.replace("!", "")).queue();

        }

        Member member = event.getMember();

        EmbedBuilder emb = new EmbedBuilder();
        emb.setColor(new Color(128, 255, 55));
        emb.setDescription("**" + event.getUser().getAsTag() + "** (<@" + event.getMember().getUser().getId() + ">)" + " присоединился к серверу.");

        emb.addField("Дата регистрации:", "<t:" + member.getTimeCreated().toEpochSecond() + ":d> " + " [<t:" + member.getTimeCreated().toEpochSecond() + ":R>]", true);

        emb.setFooter("ID:" + member.getId());

        event.getGuild().getTextChannels().stream().filter(textChannel -> textChannel.getId().equals("942516483223846964"))
                .forEach(textChannel -> textChannel.sendMessageEmbeds(emb.build()).queue());

        //event.getMember().getGuild().addRoleToMember(event.getMember(), event.getMember().getGuild().getRolesByName("BRO", false).get(0)).queue();
    }

    @Override
    public void onGuildMemberRemove(@NotNull GuildMemberRemoveEvent event) {
        Member member = event.getMember();

        EmbedBuilder emba = new EmbedBuilder();
        emba.setColor(new Color(255, 60, 60));

        emba.addField("Приоритетная роль:", member.getRoles()
                        .stream()
                        .sorted(Comparator.comparingInt(Role::getPositionRaw).reversed())
                        .findFirst()
                        .orElse(null)
                        .getAsMention()
                , false);

        String date = DataUtil.formatDate(event.getMember());
        emba.setDescription("**" + event.getUser().getAsTag() + "** (<@" + event.getUser().getId() + ">)" + " покинул сервер.");

        emba.addField("Пробыл на сервере", date, false);

        emba.setFooter("ID: " + member.getId());

        event.getGuild().getTextChannels().stream().filter(textChannel -> textChannel.getId().equals("942516483223846964"))
                .forEach(textChannel -> textChannel.sendMessageEmbeds(emba.build()).queue());
    }
}