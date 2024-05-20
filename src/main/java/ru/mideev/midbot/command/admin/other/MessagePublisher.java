package ru.mideev.midbot.command.admin.other;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.Arrays;

public class MessagePublisher extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getGuild().getId().equals("975854031329185802")) {
            EmbedBuilder eb = new EmbedBuilder();

            String[] lines = event.getMessage().getContentRaw().split("\n");
            if (lines.length >= 2) {
                String title = lines[0];
                String description = String.join("\n", Arrays.copyOfRange(lines, 1, lines.length));

                if (event.getChannel().getId().equals("1150155460331196607")) {
                    eb.setColor(Color.decode("0xffe340"));
                    eb.setTitle(title);
                    eb.setDescription(description);
                    event.getChannel().sendMessageEmbeds(eb.build()).queue();
                }

                if (event.getChannel().getId().equals("1150155345906368642")) {
                    eb.setColor(Color.decode("0x5064ff"));
                    eb.setTitle(title);
                    eb.setDescription(description);
                    event.getChannel().sendMessageEmbeds(eb.build()).queue();
                }

                if (event.getChannel().getId().equals("1150155476873515211")) {
                    eb.setColor(Color.decode("0xdcdddf"));
                    eb.setTitle(title);
                    eb.setDescription(description);
                    event.getChannel().sendMessageEmbeds(eb.build()).queue();
                }
            }
        }
    }
}

