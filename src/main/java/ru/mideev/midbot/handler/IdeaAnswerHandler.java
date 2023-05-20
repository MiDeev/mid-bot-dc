package ru.mideev.midbot.handler;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.PrivateChannel;
import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateNicknameEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import ru.mideev.midbot.Main;

import java.awt.*;
import java.util.List;
import java.util.regex.Pattern;

public class IdeaAnswerHandler extends ListenerAdapter {

    private static final Pattern IDEA_PATTERN = Pattern.compile("Предложение от: <@(.*)+>", Pattern.MULTILINE);

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (!event.getMessage().getContentDisplay().startsWith(".an") || (!event.getChannel().asTextChannel().getId().equals("1109502097914863667") && !event.getChannel().asTextChannel().getId().equals("979498476452859994")) || event.getMember() == null || !event.getMember().getId().equals("421259943123877888"))
            return;

        Message current = event.getMessage();
        Message message = current.getReferencedMessage();

        if (message != null) {
            List<MessageEmbed> embeds = message.getEmbeds();

            if (embeds.size() != 0) {
                String[] mass = current.getContentRaw().split(" ");
                String[] dist = new String[mass.length - 1];
                System.arraycopy(mass, 1, dist, 0, mass.length - 1);
                String answer = String.join(" ", dist);

                MessageEmbed embed = embeds.get(0);
                User user = message.getGuild().getMemberById(Main.DATABASE.getSnowflakeByMessageId(message.getIdLong())).getUser();

                String ideaType = "";
                if (event.getChannel().asTextChannel().getId().equals("1109502097914863667")) {
                    ideaType = "видео";
                } else if (event.getChannel().asTextChannel().getId().equals("979498476452859994")) {
                    ideaType = "Discord";
                }

                message.editMessageEmbeds(
                        new EmbedBuilder(embed)
                                .setAuthor("ЗАКРЫТАЯ ИДЕЯ ", null, user.getEffectiveAvatarUrl())
                                .addField("Ответ:", answer, true)
                                .setColor(Color.decode("0x96ff3c"))
                                .build()
                ).queue();

                System.out.println(event.getMessage().getId());
                System.out.println(event.getMessage().getContentRaw());

                PrivateChannel privateChannel = user.openPrivateChannel().complete();
                privateChannel.sendMessageEmbeds(
                        new EmbedBuilder()
                                .setTitle("На вашу идею поступил ответ")
                                .setColor(Color.decode("0x96ff3c"))
                                .addField("Ваша идея для " + ideaType + ":", IDEA_PATTERN.matcher(embed.getDescription()).replaceAll("").replaceFirst("\n", "").replaceFirst("\n", ""), false)
                                .addField("Ответ:", answer, false)
                                .setFooter("На вашу идею ответил " + event.getMember().getUser().getAsTag())
                                .build()
                ).queue();

                try {
                    current.delete().queue();
                } catch (Throwable ignored) {
                }
            }
        }

    }
}

