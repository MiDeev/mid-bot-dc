package ru.mideev.midbot.command;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import ru.mideev.midbot.util.UtilLang;

import java.util.Locale;

public class HelpCommand extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (event.getName().equals("help")) {
            EmbedBuilder eb = new EmbedBuilder();

            eb.setColor(event.getMember().getColor());
            eb.setTitle("Список доступных команд: ");
            eb.setDescription("**Получить информацию о сервере:**\n" +
                    "**`/si`** *`(server info)`* - отображает информацию о сервере, содержащую в себе: количество каналов, общие данные об участниках, сведения о бустах, \n" +
                    "ник владельца сервера, ID сервера и тому подобное.\n" +
                    "\n" +
                    "**Получить информацию об участнике:**\n" +
                    "**`/ui`** *`(user info)`* `[ID | @упомнинание]` - отображает всю информацию. Принимает в себя аргументы в виде указания '@' или 'ID' участника. По умолчанию отображает сведения об участнике, вызвавшего команду.\n" +
                    "\n" +
                    "**Получить аватар участника:**\n" +
                    "**`/ava`** `[ID | @упомнинание]` - показывает аватар. Можно получить аватар любого из участников, указывая в аргументах его 'ID' или упомянув его через '@'. По умолчанию отображает информацию, пользователя, вызвавшего команду.\n" +
                    "\n" +
                    "**Получить баннер участника:**\n" +
                    "**`/banner`**  `[ID | @упомнинание]` - позволяет отобразить баннер. Получить баннер одного из участников, можно указав в аргументах его 'ID' или упомянув его через '@'. По умолчанию отображает информацию, пользователя, вызвавшего команду.\n" +
                    "\n" +
                    "**Получить информацию о времени:**\n" +
                    "**`/time`**  `[ID | @упомнинание]` - отображает возраст аккаунта и время с момента входа на сервер. Получить сведения о ком-либо из участников, можно указав в аргументах 'ID' или упомянув пользователя через '@'.  По умолчанию отображает информацию, пользователя, вызвавшего команду.\n" +
                    "\n" +
                    "**Поиграть в \"угадай число\":**\n" +
                    "**`/guess`**  `[число]` - простая игра, в которой главная суть - угадывания числа. Суть: вы пишете число в диапазоне от 0 до 9999, если вам выпадает случайно-сгенерированное число совпадающее с вашим - вы выигрываете! Да, будет нелегко, но оно того стоит, при выигрыше вы получите ||упс, случилась интрига||! Так же, если вы не хотите угадывать число, вы можете генерировать числа, написав `/guess`, не указывая в аргументах абсолютно ничего.");

            eb.setFooter("© 2023 mideev", "https://images-ext-2.discordapp.net/external/7rTn_lmq5h_DIygxeEC5wN5_UY2pwtx8XIzYiG23t8Q/https/cdn.discordapp.com/avatars/421259943123877888/a_4811438747bb7f558531320449410741.gif");

            if (event.getChannel().getId().equals("941458443749978122") && event.getInteraction().getCommandString().toLowerCase(Locale.ROOT).equals(UtilLang.PREFIX + "help")) {
                event.replyEmbeds(eb.build()).queue();

            } else if (event.getInteraction().getMember().getId().equals("421259943123877888") && event.getInteraction().getCommandString().startsWith(UtilLang.PREFIX + "help")) {
                event.replyEmbeds(eb.build()).queue();
            }
        }
    }
}
