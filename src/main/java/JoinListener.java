import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Objects;


public class JoinListener extends ListenerAdapter {

    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
        String name = event.getUser().getName();
        if (name.contains("!")) {
            event.getMember().modifyNickname(name.replace("!", "")).queue();

        }

        EmbedBuilder emb = new EmbedBuilder();
        emb.setColor(new Color(103, 236, 129));
        emb.setDescription("**" + event.getUser().getAsTag() + "** (<@" + event.getMember().getUser().getId() + ">)" + " присоединился к серверу.");

        event.getGuild().getTextChannels().stream().filter(textChannel -> textChannel.getId().equals("942516483223846964"))
                .forEach(textChannel -> textChannel.sendMessageEmbeds(emb.build()).queue());

        emb.setColor(new Color(136, 165, 255));
        emb.setDescription("**Привет, <@" + event.getMember().getUser().getId() + ">**! " + "Добро пожаловать на сервер **MiDeev's Brothers**. " +
                "\nНастоятельно рекомендуем прочитать <#941321405172162611>. \n**Весёлого времяпрепровождения!**");

        event.getGuild().getTextChannels().stream().filter(textChannel -> textChannel.getId().equals("941334996654911488"))
                .forEach(textChannel -> textChannel.sendMessageEmbeds(emb.build()).queue());


        event.getMember().getGuild().addRoleToMember(event.getMember(), event.getMember().getGuild().getRolesByName("BRO", false).get(0)).queue();
    }


    @Override
    public void onGuildMemberRemove(@NotNull GuildMemberRemoveEvent event) {

        EmbedBuilder emba = new EmbedBuilder();
        emba.setColor(new Color(255, 98, 98));

        String ti = Objects.requireNonNull(event.getMember()).getTimeJoined()
                .atZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime()
                .format(DateTimeFormatter.ofPattern("yyyy-dd-MM HH:mm:ss"));

        SimpleDateFormat format = new SimpleDateFormat("yyyy-dd-MM HH:mm:ss");

        long timeUp = 0;
        try {
            timeUp = format.parse(ti).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long diff = System.currentTimeMillis() - timeUp;

        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;
        long diffDays = diff / (24 * 60 * 60 * 1000);

        StringBuilder sb = new StringBuilder();

        sb.append(diffDays + " дней, ");
        sb.append(diffHours + " часов, ");
        sb.append(diffMinutes + " минут, ");
        sb.append(diffSeconds + " секунд");

        emba.setDescription("**" + event.getUser().getAsTag() + "** (<@" + event.getUser().getId() + ">)" + " покинул сервер." +
                "\n\nПробыл на сервере: **" + sb + "**");

        event.getGuild().getTextChannels().stream().filter(textChannel -> textChannel.getId().equals("942516483223846964"))
                .forEach(textChannel -> textChannel.sendMessageEmbeds(emba.build()).queue());
    }
}