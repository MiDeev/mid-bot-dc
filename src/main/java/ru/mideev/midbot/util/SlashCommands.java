package ru.mideev.midbot.util;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.utils.ImageProxy;
import net.dv8tion.jda.api.utils.TimeFormat;
import org.jetbrains.annotations.NotNull;
import ru.mideev.midbot.Main;

import java.awt.*;
import java.util.*;
import java.util.List;

public class SlashCommands extends ListenerAdapter {
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

            String date = DataUtil.formatDate(member);

            EmbedBuilder ui = new EmbedBuilder();
            ui.setColor(new Color(95, 129, 255));

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

            if (event.getChannel().getId().equals("941458443749978122") && com.toLowerCase(Locale.ROOT).startsWith(UtilLang.PREFIX + "ui") || com.toLowerCase(Locale.ROOT).startsWith(UtilLang.PREFIX + "userinfo")) {
                event.replyEmbeds(ui.build()).queue();
            } else if (event.getInteraction().getCommandString().toLowerCase(Locale.ROOT).startsWith(UtilLang.PREFIX + "ui")) {
                event.replyEmbeds(ui.build()).queue();
            }

        }

        if (event.getName().equals("ava")) {

            String net = event.getInteraction().getCommandString();

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

            EmbedBuilder av = new EmbedBuilder();
            av.setColor(new Color(95, 129, 255));

            av.setDescription("**Аватар пользователя: **" + "<@" + member.getId() + ">");

            if (member.getUser().getAvatar() == null) {
                av.setImage(member.getUser().getEffectiveAvatarUrl());
            } else av.setImage(member.getUser().getAvatar().getUrl(512));

            av.setFooter("Команду запросил: " + event.getMember().getUser().getAsTag(), event.getMember().getEffectiveAvatarUrl());

            if (event.getChannel().getId().equals("941458443749978122") && net.startsWith(UtilLang.PREFIX + "ava") || net.startsWith(UtilLang.PREFIX + "avatar")) {
                event.replyEmbeds(av.build()).queue();
            } else if (event.getInteraction().getMember().getId().equals("421259943123877888") && event.getInteraction().getCommandString().startsWith(UtilLang.PREFIX + "ava")) {
                event.replyEmbeds(av.build()).queue();
            }
        }

        if (event.getName().equals("banner")) {

            String net = event.getInteraction().getCommandString();

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

            EmbedBuilder ba = new EmbedBuilder();
            ba.setColor(new Color(95, 129, 255));

            User.Profile profile = member.getUser().retrieveProfile().complete();
            Optional<ImageProxy> banner = Optional.ofNullable(profile.getBanner());

            if (profile.getBanner() == null) {
                ba.setColor(new Color(252, 80, 80));
                ba.setDescription("**У данного участника отсутствует баннер.**");
                ba.setFooter("Команду запросил: " + event.getMember().getUser().getAsTag(), event.getMember().getEffectiveAvatarUrl());
            } else {
                ba.setDescription("**Баннер пользователя: **" + "<@" + member.getId() + ">");
                banner.ifPresent(it -> ba.setImage(it.getUrl(512)));
                ba.setFooter("Команду запросил: " + event.getMember().getUser().getAsTag(), event.getMember().getEffectiveAvatarUrl());
            }

            if (event.getChannel().getId().equals("941458443749978122") && net.startsWith(UtilLang.PREFIX + "banner") || net.startsWith(UtilLang.PREFIX + "banner")) {
                event.replyEmbeds(ba.build()).queue();
            } else if (event.getInteraction().getMember().getId().equals("421259943123877888") && event.getInteraction().getCommandString().startsWith(UtilLang.PREFIX + "banner")) {
                event.replyEmbeds(ba.build()).queue();
            }
        }

        if (event.getName().equals("si")) {
            EmbedBuilder si = new EmbedBuilder();
            si.setColor(new Color(95, 129, 255));

            si.setTitle("Информация о " + event.getGuild().getName());

            si.addField("Участники:", "<:all_members:949778978913267713> Всего: **" + event.
                    getGuild().getMemberCount() + "**\n<:people:949778979169107978> Людей: **" + event.
                    getGuild().getMembers().stream().filter(member -> !member.getUser().isBot()).count()
                    + "**\n<:bot:949778978737127445> Ботов: **" + event.
                    getGuild().getMembers().stream().filter(member -> member.getUser().isBot()).count()

                    + "**\n<:total_online:949943607236915210> Онлайн: **" + event.
                    getGuild().getMembers().stream().filter(member -> !member
                            .getOnlineStatus().equals(OnlineStatus.OFFLINE)).count() + "**", true);

            si.addField("Статусы:", "<:online:949719753822437468> В сети: **" + event.
                    getGuild().getMembers().stream().filter(member -> member
                            .getOnlineStatus().equals(OnlineStatus.ONLINE)).count() + "**\n<:idle:949719753763717170> Не активно: **" + event.
                    getGuild().getMembers().stream().filter(member -> member
                            .getOnlineStatus().equals(OnlineStatus.IDLE)).count() + "**\n<:dnd:949719753784701048> Не беспокоить: **" + event.
                    getGuild().getMembers().stream().filter(member -> member
                            .getOnlineStatus().equals(OnlineStatus.DO_NOT_DISTURB)).count() + "**\n<:offline:949719753390428161> Не в сети: **" + event.
                    getGuild().getMembers().stream().filter(member -> member
                            .getOnlineStatus().equals(OnlineStatus.OFFLINE)).count() + "**", true);

            si.addField("Каналы:", "<:all_channels:949777348474056714> Всего: **" + (event.
                    getGuild().getTextChannels().size() + event.
                    getGuild().getVoiceChannels().size()) + "**\n<:text_channel:949776974287609876> Текстовых: **" + event.
                    getGuild().getTextChannels().size() + "**\n<:voice_channel:949775496382664765> Голосовых: **" + event.
                    getGuild().getVoiceChannels().size() + "**\n<:category:949941456607526912> Категорий: **" + event.
                    getGuild().getCategories().size() + "**", true);

            String s = String.valueOf(event.getGuild().getBoostTier());

            String ver = "" + event.getGuild().getVerificationLevel();

            si.addField("Создатель:", "<:developer:950745056409686049>" + event.getGuild().getOwner().getUser().getAsTag(), true);

            switch (ver) {
                case "NONE": {
                    String kek = ver.replace("NONE", "Отсутствует");
                    si.addField("Модерация:", "<:protection:950746378538205244>" + kek, true);
                    break;
                }
                case "LOW": {
                    String kek = ver.replace("LOW", "Низкая");
                    si.addField("Модерация:", "<:protection:950746378538205244>" + kek, true);
                    break;
                }
                case "MEDIUM": {
                    String kek = ver.replace("MEDIUM", "Средняя");
                    si.addField("Модерация:", "<:protection:950746378538205244>" + kek, true);
                    break;
                }
                case "HIGH": {
                    String kek = ver.replace("HIGH", "Высокая");
                    si.addField("Модерация:", "<:protection:950746378538205244>" + kek, true);
                    break;
                }
                case "VERY_HIGH": {
                    String kek = ver.replace("VERY_HIGH", "Полная");
                    si.addField("Модерация:", "<:protection:950746378538205244>" + kek, true);
                    break;
                }
                default:
                    si.addField("Модерация:", "<:protection:950746378538205244>" + ver, true);
                    break;
            }

            si.addField("Бусты:", "<:boost:950748442458742864> Бустов: **" + event.getGuild().getBoostCount() + "**", true);

            si.addField("ID сервера:", event.getGuild().getId(), true);

            si.addField("Сервер создан:", TimeFormat.DATE_LONG.format(event.getGuild().getTimeCreated()), true);

            si.setFooter("© 2022 MiDeev", "https://cdn.discordapp.com/attachments/942520425936719952/979496152607096852/vcat_40.png");


            if (event.getChannel().getId().equals("941458443749978122")) {
                event.replyEmbeds(si.build()).queue();

            } else if (event.getInteraction().getMember().getId().equals("421259943123877888")) {
                event.replyEmbeds(si.build()).queue();
            }
        }

        if (event.getName().equals("help")) {
            EmbedBuilder eb = new EmbedBuilder();

            eb.setColor(event.getMember().getColor());
            eb.setTitle("Список доступных команд: ");
            eb.setDescription("**Получить информацию о сервере:**\n" +
                    "**`.si`** *`(.server info)`* - отображает информацию о сервере, содержащую в себе: количество каналов, общие данные об участниках, сведения о бустах, \n" +
                    "ник владельца сервера, ID сервера и тому подобное.\n" +
                    "\n" +
                    "**Получить информацию об участнике:**\n" +
                    "**`.ui`** *`(.user info)`* `[ID | @упомнинание]` - отображает информацию об участнике. Принимает в себя аргументы в виде упоминания пользователя через '@' или 'ID'. По умолчанию отображает сведения об участнике, вызвавшего команду.\n" +
                    "\n" +
                    "**Получить аватар участника:**\n" +
                    "**`.ava`** *`(.avatar)`* `[ID | @упомнинание]` - показывает аватар участника. Можно получить аватар любого из участников, указывая в аргументах его 'ID' или упомянув его через '@'.  По умолчанию отобразит аватар, вызвавшего команду пользователя.\n" +
                    "\n" +
                    "**Получить баннер участника:**\n" +
                    "**`.banner`**  `[ID | @упомнинание]` - позволяет отобразить баннер участника. Получить баннер одного из участников, можно указав в аргументах его 'ID' или упомянув его через '@'.  По умолчанию отображает баннер, вызвавшего команду пользователя.");
            eb.setFooter("© 2022 MiDeev", "https://cdn.discordapp.com/attachments/942520425936719952/979496152607096852/vcat_40.png");

            if (event.getChannel().getId().equals("941458443749978122") && event.getInteraction().getCommandString().toLowerCase(Locale.ROOT).equals(UtilLang.PREFIX + "help")) {
                event.replyEmbeds(eb.build()).queue();

            } else if (event.getInteraction().getMember().getId().equals("421259943123877888") && event.getInteraction().getCommandString().startsWith(UtilLang.PREFIX + "help")) {
                event.replyEmbeds(eb.build()).queue();
            }
        }
    }
}