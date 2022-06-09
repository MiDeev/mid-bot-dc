package ru.mideev.midbot.command;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.utils.TimeFormat;
import org.jetbrains.annotations.NotNull;
import ru.mideev.midbot.Main;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class ServerInfo extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (!event.isFromGuild()) {
            System.out.println(event.getAuthor().getAsTag() + ": " + event.getMessage().getContentDisplay());
            return;
        }

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


        if (event.getChannel().getId().equals("941458443749978122") && event.getMessage().getContentDisplay().equals(".si") || event.getMessage().getContentDisplay().equals(".server info")) {
            event.getGuild().getTextChannels().stream().filter(textChannel -> textChannel.getId().equals("941458443749978122"))
                    .forEach(textChannel -> textChannel.sendMessageEmbeds(si.build()).queue());

            Main.DATABASE.insertCommandUsage(
                    event.getMember().getIdLong(),
                    event.getMessage().getContentDisplay()
            );
        } else if (event.getMessage().getAuthor().getId().equals("421259943123877888") && event.getMessage().getContentDisplay().equals(".si")) {
            event.getMessage().getTextChannel().sendMessageEmbeds(si.build()).queue();
        }

        String[] args = event.getMessage().getContentRaw().split(" ");

        EmbedBuilder eb = new EmbedBuilder();

        if (event.getMessage().getContentDisplay().equals(".si") && event.getChannel().getId().equals("941334996654911488") && !event.getMember().getId().equals("421259943123877888")) {
            eb.setDescription("<@" + event.getMessage().getAuthor().getId() + ">" + " все команды доступны в <#941458443749978122>");
            eb.setColor(event.getMember().getColor());
            event.getMessage().getTextChannel().sendMessageEmbeds(eb.build()).delay(7, TimeUnit.SECONDS).flatMap(Message::delete).queue();
            event.getMessage().delete().queueAfter(3, TimeUnit.SECONDS);
        }
    }
}
