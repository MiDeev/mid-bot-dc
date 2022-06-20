package ru.mideev.midbot.command;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.utils.ImageProxy;
import org.jetbrains.annotations.NotNull;
import ru.mideev.midbot.util.DataUtil;
import ru.mideev.midbot.Main;
import ru.mideev.midbot.util.UtilLang;

import java.awt.*;
import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

public class UserInfo extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {


        if (!event.getMessage().getContentDisplay().startsWith(UtilLang.PREFIX + "ui")) return;

        String com = event.getMessage().getContentDisplay();
        String[] args = com.split(" ");

        List<Member> list = event.getMessage().getMentions().getMembers();

        Member member = event.getMember();

        EmbedBuilder eb = new EmbedBuilder();

        if (member != null) {
            Color color = member.getColor();

            if (color != null) {
                eb.setColor(color);
            }

            eb.addField("Ошибка:", "Указан не верный аргумент.", true);
        }

        try {
            if (!list.isEmpty()) {
                member = list.get(0);
            } else if (args.length == 2) {
                member = event.getGuild().getMemberById(args[1]);
            }
        } catch (Throwable throwable) {
            event.getMessage().getTextChannel().sendMessageEmbeds(eb.build()).queue();
            throw throwable;
        }


        if (member == null) return;

        String date = DataUtil.formatDate(member);

        EmbedBuilder ui = new EmbedBuilder();
        ui.setColor(new Color(141, 127, 254));

        String nickname = member.getNickname();

        if (nickname == null) {
            ui.addField("Никнейм:", member.getUser().getAsTag(), true);
        } else {
            ui.addField("Никнейм:", member.getUser().getName() + " \n(" + nickname + ")", true);
        }

        //ui.setTitle("Информация об участнике: " + member.getUser().getAsTag());
        ui.setAuthor("Информация об участнике: " + member.getUser().getAsTag(), null, member.getEffectiveAvatarUrl());

        ui.setDescription("**Общие сведения:**" + "\n" + "** **");
        ui.addField("Устройство:", UtilLang.clientTypeToString(member.getActiveClients().stream().findFirst().orElse(ClientType.UNKNOWN)), true);
        ui.addField("Статус:", UtilLang.onlineStatusToString(member.getOnlineStatus()), true);
        //ui.addField("Время:", "Пробыл на сервере: " + "**" + date + "**" + "\n" + "Дата регистрации: <t:" + member.getTimeCreated().toEpochSecond() + ":d>", false);
        ui.addField("Дата прибытия:", "<t:" + member.getTimeJoined().toEpochSecond() + ":d> \n" + " (<t:" + member.getTimeJoined().toEpochSecond() + ":R>)", true);
        ui.addField("Дата регистрации:", "<t:" + member.getTimeCreated().toEpochSecond() + ":d> \n" + " (<t:" + member.getTimeCreated().toEpochSecond() + ":R>)", true);

        ui.addField("ID:", member.getId(), false);

        ui.addField("Роль:", member.getRoles()
                        .stream()
                        .sorted(Comparator.comparingInt(Role::getPositionRaw).reversed())
                        .findFirst()
                        .orElse(null)
                        .getAsMention()
                , false);

        User.Profile profile = member.getUser().retrieveProfile().complete();
        Optional<ImageProxy> banner = Optional.ofNullable(profile.getBanner());

        banner.ifPresent(it -> ui.setImage(it.getUrl(256)));

        //ui.setFooter("© 2022 MiDeev", "https://cdn.discordapp.com/attachments/942520425936719952/979496152607096852/vcat_40.png");
        ui.setFooter("Команду запросил: " + event.getMember().getUser().getAsTag(), event.getMember().getEffectiveAvatarUrl());
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

        if (event.getChannel().getId().equals("941458443749978122") && com.startsWith(UtilLang.PREFIX + "ui") || com.startsWith(UtilLang.PREFIX + "userinfo")) {
            event.getMessage().getTextChannel().sendMessageEmbeds(ui.build()).queue();
        } else if (event.getMessage().getAuthor().getId().equals("421259943123877888") && event.getMessage().getContentDisplay().startsWith(UtilLang.PREFIX + "ui")) {
            event.getMessage().getTextChannel().sendMessageEmbeds(ui.build()).queue();
        }
        Main.DATABASE.insertCommandUsage(
                event.getMember().getIdLong(),
                event.getMessage().getContentDisplay()
        );
    }
}
