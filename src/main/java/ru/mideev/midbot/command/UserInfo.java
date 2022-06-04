package ru.mideev.midbot.command;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.IMentionable;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import ru.mideev.midbot.Kal;
import ru.mideev.midbot.Main;
import ru.mideev.midbot.util.UtilLang;

import java.awt.*;
import java.util.Objects;
import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

public class UserInfo extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        if (!event.getMessage().getContentDisplay().startsWith(".ui")) return;

        String com = event.getMessage().getContentDisplay();
        String[] args = com.split(" ");

        List<Member> list = event.getMessage().getMentions().getMembers();

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


        if (member.getNickname() == null) {
            ui.addField("Никнейм:", member.getUser().getAsTag(), true);
        }

        if (member.getNickname() != null) {
            ui.addField("Никнейм:", " \n(" + member.getNickname() + ")", true);
        }

        //ui.setTitle("Информация об участнике: " + member.getUser().getAsTag());
        ui.setAuthor("Информация об участнике: " + member.getUser().getAsTag(), null, member.getEffectiveAvatarUrl());

        ui.setDescription("**Общие сведения:**" + "\n" + "** **");
        ui.addField(" ", " ", true);
        ui.addField("Статус:",  UtilLang.onlineStatusToString(member.getOnlineStatus()), true);
        //ui.addField("Время:", "Пробыл на сервере: " + "**" + date + "**" + "\n" + "Дата регистрации: <t:" + member.getTimeCreated().toEpochSecond() + ":d>", false);
        ui.addField("Дата прибытия:", "<t:" + member.getTimeJoined().toEpochSecond() + ":d> \n" + " (<t:" + member.getTimeJoined().toEpochSecond() + ":R>)", true);
        ui.addField("Дата регистрации:", "<t:" + member.getTimeCreated().toEpochSecond() + ":d> \n" + " (<t:" + member.getTimeCreated().toEpochSecond() + ":R>)", true);

        ui.addField("ID:", member.getId(), false);

        ui.addField("Роли:", member.getRoles()
                        .stream()
                        .map(IMentionable::getAsMention)
                        .collect(Collectors.joining("\n"))
                , false);

        ui.setFooter("© 2022 MiDeev", "https://cdn.discordapp.com/attachments/942520425936719952/979496152607096852/vcat_40.png");
        Optional<Activity> activity = member.getActivities().stream().filter(x -> x.getType() == Activity.ActivityType.PLAYING || x.getType() == Activity.ActivityType.CUSTOM_STATUS).findFirst();

        activity.ifPresent(notNullActivity -> {
            if (notNullActivity.isRich()) {
                ui.addField("Играет в:", "\"" + Objects.requireNonNull(notNullActivity.asRichPresence()).getName() + "\"", false);
            } else if (notNullActivity.getName().equals("Custom Status")) {
                ui.addField(null, null, false);
            } else {
                ui.addField("Пользовательский статус:", notNullActivity.getName(), false);
            }
        });

        if (event.getChannel().getId().startsWith("941458443749978122") && com.startsWith(".ui") || com.startsWith(".user info")) {
            event.getGuild().getTextChannels().stream().filter(textChannel -> textChannel.getId().equals("941458443749978122"))
                    .forEach(textChannel -> textChannel.sendMessageEmbeds(ui.build()).queue());
        }

        Main.DATABASE.insertCommandUsage(
                event.getMember().getIdLong(),
                event.getMessage().getContentDisplay()
        );
    }
}
