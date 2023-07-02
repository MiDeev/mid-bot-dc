package ru.mideev.midbot.handler;

import lombok.SneakyThrows;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.concrete.ThreadChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
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
import ru.mideev.midbot.Main;
import ru.mideev.midbot.dao.AnswersBranchesDao;
import ru.mideev.midbot.util.UtilLang;

import java.awt.*;
import java.time.Instant;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SurveyHandler extends ListenerAdapter {
    public static final String CHANNEL_ID = "1013527821085315082";
    public static final String BUTTON_ID = "answer";

    Button button = Button.of(ButtonStyle.PRIMARY, BUTTON_ID, "Ответить");
    Pattern pattern = Pattern.compile("\"([^\"]*)\"");

    @SneakyThrows
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (!(event.getChannel() instanceof TextChannel channel)) return;

        Message message = event.getMessage();
        String content = message.getContentRaw();

        if (!content.startsWith(".q"))
            return;

        if (!channel.getId().equals(CHANNEL_ID)) {
            return;
        }

        Matcher matcher = pattern.matcher(content);

        if (matcher.end() != 3) {
            channel.sendMessage("Неверный формат команды. Используйте: .q \"Вопрос\" \"Заголовок\" \"Пример отввета\"").queue();
            return;
        }

        int args_count = matcher.end();

        String[] params = new String[matcher.end()];

        while (matcher.find()) params[args_count++ - 1] = matcher.group(1);

        Main.DATABASE.getJdbi().useExtension(AnswersBranchesDao.class, dao -> {
            long oldId = dao.getOldId();
            if (oldId == 0) return;

            Message message1 = channel.retrieveMessageById(oldId).submit().get();
            if (!message1.getAuthor().isBot()) return;

            var eb = new EmbedBuilder()
                    .setColor(Color.decode("0xdcddde"))
                    .setAuthor("MiBrothers спрашивает:", null, "https://cdn.discordapp.com/attachments/942520425936719952/1120044046983893155/mideev.gif")
                    .setDescription("### " + dao.getParam0())
                    .setTimestamp(Instant.now()).build();

            message1.editMessageEmbeds().setEmbeds(eb).setActionRow(button.asDisabled()).queue();
        });

        Modal.create("answer", params[0])
                .addComponents(ActionRow.of(TextInput.create("body", params[1], TextInputStyle.PARAGRAPH)
                        .setPlaceholder(params[2])
                        .setMinLength(10)
                        .setMaxLength(1000)
                        .build()))
                .build();

        var eb = new EmbedBuilder()
                .setColor(Color.decode("0xdcddde"))
                .setAuthor("MiBrothers спрашивает:", null, "https://cdn.discordapp.com/attachments/942520425936719952/1120044046983893155/mideev.gif")
                .setDescription("### " + params[0])
                .setTimestamp(Instant.now()).build();

        Message message1 = channel.sendMessage("<@&980016919581171763>").addEmbeds(eb).setActionRow(button).submit().get();

        Main.DATABASE.getJdbi().useExtension(AnswersBranchesDao.class, dao -> {
            ThreadChannel threadChannel = channel.createThreadChannel(params[0]).submit().get();
            threadChannel.getManager().setLocked(true).queue();
            threadChannel.sendMessage("**Ответы участников сервера на вопрос \"`%s`\":**".formatted(params[0])).queue();
            dao.setAnswerBranch(threadChannel.getIdLong(), message1.getIdLong(), params[0], params[1], params[2]);
        });

    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        if (!event.getComponentId().equals("answer"))
            return;

        long userId = event.getUser().getIdLong();
        long questionId = event.getMessage().getIdLong();
        if (Main.DATABASE.getJdbi().withExtension(AnswersBranchesDao.class, dao -> dao.hasAnswered(questionId, userId)))
            event.reply("**Вы уже отвечали на этот вопрос!**").setEphemeral(true).queue();
        else {
            Main.DATABASE.getJdbi().useExtension(AnswersBranchesDao.class, dao -> event.replyModal(Modal.create("answer", dao.getParam0())
                    .addComponents(ActionRow.of(TextInput.create("body", dao.getParam1(), TextInputStyle.PARAGRAPH)
                            .setPlaceholder(dao.getParam2())
                            .setMinLength(10)
                            .setMaxLength(1000)
                            .build()))
                    .build()).queue());
        }

    }

    @Override
    public void onModalInteraction(ModalInteractionEvent event) {
        if (event.getModalId().equals("answer")) {
            long answerBranchId = Main.DATABASE.getJdbi().withExtension(AnswersBranchesDao.class, AnswersBranchesDao::getAnswerBranchId);

            button.withDisabled(true);

            String body = Objects.requireNonNull(event.getValue("body")).getAsString();

            var si = new EmbedBuilder()
                    .setColor(Color.decode("0xdcddde"))
                    .setAuthor("Ответ от " + UtilLang.userTagFormat(event.getUser()) + ":", null, event.getUser().getAvatarUrl())
                    .setDescription("### `" + body + "`")
                    .setFooter(event.getUser().getId())
                    .setTimestamp(Instant.now());

            Objects.requireNonNull(Objects.requireNonNull(event.getGuild()).getThreadChannelById(answerBranchId)).sendMessageEmbeds(si.build()).queue(message -> {
                message.addReaction(Emoji.fromUnicode("\uD83D\uDC4D")).queue();
                message.addReaction(Emoji.fromUnicode("\uD83D\uDC4E")).queue();
            });

            event.deferReply().setEphemeral(true).addContent("**Благодарим за ответ!**").submit();

            Main.DATABASE.getJdbi().useExtension(AnswersBranchesDao.class,
                    dao -> dao.addAnswer(Objects.requireNonNull(event.getMessage()).getIdLong(), event.getUser().getIdLong()));
        }
    }
}

