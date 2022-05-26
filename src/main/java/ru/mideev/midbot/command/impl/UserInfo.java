package ru.mideev.midbot.command.impl;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import ru.mideev.midbot.command.AbstractCommand;
import ru.mideev.midbot.command.filter.FilterChannelWithMessage;
import ru.mideev.midbot.util.Constants;
import ru.mideev.midbot.util.DateUtil;

import java.awt.*;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static ru.mideev.midbot.util.Constants.FLOOD_CHANNEL_ID;

public class UserInfo extends AbstractCommand {

    public UserInfo() {
        super("ui", "Информация о пользователе", true);
        filters.add(new FilterChannelWithMessage(Constants.FLOOD_CHANNEL_ID, "<@$AUTHOR_ID>" + " все команды доступны в <#" + FLOOD_CHANNEL_ID + ">", Collections.singletonList("421259943123877888")));
    }

    @Override
    public boolean execute(MessageReceivedEvent event, String[] args) {
        List<Member> list = event.getMessage().getMentionedMembers();

        Member member = event.getMember();
        try {
            if (!list.isEmpty()) {
                member = list.get(0);
            } else if (args.length == 1) {
                member = event.getGuild().getMemberById(args[0]);
            }
        } catch (Throwable throwable) {
            EmbedBuilder eb = new EmbedBuilder();
            eb.setColor(event.getMember().getColor());
            eb.addField("Ошибка!", "Чота неправильно, давай по новой.", true);
            event.getMessage().getTextChannel().sendMessageEmbeds(eb.build()).queue();
            throw throwable;
        }

        String date = DateUtil.formatDate(member);

        EmbedBuilder ui = new EmbedBuilder();
        ui.setColor(new Color(141, 127, 254));

        ui.setTitle("Информация об участнике: " + member.getUser().getAsTag());

        ui.addField("Время:", "Пробыл на сервере: " + "**" + date + "**", true);

        Optional<Activity> activity = member.getActivities().stream().filter(x -> x.getType() == Activity.ActivityType.CUSTOM_STATUS).findFirst();

        activity.ifPresent(notNullActivity -> {
            String status = notNullActivity.getName();
            ui.addField("Статус:", status, false);
        });

        event.getMessage().getChannel().sendMessageEmbeds(ui.build()).queue();
        return true;
    }
}
