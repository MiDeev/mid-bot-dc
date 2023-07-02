package ru.mideev.midbot.handler;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
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
        Guild guild = event.getGuild();
        Member member = event.getMember();
        Role role = event.getGuild().getRoleById("941360822427467846");
        assert role != null;
        guild.addRoleToMember(member, role).queue();

        int count = event.getGuild().getMemberCount();

        var embed = new EmbedBuilder().setColor(new Color(128, 255, 55))
                .setDescription("**%s** (<@%s>) присоединился к серверу.".formatted(UtilLang.userTagFormat(event.getUser()), event.getMember().getUser().getId()))
                .addField("Дата регистрации:", "<t:%d:d>  [<t:%d:R>]".formatted(member.getTimeCreated().toEpochSecond(), member.getTimeCreated().toEpochSecond()), true)
                .addField("Теперь на сервере:", "%d %s.".formatted(count, UtilLang.pluralsRu("участник", "участника", "участников", count)), false)
                .setFooter("ID участника: " + member.getId())
                .setTimestamp(event.getMember().getTimeJoined()).build();

        event.getGuild().getTextChannels().stream().filter(textChannel -> textChannel.getId().equals("942516483223846964"))
                .forEach(textChannel -> textChannel.sendMessageEmbeds(embed).queue());
    }

    @Override
    public void onGuildMemberRemove(@NotNull GuildMemberRemoveEvent event) {
        Member member = event.getMember();
        assert member != null;

        String date = DataUtil.joinedDate(event.getMember());

        var embed = new EmbedBuilder().setColor(new Color(255, 60, 60))
                .addField("Приоритетная роль:",
                        Objects.requireNonNull(member.getRoles().stream().max(Comparator.comparingInt(Role::getPositionRaw)).orElse(null)).getAsMention(), false)
                .setDescription("**%s** (<@%s>) покинул сервер.".formatted(UtilLang.userTagFormat(event.getUser()), event.getUser().getId()))
                .addField("Пробыл на сервере:", date, false)
                .addField("Теперь на сервере:", "%d участников.".formatted(event.getGuild().getMemberCount()), false)
                .setFooter("ID участника: " + member.getId())
                .setTimestamp(Instant.now()).build();

        event.getGuild().getTextChannels().stream().filter(textChannel -> textChannel.getId().equals("942516483223846964"))
                .forEach(textChannel -> textChannel.sendMessageEmbeds(embed).queue());
    }
}