package ru.mideev.midbot.command.util;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.buttons.ButtonStyle;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Locale;
import java.util.Objects;

public class Roles extends ListenerAdapter {
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        EmbedBuilder si = new EmbedBuilder();
        EmbedBuilder im = new EmbedBuilder();
        if (event.getChannel().getId().equals("983318878258081813") && event.getMessage().getContentDisplay().toLowerCase(Locale.ROOT).equals(".roles")) {
            si.setColor(new Color(220, 221, 222));
            im.setColor(new Color(220, 221, 222));
            im.setImage("https://cdn.discordapp.com/attachments/942520425936719952/1043953226917085344/chosing_roles.png");

            si.setDescription(
                    "<@&983322579769126983> - **уведомления о новых видео.**\n" +
                            "*Уведомляет о выходе каждого из видео с [канала](https://www.youtube.com/channel/UCTHm7Ijz2b7XJPkl4_W0vjA) <@421259943123877888>'a*\n\n" +
                            "<@&980016910227869746> - **позволяет отслеживать объявления сервера.**\n" +
                            "*Посредством уведомлений ни одно [объявление](https://discord.com/channels/941320640420532254/950750830833852446) не уйдёт мимо глаз.*\n" +
                            "\n" +
                            "<@&980016919581171763> - **роль для доступа к каналу с \"дичью\".**\n" +
                            "*Данная роль открывает доступ к каналу [без правил](https://discord.com/channels/941320640420532254/983318366750146570).*" +
                            "\n\n" +
                            "<@&975336015344566292> | <@&942467119323422820> - **гендерные роли.**\n" +
                            "*Здесь можно быть кем угодно и даже пол тут выбрать можно.*"
            );
            event.getMessage().getChannel().asNewsChannel()
                    .sendMessageEmbeds(im.build(), si.build())
                    .setComponents(
                            ActionRow.of(Button.of(ButtonStyle.SECONDARY, "announce", "ANNOUNCEMENTS"),
                                    (Button.of(ButtonStyle.SECONDARY, "tracking", "TRACKING")),
                                    (Button.of(ButtonStyle.SECONDARY, "nsfw", "NSFW LISTENER"))),
                            ActionRow.of((Button.of(ButtonStyle.PRIMARY, "male", "BRO")),
                                    (Button.of(ButtonStyle.DANGER, "female", "SIS")))
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
