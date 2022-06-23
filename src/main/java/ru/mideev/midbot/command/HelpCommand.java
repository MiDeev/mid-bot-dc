package ru.mideev.midbot.command;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import ru.mideev.midbot.Main;
import ru.mideev.midbot.util.UtilLang;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class HelpCommand extends ListenerAdapter {
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        EmbedBuilder eb = new EmbedBuilder();

        if (event.getChannel().getId().equals("941458443749978122") && event.getMessage().getContentDisplay().toLowerCase(Locale.ROOT).equals(UtilLang.PREFIX + "help")) {
            eb.setColor(event.getMember().getColor());
            eb.setTitle("Список доступных команд: ");
            eb.setDescription("**Получить информацию о сервере:**\n" +
                    "**`.si`** *`(.server info)`* - отображает информацию о сервере, содержащую в себе: количество каналов, общие данные об участниках, сведения о бустах, \n" +
                    "ник владельца сервера, ID сервера и тому подобное.\n" +
                    "\n" +
                    "**Получить информацию об участнике:**\n" +
                    "**`.ui`** *`(.user info)`* `[ID | @упомнинание]` - отображает информацию об участнике. Принимает в себя аргументы в виде упоминания пользователя через '@' или 'ID'. По умолчанию отображает сведения об участнике, вызвавшего команду.\n" +
                    "\n" +
                    "**Получить аватар участника:**\n" +
                    "**`.ava`** *`(.avatar)`* `[ID | @упомнинание]` - показывает аватар участника. Можно получить аватар любого из участников, указывая в аргументах его 'ID' или упомянув его через '@'.  По умолчанию отобразит аватар, вызвавшего команду пользователя.\n" +
                    "\n" +
                    "**Получить баннер участника:**\n" +
                    "**`.banner`**  `[ID | @упомнинание]` - позволяет отобразить баннер участника. Получить баннер одного из участников, можно указав в аргументах его 'ID' или упомянув его через '@'.  По умолчанию отображает баннер, вызвавшего команду пользователя.");
            eb.setFooter("© 2022 MiDeev", "https://cdn.discordapp.com/attachments/942520425936719952/979496152607096852/vcat_40.png");
            event.getMessage().getTextChannel().sendMessageEmbeds(eb.build()).queue();

            Main.DATABASE.insertCommandUsage(
                    event.getMember().getIdLong(),
                    event.getMessage().getContentDisplay()
            );

        } else if (event.getMessage().getAuthor().getId().equals("421259943123877888") && event.getMessage().getContentDisplay().startsWith(UtilLang.PREFIX + "help")) {
            event.getMessage().getTextChannel().sendMessageEmbeds(eb.build()).queue();
        }
    }
}
