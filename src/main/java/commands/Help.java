package commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class Help extends ListenerAdapter {
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        EmbedBuilder eb = new EmbedBuilder();

        if (event.getChannel().getId().equals("941458443749978122") && event.getMessage().getContentDisplay().startsWith("/help")) {
            eb.setColor(event.getMember().getColor());
            eb.setTitle("Список доступных команд: ");
            eb.addField("Получить информацию о сервере:", "**`/si`** *`[/server info]`* - отображает информацию о сервере, содержащую в себе: количество каналов, общие данные об участниках, сведения о бустах, \nник владельца сервера, ID сервера и тому подобное.", true);
            eb.setFooter("© 2022 MiDeev", "https://cdn.discordapp.com/avatars/789218753576566855/a0a96460803e6cc6e27e7023d07d0bba.webp?size=64");
            event.getMessage().getTextChannel().sendMessageEmbeds(eb.build()).queue();

        } else if (event.getMessage().getContentDisplay().startsWith("/help")) {
            eb.setDescription("<@" + event.getMessage().getAuthor().getId() + ">" + " все команды доступны в <#941458443749978122>");
            eb.setColor(event.getMember().getColor());
            event.getMessage().getTextChannel().sendMessageEmbeds(eb.build()).delay(7, TimeUnit.SECONDS).flatMap(Message::delete).queue();
            event.getMessage().delete().queueAfter(3, TimeUnit.SECONDS);
        }
    }
}
