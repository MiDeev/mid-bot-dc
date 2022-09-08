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
                    "**`/ui`** *`(user info)`* `[ID | @упомнинание]` - отображает информацию об участнике. Принимает в себя аргументы в виде упоминания пользователя через '@' или 'ID'. По умолчанию отображает сведения об участнике, вызвавшего команду.\n" +
                    "\n" +
                    "**Получить аватар участника:**\n" +
                    "**`/ava`** `[ID | @упомнинание]` - показывает аватар участника. Можно получить аватар любого из участников, указывая в аргументах его 'ID' или упомянув его через '@'.  По умолчанию отобразит аватар, вызвавшего команду пользователя.\n" +
                    "\n" +
                    "**Получить баннер участника:**\n" +
                    "**`/banner`**  `[ID | @упомнинание]` - позволяет отобразить баннер участника. Получить баннер одного из участников, можно указав в аргументах его 'ID' или упомянув его через '@'.  По умолчанию отображает баннер, вызвавшего команду пользователя.");
            eb.setFooter("© 2022 MiDeev", "https://cdn.discordapp.com/attachments/942520425936719952/979496152607096852/vcat_40.png");

            if (event.getChannel().getId().equals("941458443749978122") && event.getInteraction().getCommandString().toLowerCase(Locale.ROOT).equals(UtilLang.PREFIX + "help")) {
                event.replyEmbeds(eb.build()).queue();

            } else if (event.getInteraction().getMember().getId().equals("421259943123877888") && event.getInteraction().getCommandString().startsWith(UtilLang.PREFIX + "help")) {
                event.replyEmbeds(eb.build()).queue();
            }
        }
    }
}
