package commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Objects;

public class Rules extends ListenerAdapter {
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        if (event.getChannel().getId().equals("942520425936719952") && event.getMessage().getContentDisplay().contains("/rules")) {

            EmbedBuilder emba = new EmbedBuilder();
            emba.setColor(new Color(255, 79, 114));
            //emba.setDescription("Здарова, " + "<@" + event.getMessage().getAuthor().getId() + ">");
            emba.setImage(event.getGuild().getIconUrl());

            Objects.requireNonNull(event.getMessage().getCategory()).getTextChannels().get(2).sendMessageEmbeds(emba.build()).queue();
        }

        if(event.getMessage().getContentDisplay().contains("https://discord.gg")) {
            event.getMessage().delete().queue();
        }
    }
}
