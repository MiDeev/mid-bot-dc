package ru.mideev.midbot.command.admin;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import ru.mideev.midbot.Main;
import ru.mideev.midbot.util.UtilLang;

import java.util.Locale;

public class CommandCountCommand extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getMessage().getContentDisplay().toLowerCase(Locale.ROOT).equals(UtilLang.PREFIX + "cc")) {
            System.out.println(Main.DATABASE.countAllCommandUsages());
        }
    }
}
