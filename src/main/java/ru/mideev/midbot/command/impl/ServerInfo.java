package ru.mideev.midbot.command.impl;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.utils.TimeFormat;
import org.jetbrains.annotations.NotNull;
import ru.mideev.midbot.command.AbstractCommand;
import ru.mideev.midbot.command.filter.FilterChannel;
import ru.mideev.midbot.command.filter.FilterChannelWithMessage;
import ru.mideev.midbot.util.Constants;

import java.awt.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static ru.mideev.midbot.util.Constants.FLOOD_CHANNEL_ID;

public class ServerInfo extends AbstractCommand {

    private static final Map<String, String> PROTECTION_MAPPINGS = new HashMap<String, String>() {
        {
            put("NONE", "Отсутствует");
            put("LOW", "Низкая");
            put("MEDIUM", "Средняя");
            put("HIGH", "Высокая");
            put("VERY_HIGH", "Полная");
        }
    };

    public ServerInfo() {
        super("si", "Информация о сервере", true);
        filters.add(new FilterChannelWithMessage(Constants.FLOOD_CHANNEL_ID, "<@$AUTHOR_ID>" + " все команды доступны в <#" + FLOOD_CHANNEL_ID + ">", Collections.singletonList("421259943123877888")));
    }

    @Override
    public boolean execute(MessageReceivedEvent event, String[] args) {
        EmbedBuilder si = new EmbedBuilder();
        si.setColor(new Color(141, 127, 254));

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

        String ver = event.getGuild().getVerificationLevel().name();

        si.addField("Создатель:", "<:developer:950745056409686049>" + event.getGuild().getOwner().getUser().getAsTag(), true);
        si.addField("Модерация:", "<:protection:950746378538205244>" + PROTECTION_MAPPINGS.getOrDefault(ver, ver), true);
        si.addField("Бусты:", "<:boost:950748442458742864> Бустов: **" + event.getGuild().getBoostCount() + "**", true);
        si.addField("ID сервера:", event.getGuild().getId(), true);
        si.addField("Сервер создан:", TimeFormat.DATE_LONG.format(event.getGuild().getTimeCreated()), true);
        si.setFooter("© 2022 MiDeev", "https://cdn.discordapp.com/avatars/789218753576566855/a0a96460803e6cc6e27e7023d07d0bba.webp?size=128");

        event.getMessage().getTextChannel().sendMessageEmbeds(si.build()).queue();
        return true;
    }
}
