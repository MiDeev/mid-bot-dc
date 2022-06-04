package ru.mideev.midbot.command;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import ru.mideev.midbot.Main;

import java.util.concurrent.TimeUnit;

public class Help extends ListenerAdapter {
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        EmbedBuilder eb = new EmbedBuilder();

        if (event.getChannel().getId().equals("941458443749978122") && event.getMessage().getContentDisplay().equals(".help")) {
            eb.setColor(event.getMember().getColor());
            eb.setTitle("Список доступных команд: ");
            eb.addField("Получить информацию о сервере:", "**`.si`** *`(.server info)`* - отображает информацию о сервере, содержащую в себе: количество каналов, общие данные об участниках, сведения о бустах, \n" +
                    "ник владельца сервера, ID сервера и тому подобное.\n" +
                    "\n" +
                    "**`.ui`** *`(.user info)`* `[ID | @упомнинание]` - отображает информацию об участнике. Принимает в себя аргументы в виде упоминания пользователя через '@' или 'ID'. По умолчанию отображает сведения об участнике, вызвавшем команду.", true);
            eb.setFooter("© 2022 MiDeev", "https://cdn.discordapp.com/attachments/942520425936719952/979496152607096852/vcat_40.png");
            event.getMessage().getTextChannel().sendMessageEmbeds(eb.build()).queue();

            Main.DATABASE.insertCommandUsage(
                    event.getMember().getIdLong(),
                    event.getMessage().getContentDisplay()
            );

        } else if (event.getMessage().getContentDisplay().equals(".help")) {
            eb.setDescription("<@" + event.getMessage().getAuthor().getId() + ">" + " все команды доступны в <#941458443749978122>");
            eb.setColor(event.getMember().getColor());
            event.getMessage().getTextChannel().sendMessageEmbeds(eb.build()).delay(7, TimeUnit.SECONDS).flatMap(Message::delete).queue();
            event.getMessage().delete().queueAfter(3, TimeUnit.SECONDS);
        }
    }
}
