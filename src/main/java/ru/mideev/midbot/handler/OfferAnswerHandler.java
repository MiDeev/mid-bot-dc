package ru.mideev.midbot.handler;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import ru.mideev.midbot.Main;
import ru.mideev.midbot.util.HandlerUtil;

import java.awt.*;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Pattern;

public class OfferAnswerHandler extends ListenerAdapter {

//    private static final Pattern IDEA_PATTERN = Pattern.compile("Предложение от: <@(.*)+>", Pattern.MULTILINE);

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        Message current = event.getMessage();
        Message message = current.getReferencedMessage();

        if (event.getMember() == null)
            return;

        if (message == null) {
            return;
        }

        if (!(event.getChannel().asTextChannel().getId().equals("985623622293028935") && event.getMember().getId().equals("421259943123877888")))
            return;

        List<MessageEmbed> embeds = message.getEmbeds();

        if (embeds.size() == 0) {
            return;
        }

        String answer = HandlerUtil.getAnswer(current, message);

        if (event.getMessage().getContentDisplay().toLowerCase(Locale.ROOT).startsWith(".true")) {

            MessageEmbed embed = embeds.get(0);
            User user = Objects.requireNonNull(message.getGuild().getMemberById(Main.DATABASE.getSnowflakeByMessageId(message.getIdLong()))).getUser();
            message.editMessageEmbeds(
                    new EmbedBuilder(embed)
                            .setAuthor("ПРИНЯТОЕ ПРЕДЛОЖЕНИЕ ", null, user.getEffectiveAvatarUrl())
                            .addField("РЕШЕНИЕ:", answer, true)
                            .setColor(Color.decode("0x8cff38"))
                            .build()
            ).queue();

        } else if (event.getMessage().getContentDisplay().toLowerCase(Locale.ROOT).startsWith(".false")) {

            MessageEmbed embed = embeds.get(0);
            User user = message.getGuild().getMemberById(Main.DATABASE.getSnowflakeByMessageId(message.getIdLong())).getUser();
            message.editMessageEmbeds(
                    new EmbedBuilder(embed)
                            .setAuthor("ОТКЛОНЁННОЕ ПРЕДЛОЖЕНИЕ ", null, user.getEffectiveAvatarUrl())
                            .addField("РЕШЕНИЕ:", answer, true)
                            .setColor(Color.decode("0xff3535"))
                            .build()
            ).queue();

        }

        current.delete().queue();
    }
}
