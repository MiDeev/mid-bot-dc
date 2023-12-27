package ru.mideev.midbot.command.admin;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import ru.mideev.midbot.Main;
import ru.mideev.midbot.dao.UsersDao;
import ru.mideev.midbot.util.UtilLang;

import java.util.Comparator;
import java.util.Locale;

public class TestCommand extends ListenerAdapter {
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (!event.getMessage().getContentDisplay().equals(".dates")) return;

        Main.DATABASE.getJdbi().useExtension(UsersDao.class, dao -> {
            System.out.println(dao.findUserOrCreate(event.getAuthor().getIdLong()));
        });

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
                    .sorted(Comparator.comparing(member -> {
                        member.getUser().getName();
                        return member.getUser().getName().length();
                    }))
                    .forEach(member -> {
                        member.getUser().getName();
                        System.out.println(member.getUser().getName());
                    });
            System.out.println("Time: " + (System.nanoTime() - start));
        }
        System.out.println("USERS COUNT: " + event.getGuild().getMemberCount());
    }
}
