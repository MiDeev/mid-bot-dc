package ru.mideev.midbot.command.impl;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import ru.mideev.midbot.command.AbstractCommand;
import ru.mideev.midbot.command.filter.FilterChannelWithMessage;
import ru.mideev.midbot.util.Constants;

import java.util.Collections;

import static ru.mideev.midbot.util.Constants.*;

public class Help extends AbstractCommand {

    public Help() {
        super("help", "Помощь по командам", true);
        filters.add(new FilterChannelWithMessage(Constants.FLOOD_CHANNEL_ID, "<@$AUTHOR_ID>" + " все команды доступны в <#" + FLOOD_CHANNEL_ID + ">", Collections.singletonList("421259943123877888")));
    }

    @Override
    public boolean execute(MessageReceivedEvent event, String[] args) {
        EmbedBuilder eb = new EmbedBuilder();

        eb.setColor(event.getMember().getColor());
        eb.setTitle("Список доступных команд: ");
        eb.addField("Получить информацию о сервере:", "**`.si`** *`[.server info]`* - отображает информацию о сервере, содержащую в себе: количество каналов, общие данные об участниках, сведения о бустах, \nник владельца сервера, ID сервера и тому подобное.", true);
        eb.setFooter("© 2022 MiDeev", "https://cdn.discordapp.com/avatars/789218753576566855/a0a96460803e6cc6e27e7023d07d0bba.webp?size=64");
        event.getMessage().getTextChannel().sendMessageEmbeds(eb.build()).queue();

        return true;
    }
}
