package ru.mideev.midbot.command.admin;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Locale;

public class RepeatMessage extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Guild guild = event.getGuild();
        TextChannel textChannel = guild.getTextChannelById("941334996654911488");

        if (event.getChannel().getId().equals("942520425936719952")) {
           textChannel.sendMessage(event.getMessage().getContentRaw()).queue();

        }
    }
}
