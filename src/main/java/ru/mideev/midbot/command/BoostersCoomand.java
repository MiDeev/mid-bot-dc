package ru.mideev.midbot.command;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

import static ru.mideev.midbot.util.UtilLang.DEFAULT_EMBED_COLOR;

public class BoostersCoomand extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("boosters")) {
            EmbedBuilder eb = new EmbedBuilder();
            eb.setColor(Color.decode(DEFAULT_EMBED_COLOR));
            eb.setAuthor("Активные бустеры сервера: ");
            eb.setFooter("Благодаря им сервер становится лучше!");
            List<Member> boosters = Objects.requireNonNull(event.getGuild()).getBoosters();

            if (boosters.isEmpty()) {
                eb.setDescription("На данный момент нет активных бустеров на сервере.");
            } else {
                int count = 1;
                for (Member booster : boosters) {
                    eb.addField("#" + count + " " + booster.getUser().getEffectiveName(), booster.getUser().getName(), false);
                    count++;
                }
            }
            event.replyEmbeds(eb.build()).queue();
        }
    }
}
