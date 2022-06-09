package ru.mideev.midbot.command.admin;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import ru.mideev.midbot.Main;

import java.awt.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ClearCommand extends ListenerAdapter {

    public void onMessageReceived(MessageReceivedEvent messageReceivedEvent) {

        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(new Color(255, 53, 107));
        if (!messageReceivedEvent.getMessage().getContentDisplay().startsWith(".clear") || !messageReceivedEvent.getMember().getId().equals("421259943123877888")) return;

        String[] args = messageReceivedEvent.getMessage().getContentRaw().split(" ");

        if (args.length != 2) {
            eb.setDescription("Использование: **.clear <количество>**");
            eb.setColor(new Color(255, 53, 53));
            messageReceivedEvent.getMessage().delete().queueAfter(5, TimeUnit.SECONDS);
            messageReceivedEvent.getChannel().sendMessageEmbeds(eb.build()).queue();
            return;
        }

        if (!isNumber(args[1])) {
            eb.setDescription("**Указан неправильный аргумент.**");
            eb.setColor(new Color(255, 53, 53));
            messageReceivedEvent.getMessage().delete().queueAfter(5, TimeUnit.SECONDS);
            messageReceivedEvent.getChannel().sendMessageEmbeds(eb.build()).queue();
            return;
        }

        List<Message> messageList = messageReceivedEvent.getChannel().getHistory().retrievePast(Integer.parseInt(args[1]) + 1).complete();
        messageReceivedEvent.getTextChannel().deleteMessages(messageList).queue();


        eb.setDescription("Было удалено **" + args[1] + "** сообщений.");
        messageReceivedEvent.getMessage().getTextChannel().sendMessageEmbeds(eb.build()).queue();

        Main.DATABASE.insertCommandUsage(
                messageReceivedEvent.getMember().getIdLong(),
                messageReceivedEvent.getMessage().getContentDisplay()
        );
    }

    private boolean isNumber(String msg) {
        try {
            Integer.parseInt(msg);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
