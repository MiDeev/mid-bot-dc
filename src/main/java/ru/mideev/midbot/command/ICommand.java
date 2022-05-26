package ru.mideev.midbot.command;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public interface ICommand {

    boolean execute(MessageReceivedEvent event, String[] args);
}
