package ru.mideev.midbot.command;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.jetbrains.annotations.NotNull;
import ru.mideev.midbot.util.NumberGenerator;
import ru.mideev.midbot.util.UtilLang;

import java.awt.*;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

import static ru.mideev.midbot.util.UtilLang.EMBED_COLOR;

public class RandomNumberCommand extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (event.getName().equals("guess")) {
            String com = event.getInteraction().getCommandString();
            OptionMapping optionMapping = event.getInteraction().getOption("число");

            EmbedBuilder rn = new EmbedBuilder();
            rn.setColor(Color.decode(EMBED_COLOR));
            rn.setAuthor("Информация о выпавшем числе:", null);

            ThreadLocalRandom random = ThreadLocalRandom.current();

            String str = "";
            String genNum = NumberGenerator.pad(random.nextInt(9999) + 1);
            String finalNumber = "";
            if (optionMapping != null) {
                int num = (int) optionMapping.getAsDouble();

                if (num < 0) {
                    num = 0;
                }

                if (num > 9999) {
                    num = 9999;
                }

                String numStr = String.valueOf(num);

                if (numStr.length() <= 4) {
                    StringBuilder builder = new StringBuilder();

                    for (int i = 4 - numStr.length(); i > 0; --i) {
                        builder.append("0");
                    }

                    builder.append(numStr);
                    str = "Ваше число: **" + builder + "**";

                    if (builder.toString().equals(genNum)) {
                        finalNumber = "**Число совпало!** \n" + genNum + "⭐ **=** ⭐" + builder;
                        rn.setColor(Color.decode("#85db19"));

                    } else {
                        finalNumber = "**Число не совпало**: \n" + genNum + " **!=** " + builder;
                        rn.setColor(Color.decode("#ff531f"));
                    }
                }
            }

            rn.setDescription("Вам выпало число: **" + genNum + "**" + "\n" + str + "\n\n" + finalNumber);
            rn.setFooter("Команду запросил: " + UtilLang.memberTagFormat(event.getMember()), event.getMember().getEffectiveAvatarUrl());

            if (event.getChannel().getId().equals("941458443749978122") && com.toLowerCase(Locale.ROOT).startsWith(UtilLang.PREFIX + "guess")) {
                event.replyEmbeds(rn.build()).queue();
            } else if (event.getInteraction().getCommandString().toLowerCase(Locale.ROOT).startsWith(UtilLang.PREFIX + "guess")) {
                event.replyEmbeds(rn.build()).queue();
            }
        }
    }
}
