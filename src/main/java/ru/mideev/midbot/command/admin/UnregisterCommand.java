package ru.mideev.midbot.command.admin;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class UnregisterCommand extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw();

        if (message.startsWith(".unreg")) {
            if (event.getMember().getId().equals("421259943123877888")) {
                String commandId = message.split(" ")[1];
                event.getJDA().deleteCommandById(commandId).submit();
                event.getChannel().sendMessage("Команда с ID \"" + commandId + "\" успешно удалена!").queue();
            } else {
                event.getChannel().sendMessage("Чтобы воспользоваться этой командой вам необходимо стать Мидеевым.").queue();
            }
        }
    }
}