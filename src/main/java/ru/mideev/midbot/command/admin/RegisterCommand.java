package ru.mideev.midbot.command.admin;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterCommand extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw();

        if (message.startsWith(".reg")) {

            if (event.getMember().getId().equals("421259943123877888")) {

                Pattern pattern = Pattern.compile("\"(.*?)\"");
                Matcher matcher = pattern.matcher(message);
                List<String> matches = new ArrayList<>();
                while (matcher.find()) {
                    matches.add(matcher.group(1));
                }

                if (matches.size() < 2) {
                    event.getChannel().sendMessage("Не указано название или описание команды в кавычках.").queue();
                    return;
                }

                String commandName = matches.get(0);
                String commandDescription = matches.get(1);

                event.getJDA().upsertCommand(commandName, commandDescription).submit();
                event.getChannel().sendMessage("Команда \"" + commandName + "\" успешно зарегистрирована!").queue();
            } else {
                event.getChannel().sendMessage("Вы не можете воспользоваться командой, поскольку вы не Мидеев.").queue();
            }
        }
    }
}