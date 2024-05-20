package ru.mideev.midbot.command.admin.other;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Locale;

public class RulesDisplay extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        EmbedBuilder be = new EmbedBuilder();
        EmbedBuilder im = new EmbedBuilder();
        im.setColor(new Color(0xdcddde));
        im.setImage("https://cdn.discordapp.com/attachments/942520425936719952/1036533351038844948/MiBrothers_Server_Rules_Banner.png");

        if (event.getChannel().getId().equals("942520425936719952") && event.getMessage().getContentDisplay().toLowerCase(Locale.ROOT).equals("rules")) {
            be.setColor(new Color(0xdcddde));
            be.addField("1. Условия использования и правила сообщества Discord:", "Следуйте официальным условиям [**предоставления\n" +
                    "сервисов Discord**](https://discord.com/terms) и [**правил сообщества Discord**](https://discord.com/guidelines).", false);
            be.addField("2. Сетевой этикет и субординация на сервере:", "Общение в чатах не должно выходить за рамки разумного,\n" +
                    "пожалуйста, соблюдайте нормы морали и этики.", false);
            be.addField("3. Содержание сообщений и прочий контент:", "Сообщения, содержащие контент сомнительного характера,\n" +
                    "политические и/или религиозные темы, запрещены на сервере.", false);
            be.addField("4. Cообщения без смысловой нагрузки:", "Нельзя намеренно засорять чаты, писать сообщения\n" +
                    "не по темам каналов и писать сообщения без смысла.", false);
            be.addField("5. Чрезмерные упоминания на сервере:", "Избыточный пинг кого-либо из участников сервера недопустим, \n" +
                    "но это можно когда сам пользователь не против упоминаний.", false);
            be.addField("6. Никнеймы и профили пользователей:", "Имена участников не должны повторяться с чужими, а профиль\n" +
                    "не должен нарушать моральные нормы и копировать другие.", false);
            be.addField("7. Провокация участников:", "Провоцирование участников на нарушение правил сервера\n" +
                    "запрещено в любом из её проявлений и видов.", false);
            be.addField("8. Реклама и коммерция:", "Реклама в любом виде и публикации объявлений о продаже\n" +
                    "чего-либо без согласования с владельцем сервера запрещены.", false);
            be.addField("9. Обход правил:", "Использование уязвимостей в правилах и попытки\n" +
                    "обходов наказаний караются блокировкой на сервере.", false);
            event.getMessage().getChannel().asNewsChannel().sendMessageEmbeds(im.build(), be.build()).queue();
        }
    }
}
