package ru.mideev.midbot.command.admin;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class SurveyBlock extends ListenerAdapter {
    public static final String CHANNEL_ID = "1119244423281262702";
    public static final String ROLE_ID = "1012278002601705473";
    public static final String TARGET_CHANNEL_ID = "1013527821085315082";

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (!(event.getChannel() instanceof TextChannel)) return;

        Message message = event.getMessage();
        String content = message.getContentRaw();
        TextChannel channel = (TextChannel) event.getChannel();

        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(Color.decode("0x5163ff"));

        if (content.startsWith(".gg") && channel.getId().equals(CHANNEL_ID)) {
            Member member = event.getMember();
            if (member == null) return;

            Role role = event.getGuild().getRoleById(ROLE_ID);
            if (role == null) return;

            if (!member.getRoles().contains(role)) {
                channel.sendMessage("**У вас нет разрешения на использование этой команды.**").queue();
                return;
            }

            String[] args = content.split("\\s+");
            if (args.length != 2) {
                eb.setColor(Color.decode("0xff531f"));
                eb.setDescription("**Неверный формат команды. Используйте: `.gg MEMBER_ID`**");
                channel.sendMessageEmbeds(eb.build()).queue();
                return;
            }

            String memberId = args[1];
            try {
                long id = Long.parseLong(memberId);
                Member target = event.getGuild().getMemberById(id);
                if (target == null) {
                    eb.setColor(Color.decode("0xff531f"));
                    eb.setDescription("**Участника с таким ID нет.**");
                    channel.sendMessageEmbeds(eb.build()).queue();
                    return;
                }

                TextChannel targetChannel = event.getGuild().getTextChannelById(TARGET_CHANNEL_ID);
                if (targetChannel == null) return;

                targetChannel.upsertPermissionOverride(target)
                        .deny(Permission.VIEW_CHANNEL)
                        .queue();

                eb.setDescription("**Участнику " + target.getAsMention() + " был заблокирован доступ к каналу \n<#1013527821085315082>.**");
                channel.sendMessageEmbeds(eb.build()).queue();

            } catch (NumberFormatException e) {
                eb.setColor(Color.decode("0xff531f"));
                eb.setDescription("**Неверный формат ID участника.** \n\nИспользуйте только числовой формат в качестве аргумента.");
                channel.sendMessageEmbeds(eb.build()).queue();
            }
        }
    }
}

