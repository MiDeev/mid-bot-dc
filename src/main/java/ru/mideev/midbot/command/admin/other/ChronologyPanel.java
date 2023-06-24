package ru.mideev.midbot.command.admin.other;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.NewsChannel;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.buttons.ButtonStyle;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;
import net.dv8tion.jda.api.interactions.modals.ModalInteraction;

import java.awt.*;

public class ChronologyPanel extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Message message = event.getMessage();
        MessageChannel channel = event.getChannel();
        String channelId = "1051153223098061010";

        if (message.getContentRaw().equals(".his") && channel.getId().equals(channelId)) {
            channel.sendMessage("**Панель управления:**")
                    .setActionRow(Button.of(ButtonStyle.SUCCESS, "start", "ДОБАВИТЬ ЗАПИСЬ"))
                    .queue();
        }
    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        if (event.getComponentId().equals("start")) {

            TextInput title = TextInput.create("title", "Заголовок", TextInputStyle.SHORT)
                    .setPlaceholder("Напишите заголовок")
                    .build();
            TextInput fieldName = TextInput.create("fieldName", "Название поля", TextInputStyle.SHORT)
                    .setPlaceholder("Укажите название поля для Field")
                    .build();

            TextInput body = TextInput.create("body", "Текст", TextInputStyle.PARAGRAPH)
                    .setPlaceholder("Распишите текст")
                    .build();

            TextInput footer = TextInput.create("footer", "Футер", TextInputStyle.SHORT)
                    .setPlaceholder("Поставьте дату")
                    .build();

            TextInput color = TextInput.create("color", "Цвет", TextInputStyle.SHORT)
                    .setPlaceholder("Установите цвет Embed-сообщения")
                    .build();

            Modal modal = Modal.create("history", "Создание хронологии")
                    .addComponents(ActionRow.of(title), ActionRow.of(fieldName), ActionRow.of(body), ActionRow.of(footer), ActionRow.of(color))
                    .build();

            event.replyModal(modal).queue();

        }
    }

    @Override
    public void onModalInteraction(ModalInteractionEvent event) {
        ModalInteraction interaction = event.getInteraction();
        if (interaction.getModalId().equals("history")) {

            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle(interaction.getValue("title").getAsString());
            eb.addField(interaction.getValue("fieldName").getAsString(), interaction.getValue("body").getAsString(), false);
            eb.setFooter("Дата: " + interaction.getValue("footer").getAsString());
            eb.setColor(Color.decode("0x" + interaction.getValue("color").getAsString()));

            String channelId = null;

            if (interaction.getValue("color").getAsString().equals("6075ff")) {
                channelId = "1008753612450713623";
            }
            if (interaction.getValue("color").getAsString().equals("ff9d25")) {
                channelId = "1008753404803305512";
            }
            if (interaction.getValue("color").getAsString().equals("dcdddf")) {
                channelId = "1008753587121311805";
            }
            System.out.println(interaction.getValue("color").getAsString());

            NewsChannel newsChannel = event.getGuild().getNewsChannelById(channelId);
            newsChannel.sendMessageEmbeds(eb.build()).queue();

            event.reply("Готово.").setEphemeral(true).queue();
        }
    }
}
