package ru.mideev.midbot.command;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.utils.ImageProxy;
import org.jetbrains.annotations.NotNull;
import ru.mideev.midbot.Main;
import ru.mideev.midbot.dao.UsersDao;
import ru.mideev.midbot.util.BadgesUtil;
import ru.mideev.midbot.util.LevelUtil;
import ru.mideev.midbot.util.UtilLang;

import java.awt.*;
import java.util.Comparator;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

import static ru.mideev.midbot.util.UtilLang.DEFAULT_EMBED_COLOR;

public class UserInfo extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (event.getName().equals("ui")) {
            String com = event.getInteraction().getCommandString();
            String[] args = com.split(" ");

            Member member = event.getMember();

            OptionMapping optionMapping = event.getInteraction().getOption("участник");

            if (optionMapping != null && optionMapping.getAsMember() != null) {
                member = optionMapping.getAsMember();
            }
            EmbedBuilder eb = new EmbedBuilder();

            if (member != null) {
                Color color = member.getColor();

                if (color != null) {
                    eb.setColor(color);
                }
                eb.addField("Ошибка:", "Указан не верный аргумент.", true);
            }

            if (member == null) return;

            EmbedBuilder ui = new EmbedBuilder();
            ui.setColor(Color.decode(DEFAULT_EMBED_COLOR));

            ui.setAuthor("Информация об участнике: " + UtilLang.memberTagFormat(member), null, member.getEffectiveAvatarUrl());
            ui.setDescription("**Общие сведения:**" + "\n" + "** **");
            ui.addField("Никнейм:", UtilLang.memberTagFormat(member), true);
            ui.addField("Устройство:", UtilLang.clientTypeToString(member.getActiveClients().stream().findFirst().orElse(ClientType.UNKNOWN)), true);
            ui.addField("Статус:", UtilLang.onlineStatusToString(member.getOnlineStatus()), true);
            ui.addField("Дата прибытия:", "<t:" + member.getTimeJoined().toEpochSecond() + ":d> \n" + " (<t:" + member.getTimeJoined().toEpochSecond() + ":R>)", true);
            ui.addField("Дата регистрации:", "<t:" + member.getTimeCreated().toEpochSecond() + ":d> \n" + " (<t:" + member.getTimeCreated().toEpochSecond() + ":R>)", true);
            ui.addField("ID:", member.getId(), false);

            Member finalMember = member;

            Main.DATABASE.getJdbi().useExtension(UsersDao.class, dao -> {
                ru.mideev.midbot.entity.User user = dao.findUserOrCreate(finalMember.getIdLong());
                ui.addField("Опыт:", (user.getExp() + "/" + LevelUtil.getExperience(user.getLevel() + 1)), true);
                ui.addField("Уровень:", Long.toString(user.getLevel()), true);
            });

            ui.addField("Приоритетная роль:", member.getRoles()
                            .stream()
                            .sorted(Comparator.comparingInt(Role::getPositionRaw).reversed())
                            .findFirst()
                            .orElse(null)
                            .getAsMention()
                    , false);

            String badges = BadgesUtil.getUserBadges(member);

            if (!badges.isEmpty()) {
                ui.addField("Значки:", badges, false);
            }

            User.Profile profile = member.getUser().retrieveProfile().complete();
            Optional<ImageProxy> banner = Optional.ofNullable(profile.getBanner());

            banner.ifPresent(it -> ui.setImage(it.getUrl(256)));

            ui.setFooter("Команду запросил: " + UtilLang.userTagFormat(event.getUser()), event.getMember().getEffectiveAvatarUrl());
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

            if (event.getChannel().getId().equals("941458443749978122") && com.toLowerCase(Locale.ROOT).startsWith(UtilLang.PREFIX + "ui") || com.toLowerCase(Locale.ROOT).startsWith(UtilLang.PREFIX + "userinfo")) {
                event.replyEmbeds(ui.build()).queue();
            } else if (event.getInteraction().getCommandString().toLowerCase(Locale.ROOT).startsWith(UtilLang.PREFIX + "ui")) {
                event.replyEmbeds(ui.build()).queue();
            }
        }
    }
}
