package ru.mideev.midbot.command.admin;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import ru.mideev.midbot.Main;
import ru.mideev.midbot.util.UtilLang;

public class CommandCountCommand extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getMessage().getContentDisplay().equals(UtilLang.PREFIX + "cc")) {
            System.out.println(Main.DATABASE.countAllCommandUsages());
        }
    }
}
