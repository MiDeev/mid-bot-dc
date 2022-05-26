package ru.mideev.midbot.listener;

import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateNicknameEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class NicknameListener extends ListenerAdapter {

    @Override
    public void onGuildMemberUpdateNickname(@NotNull GuildMemberUpdateNicknameEvent event) {
        Optional.ofNullable(event.getNewNickname()).ifPresent(name -> {
            if (name.startsWith("!")) {
                event.getMember().modifyNickname(name.replace("!", "")).queue();
            }
        });
    }
}

