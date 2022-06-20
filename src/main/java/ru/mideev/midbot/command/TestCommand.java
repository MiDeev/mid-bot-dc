package ru.mideev.midbot.command;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import ru.mideev.midbot.util.UtilLang;

import java.awt.*;
import java.util.Comparator;

public class TestCommand extends ListenerAdapter {
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (!event.getMessage().getContentDisplay().equals(UtilLang.PREFIX + "keka")) return;

        System.out.println("DATES");

        {
            long start = System.nanoTime();

            event.getGuild().getMembers()
                    .stream()
                    .sorted(Comparator.comparing(Member::getTimeCreated))
                    .forEach(member -> System.out.println(member.getUser().getAsTag() + " - " + member.getUser().getTimeCreated()));

            System.out.println("Time: " + (System.nanoTime() - start));
        }

        System.out.println("NICKNAMES");

        {
            long start = System.nanoTime();

            event.getGuild().getMembers()
                    .stream()
                    .sorted(Comparator.comparing(member -> member.getNickname() == null ? member.getUser().getName().length() : member.getNickname().length()))
                    .forEach(member -> System.out.println(member.getNickname() == null ? member.getUser().getName() : member.getNickname()));

            System.out.println("Time: " + (System.nanoTime() - start));
        }

        System.out.println("USERS COUNT: " + event.getGuild().getMemberCount());
    }
}
