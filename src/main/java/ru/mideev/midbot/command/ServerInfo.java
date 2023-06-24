package ru.mideev.midbot.command;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.utils.TimeFormat;
import org.jetbrains.annotations.NotNull;
import ru.mideev.midbot.util.UtilLang;

import java.awt.*;

import static ru.mideev.midbot.util.UtilLang.DEFAULT_EMBED_COLOR;

public class ServerInfo extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (event.getName().equals("si")) {
            EmbedBuilder si = new EmbedBuilder();
            si.setColor(Color.decode(DEFAULT_EMBED_COLOR));
            si.setAuthor("Информация о " + event.getGuild().getName(), null, event.getGuild().getIcon().getUrl(128));
            si.addField("Участники:", "<:all_members:1122089047502880809> Всего: **" + event.
                    getGuild().getMemberCount() + "**\n<:peoples:1122246144072876133> Людей: **" + event.
                    getGuild().getMembers().stream().filter(member -> !member.getUser().isBot()).count()
                    + "**\n<:robots:1122245099015917658> Ботов: **" + event.
                    getGuild().getMembers().stream().filter(member -> member.getUser().isBot()).count()

                    + "**\n<:online_members:1122252430608969768> Онлайн: **" + event.
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

            si.addField("Каналы:", "<:all_channels:1122253092486905916> Всего: **" + (event.
                    getGuild().getTextChannels().size() + event.
                    getGuild().getVoiceChannels().size()) + "**\n<:text_channels:1122248383667634337> Текстовых: **" + event.
                    getGuild().getTextChannels().size() + "**\n<:voice_channels:1122248386138099804> Голосовых: **" + event.
                    getGuild().getVoiceChannels().size() + "**\n<:categories:1122248382237380638> Категорий: **" + event.
                    getGuild().getCategories().size() + "**", true);
            si.setImage(event.getGuild().getBanner().getUrl(256));

            String ver = "" + event.getGuild().getVerificationLevel();
            si.addField("Создатель:", "<:server_owner:1122261736674836580> " + UtilLang.ownerTagFormat(event.getGuild().getOwner()), true);

            String moderation = switch (ver) {
                case "NONE" -> "Отсутствует";
                case "LOW" -> "Низкая";
                case "MEDIUM" -> "Средняя";
                case "HIGH" -> "Высокая";
                case "VERY_HIGH" -> "Полная";
                default -> ver;
            };
            si.addField("Модерация:", "<:guild_protection_level:1122273964069224541> " + moderation, true);

            si.addField("Бусты:", "<:guild_boost_level:1122275442003542156> Бустов: **" + event.getGuild().getBoostCount() + "**", true);
            si.addField("ID сервера:", "<:guild_id:1122280709873217677> " + event.getGuild().getId(), true);
            si.addField("Сервер создан:","<:guild_creation_date:1122280421313478738> " + TimeFormat.DATE_LONG.format(event.getGuild().getTimeCreated()), true);
            si.setFooter("© 2023 mideev", "https://cdn.discordapp.com/attachments/942520425936719952/979496152607096852/vcat_40.png");

            if (event.getChannel().getId().equals("941458443749978122")) {
                event.replyEmbeds(si.build()).queue();

            } else if (event.getInteraction().getMember().getId().equals("421259943123877888")) {
                event.replyEmbeds(si.build()).queue();
            }
        }
    }
}
