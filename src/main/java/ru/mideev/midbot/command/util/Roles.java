package ru.mideev.midbot.command.util;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.buttons.ButtonStyle;
import org.jetbrains.annotations.NotNull;
import ru.mideev.midbot.util.UtilLang;

import java.awt.*;
import java.util.Locale;
import java.util.Objects;

public class Roles extends ListenerAdapter {
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        EmbedBuilder si = new EmbedBuilder();
        if (event.getChannel().getId().equals("983318878258081813") && event.getMessage().getContentDisplay().toLowerCase(Locale.ROOT).equals(".roles")) {
            si.setColor(new Color(255, 52, 52));
            si.setAuthor("ВЫБОР РОЛЕЙ:", null, "https://cdn.discordapp.com/attachments/942520425936719952/976107636640587846/13.png");

            si.setDescription(
                    "\uD83C\uDFAC <@&983322579769126983> - **уведомления о новых видео MiDeev'a**\n" +
                            "*Роль для тех, кто не хочет пропускать уведомления о новых видео <@421259943123877888>'a*\n\n" +
                            "\uD83C\uDF1F <@&980016910227869746> - **уведомления об изменениях сервера и прочих новостях.**\n" +
                            "*Интересуетесь новостями и обновлениями сервера? Тогда эта роль для Вас.*\n" +
                            "\n" +
                            "\uD83D\uDCA5 <@&980016919581171763> - **роль для доступа к каналу с дичью.**\n" +
                            "*Получив эту роль - Вам откроется чат с полным отстутствием правил.*" +
                            "\n\n" +
                            "\uD83D\uDC99 <@&975336015344566292> | \uD83D\uDC9C <@&942467119323422820> - **гендерные роли.**\n" +
                            "*Можно выбрать одну из ролей, нажав на реакцию ниже*."
            );
            event.getMessage().getChannel().asTextChannel()
                    .sendMessageEmbeds(si.build())
                    .setActionRows(
                            ActionRow.of(Button.of(ButtonStyle.SECONDARY, "announce", "\uD83C\uDFAC АНОНСЫ"),
                                    (Button.of(ButtonStyle.SECONDARY, "tracking", "\uD83C\uDF1F ОБЪЯВЛЕНИЯ")),
                                    (Button.of(ButtonStyle.SECONDARY, "nsfw", "\uD83D\uDCA5 NSFW")),
                                    (Button.of(ButtonStyle.PRIMARY, "male", "\uD83D\uDC99 BRO")),
                                    (Button.of(ButtonStyle.DANGER, "female", "\uD83D\uDC9C SIS")))
                    )
                    .queue();
        }
    }

    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        if (Objects.equals(event.getButton().getId(), "announce")) {
            if (event.getGuild().getMember(event.getUser()).getRoles().stream().anyMatch(x -> x.getId().equals("983322579769126983"))) {
                event.getGuild()
                        .removeRoleFromMember(event.getUser(), event.getGuild().getRoleById("983322579769126983"))
                        .queue();
                event.getInteraction().deferReply(true).setContent("Роль успешна убрана.").queue();
            } else {
                event.getGuild()
                        .addRoleToMember(event.getUser(), event.getGuild().getRoleById("983322579769126983"))
                        .queue();
                event.getInteraction().deferReply(true).setContent("Роль успешна выдана.").queue();
            }
        }
        if (Objects.equals(event.getButton().getId(), "tracking")) {
            if (event.getGuild().getMember(event.getUser()).getRoles().stream().anyMatch(x -> x.getId().equals("980016910227869746"))) {
                event.getGuild()
                        .removeRoleFromMember(event.getUser(), event.getGuild().getRoleById("980016910227869746"))
                        .queue();
                event.getInteraction().deferReply(true).setContent("Роль успешна убрана.").queue();
            } else {
                event.getGuild()
                        .addRoleToMember(event.getUser(), event.getGuild().getRoleById("980016910227869746"))
                        .queue();
                event.getInteraction().deferReply(true).setContent("Роль успешна выдана.").queue();
            }
        }
        if (Objects.equals(event.getButton().getId(), "nsfw")) {
            if (event.getGuild().getMember(event.getUser()).getRoles().stream().anyMatch(x -> x.getId().equals("980016919581171763"))) {
                event.getGuild()
                        .removeRoleFromMember(event.getUser(), event.getGuild().getRoleById("980016919581171763"))
                        .queue();
                event.getInteraction().deferReply(true).setContent("Роль успешна убрана.").queue();
            } else {
                event.getGuild()
                        .addRoleToMember(event.getUser(), event.getGuild().getRoleById("980016919581171763"))
                        .queue();
                event.getInteraction().deferReply(true).setContent("Роль успешна выдана.").queue();
            }
        }
        if (Objects.equals(event.getButton().getId(), "male")) {
            if (event.getGuild().getMember(event.getUser()).getRoles().stream().anyMatch(x -> x.getId().equals("975336015344566292"))) {
                event.getGuild()
                        .removeRoleFromMember(event.getUser(), event.getGuild().getRoleById("975336015344566292"))
                        .queue();
                event.getInteraction().deferReply(true).setContent("Роль успешна убрана.").queue();
            } else {
                event.getGuild()
                        .addRoleToMember(event.getUser(), event.getGuild().getRoleById("975336015344566292"))
                        .queue();
                event.getInteraction().deferReply(true).setContent("Роль успешна выдана.").queue();
            }
        }
        if (Objects.equals(event.getButton().getId(), "female")) {
            if (event.getGuild().getMember(event.getUser()).getRoles().stream().anyMatch(x -> x.getId().equals("942467119323422820"))) {
                event.getGuild()
                        .removeRoleFromMember(event.getUser(), event.getGuild().getRoleById("942467119323422820"))
                        .queue();
                event.getInteraction().deferReply(true).setContent("Роль успешна убрана.").queue();
            } else {
                event.getGuild()
                        .addRoleToMember(event.getUser(), event.getGuild().getRoleById("942467119323422820"))
                        .queue();
                event.getInteraction().deferReply(true).setContent("Роль успешна выдана.").queue();
            }
        }
    }
}
