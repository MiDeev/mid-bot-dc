package ru.mideev.midbot.command.admin.other;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
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
                            "*Посредством уведомлений ни одно [объявление](https://discord.com/channels/941320640420532254/950750830833852446) не уйдёт мимо глаз.*\n\n" +
                            "<@&980016919581171763> - **помогает отслеживать вопросы в <#1013527821085315082>.**\n" +
                            "*Эта роль упоминается при каждом новом публичном [вопросе](https://canary.discord.com/channels/941320640420532254/1013527821085315082).*\n" +
                            "\n" +
                            "<@&975336015344566292> | <@&942467119323422820> - **гендерные роли.**\n" +
                            "*Здесь можно быть кем угодно и даже пол тут выбрать можно.*"
            );
            event.getMessage().getChannel().asTextChannel()
                    .sendMessageEmbeds(im.build(), si.build())
                    .setComponents(
                            ActionRow.of(Button.of(ButtonStyle.SECONDARY, "announce", "ANNOUNCEMENTS"),
                                    (Button.of(ButtonStyle.SECONDARY, "tracking", "TRACKING")),
                                    (Button.of(ButtonStyle.SECONDARY, "answerer", "ANSWERER"))),
                            ActionRow.of((Button.of(ButtonStyle.PRIMARY, "male", "BRO")),
                                    (Button.of(ButtonStyle.DANGER, "female", "SIS")))
                    )
                    .queue();
        }
    }

    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        String buttonId = event.getButton().getId();
        String roleId = getRoleId(buttonId);
        if (roleId != null) {
            toggleRole(event, roleId);
        }
    }

    private String getRoleId(String buttonId) {
        return switch (buttonId) {
            case "announce" -> "983322579769126983";
            case "tracking" -> "980016910227869746";
            case "answerer" -> "980016919581171763";
            case "male" -> "975336015344566292";
            case "female" -> "942467119323422820";
            default -> null;
        };
    }

    private void toggleRole(ButtonInteractionEvent event, String roleId) {
        Member member = event.getGuild().getMember(event.getUser());
        Role role = event.getGuild().getRoleById(roleId);
        if (member.getRoles().stream().anyMatch(x -> x.getId().equals(roleId))) {
            event.getGuild()
                    .removeRoleFromMember(member, role)
                    .queue();
            event.getInteraction().deferReply(true).setContent("**Роль __" + role.getName() + "__ успешна убрана.**").queue();
        } else {
            event.getGuild()
                    .addRoleToMember(member, role)
                    .queue();
            event.getInteraction().deferReply(true).setContent("**Роль __" + role.getName() + "__ успешна выдана.**").queue();
        }
    }
}
