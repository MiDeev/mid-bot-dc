package ru.mideev.midbot.command.util;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class News extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        EmbedBuilder eb = new EmbedBuilder();

        if (event.getChannel().getId().equals("941364846899900496") && event.getMessage().getContentDisplay().equals(".news")) {
            event.getChannel().sendMessage("**Здравствуйте, дружочки пирожочки, также известные как @everyone**! \n" +
                    "Не поверите, но спустя аж целых **3 МЕСЯЦА** на канале **MiDeev'a** вышло новое видео! \n" +
                    "Вы там не падайте, а если шок всё-таки берёт верх и вы уже начали свободное подение, то успевайте в полёте включить видео, а после уже начинайте просмотр. \n" +
                    "**Мягкого вам приземления и приятного просмотра**!\n" +
                    "\n" +
                    "**__Вот ссылочка кстати:__** https://www.youtube.com/watch?v=wGTb_5E4rJ0").submit();
        }
    }
}
