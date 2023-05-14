package ru.mideev.midbot.command.admin.other;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Locale;

public class Rules extends ListenerAdapter {
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        EmbedBuilder be = new EmbedBuilder();
        EmbedBuilder im = new EmbedBuilder();
        im.setColor(new Color(0xdcddde));
        im.setImage("https://cdn.discordapp.com/attachments/942520425936719952/1036533351038844948/MiBrothers_Server_Rules_Banner.png");

        if (event.getChannel().getId().equals("942520425936719952") && event.getMessage().getContentDisplay().toLowerCase(Locale.ROOT).equals("rules")) {
            be.setColor(new Color(0xdcddde));
            be.addField("01. Следуйте официальным условиям использования\n" +
                    "сервисов Discord и правилам сообщества Discord.","Наказание: блокировка на сервере. `[бан]`",false);
            be.addField("02. Соблюдайте субординацию, уважайте других участников\n" +
                    "сервера, не устраивайте конфликты в чатах сервера.","Наказание: ограничение к общению на сервере. `[мут]`",false);
            be.addField("03. Спам, флуд и несогласованная реклама запрещены.","Наказание: ограничение к общению на сервере. `[мут]`",false);
            be.addField("04. Контенту сомнительного характера, политическим,\n" +
                    "нацистским и религиозным темам нет места на сервере.","Наказание: ограничение к общению на сервере. `[мут]`",false);
            be.addField("05. Аватарки и никнеймы пользователей не должны нарушать\n" +
                    "моральные нормы, повторяться с чьими-либо из других\n" +
                    "участников, а так же перегружены юникод-символами.","Наказание: блокировка на сервере. `[бан]`",false);
            be.addField("06. Использование Discord сервера в коммерческих целях\n" +
                    "для публикации объявлений о продаже чего-либо запрещено.","Наказание: блокировка на сервере. `[бан]`",false);
            be.addField("07. Сообщения, не несущие какую-либо смысловую нагрузку,\n" +
                    "а также излишние разбиения сообщений на раздельные строки,\n" +
                    "которые можно написать одним сообщением недопустимы.","Наказание: ограничение к общению на сервере. `[мут]`",false);
            be.addField("08. Злоупотребление нецензурными выражениями, токсичное\n" +
                    "поведение, игнорирование сетевого этикета неприемлемо.","Наказание: ограничение к общению на сервере. `[мут]`",false);
            event.getMessage().getChannel().asNewsChannel().sendMessageEmbeds(im.build(), be.build()).queue();
        }
    }
}
