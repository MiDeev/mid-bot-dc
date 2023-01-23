package ru.mideev.midbot.command;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.utils.TimeFormat;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

import static ru.mideev.midbot.util.UtilLang.EMBED_COLOR;

public class ServerInfo extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (event.getName().equals("si")) {
            EmbedBuilder si = new EmbedBuilder();
            si.setColor(Color.decode(EMBED_COLOR));
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
    }
}
