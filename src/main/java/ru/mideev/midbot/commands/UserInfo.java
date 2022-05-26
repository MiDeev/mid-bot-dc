package ru.mideev.midbot.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import ru.mideev.midbot.Kal;

import java.awt.*;
import java.util.Optional;
import java.util.List;

public class UserInfo extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        String com = event.getMessage().getContentDisplay();
        String[] args = com.split(" ");

        List<Member> list = event.getMessage().getMentionedMembers();

        Member member = event.getMember();
        try {
            if (!list.isEmpty()) {
                member = list.get(0);
            } else if (args.length == 2) {
                member = event.getGuild().getMemberById(args[1]);
            }
        } catch (Throwable throwable) {
            EmbedBuilder eb = new EmbedBuilder();
            eb.setColor(event.getMember().getColor());
            eb.addField("Ошибка!", "Чота неправильно, давай по новой.", true);
            //event.getMessage().getTextChannel().sendMessageEmbeds(eb.build()).queue();
            throw throwable;
        }

        if (member == null) return;



        String date = Kal.formatDate(member);

        EmbedBuilder ui = new EmbedBuilder();
        ui.setColor(new Color(141, 127, 254));

        ui.setTitle("Информация об участнике: " + member.getUser().getAsTag());

        ui.addField("Время:", "Пробыл на сервере: " + "**" + date + "**", true);

        Optional<Activity> activity = member.getActivities().stream().filter(x -> x.getType() == Activity.ActivityType.CUSTOM_STATUS).findFirst();

        activity.ifPresent(notNullActivity -> {
            String status = notNullActivity.getName();
            ui.addField("Статус:", status, false);
        });

        if (event.getChannel().getId().startsWith("941458443749978122") && com.startsWith(".ui") || com.startsWith(".user info")) {
            event.getGuild().getTextChannels().stream().filter(textChannel -> textChannel.getId().equals("941458443749978122"))
                    .forEach(textChannel -> textChannel.sendMessageEmbeds(ui.build()).queue());
        }

    }
}
