package ru.mideev.midbot.command.util;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import ru.mideev.midbot.util.UtilLang;

import java.awt.*;

public class Roles extends ListenerAdapter {
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        EmbedBuilder si = new EmbedBuilder();
        if (event.getChannel().getId().equals("983318878258081813") && event.getMessage().getContentDisplay().equals(UtilLang.PREFIX + "roles")) {
            si.setColor(new Color(255, 90, 90));
            si.setAuthor("ВЫБОР РОЛЕЙ:", null, "https://cdn.discordapp.com/attachments/942520425936719952/976107636640587846/13.png");

            si.setDescription(
                    "\uD83C\uDFAC <@&983322579769126983> - **уведомления о новых видео MiDeev'a**\n" +
                            "*Не хотите пропускать уведомления о новых видео MiDeev'a? Тогда эта роль для Вас!*\n" +
                            "\uD83C\uDF1F <@&980016910227869746> - **уведомления об изменениях на сервере и прочих новостях.**\n" +
                            "*Интересуетесь новостями и обновлениями сервера? Тогда эта роль для Вас!*\n" +
                            "\n" +
                            "\uD83D\uDCA5 <@&980016919581171763> - **роль для доступа к каналу с дичью.**\n" +
                            "*Если Вы морально готовы к возможным оскорблениям и прочей дичи, тогда эта роль для Вас.*"
            );
            event.getMessage().getTextChannel().sendMessageEmbeds(si.build()).queue();
        }

        if (event.getChannel().getId().equals("983318878258081813") && event.getMessage().getContentDisplay().equals(UtilLang.PREFIX + "roles")) {
            si.setColor(new Color(255, 90, 90));
                        si.setDescription(
                    "\uD83D\uDC99 <@&975336015344566292> | \uD83D\uDC9C <@&942467119323422820> - **гендерные роли.**\n" +
                            "*Можно выбрать одну из ролей, нажав на реакцию ниже*."
            );
            event.getMessage().getTextChannel().sendMessageEmbeds(si.build()).queue();
        }
    }
}
