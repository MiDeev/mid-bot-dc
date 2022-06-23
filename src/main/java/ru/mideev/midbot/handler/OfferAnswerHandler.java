package ru.mideev.midbot.handler;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateNicknameEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import ru.mideev.midbot.Main;

import java.awt.*;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

public class OfferAnswerHandler extends ListenerAdapter {

    private static final Pattern IDEA_PATTERN = Pattern.compile("Предложение от: <@(.*)+>", Pattern.MULTILINE);

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getMessage().getContentDisplay().toLowerCase(Locale.ROOT).startsWith(".true") && event.getTextChannel().getId().equals("985623622293028935") && event.getMember() != null && event.getMember().getId().equals("421259943123877888")) {

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

                    message.editMessageEmbeds(
                            new EmbedBuilder(embed)
                                    .setAuthor("ПРИНЯТОЕ ПРЕДЛОЖЕНИЕ ", null, user.getEffectiveAvatarUrl())
                                    .addField("РЕШЕНИЕ:", answer, true)
                                    .setColor(Color.decode("0x8cff38"))
                                    .build()
                    ).queue();

                    try {
                        current.delete().queue();
                    } catch (Throwable ignored) {
                    }
                }
            }
        } else if (event.getMessage().getContentDisplay().toLowerCase(Locale.ROOT).startsWith(".false") && event.getTextChannel().getId().equals("985623622293028935") && event.getMember() != null && event.getMember().getId().equals("421259943123877888")) {

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

                    message.editMessageEmbeds(
                            new EmbedBuilder(embed)
                                    .setAuthor("НЕ ПРИНЯТОЕ ПРЕДЛОЖЕНИЕ ", null, user.getEffectiveAvatarUrl())
                                    .addField("РЕШЕНИЕ:", answer, true)
                                    .setColor(Color.decode("0xff3535"))
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
}
