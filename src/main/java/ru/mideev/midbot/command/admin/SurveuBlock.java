package ru.mideev.midbot.command.admin;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class SurveuBlock extends ListenerAdapter {
    public static final String CHANNEL_ID = "1119244423281262702";
    public static final String ROLE_ID = "1012278002601705473";
    public static final String TARGET_CHANNEL_ID = "1013527821085315082";

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (!(event.getChannel() instanceof TextChannel)) return;

        Message message = event.getMessage();
        String content = message.getContentRaw();
        TextChannel channel = (TextChannel) event.getChannel();

        if (content.startsWith(".gg") && channel.getId().equals(CHANNEL_ID)) {
            Member member = event.getMember();
            if (member == null) return;

            Role role = event.getGuild().getRoleById(ROLE_ID);
            if (role == null) return;

            if (!member.getRoles().contains(role)) {
                channel.sendMessage("У вас нет разрешения на использование этой команды.").queue();
                return;
            }

            String[] args = content.split("\\s+");
            if (args.length != 2) {
                channel.sendMessage("Неверный формат команды. Используйте: .gg MEMBER_ID").queue();
                return;
            }

            String memberId = args[1];
            try {
                long id = Long.parseLong(memberId);
                Member target = event.getGuild().getMemberById(id);
                if (target == null) {
                    channel.sendMessage("Нет такого участника с таким ID.").queue();
                    return;
                }

                TextChannel targetChannel = event.getGuild().getTextChannelById(TARGET_CHANNEL_ID);
                if (targetChannel == null) return;

                targetChannel.upsertPermissionOverride(target)
                        .deny(Permission.VIEW_CHANNEL)
                        .queue();

                channel.sendMessage("Успешно заблокирован доступ к каналу для " + target.getAsMention()).queue();

            } catch (NumberFormatException e) {
                channel.sendMessage("Неверный формат ID участника.").queue();
            }
        }
    }
}

