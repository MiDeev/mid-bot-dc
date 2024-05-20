package ru.mideev.midbot.handler;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SurveyHandler extends ListenerAdapter {
    public static final String CHANNEL_ID = "1013527821085315082";
    public static final String BUTTON_ID = "answer";

    Button button = Button.of(ButtonStyle.PRIMARY, BUTTON_ID, "Ответить");
    Pattern pattern = Pattern.compile("\"([^\"]*)\"");

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (!(event.getChannel() instanceof TextChannel)) return;

        Message message = event.getMessage();
        String content = message.getContentRaw();
        TextChannel channel = (TextChannel) event.getChannel();

        EmbedBuilder eb = new EmbedBuilder();

        if (content.startsWith(".q") && channel.getId().equals(CHANNEL_ID)) {
            int count = 0;

            Matcher matcher = pattern.matcher(content);

            String[] params = new String[3];

            while (matcher.find()) {
                count++;
                params[count - 1] = matcher.group(1);
            }

            if (count != 3) {
                channel.sendMessage("Неверный формат команды. Используйте: .q \"Вопрос\" \"Заголовок\" \"Пример отввета\"").queue();
                return;
            }

            try {
                Main.DATABASE.getJdbi().useExtension(AnswersBranchesDao.class, dao -> {
                    long oldId = dao.getOldId();
                    if (oldId == 0) return;
                    Message message1 = channel.retrieveMessageById(oldId).submit().get();
                    if (!message1.getAuthor().isBot()) return;
                    eb.setColor(Color.decode("0xdcddde"));
                    eb.setAuthor("MiBrothers спрашивает:", null, "https://cdn.discordapp.com/attachments/942520425936719952/1120044046983893155/mideev.gif");
                    eb.setDescription("### " + dao.getParam0());
                    eb.setTimestamp(Instant.now());
                    message1.editMessageEmbeds().setEmbeds(eb.build()).setActionRow(button.asDisabled()).queue();
                });
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            Modal.create("answer", params[0])
                    .addComponents(ActionRow.of(TextInput.create("body", params[1], TextInputStyle.PARAGRAPH)
                            .setPlaceholder(params[2])
                            .setMinLength(10)
                            .setMaxLength(1000)
                            .build()))
                    .build();

            try {
                eb.setColor(Color.decode("0xdcddde"));
                eb.setAuthor("MiBrothers спрашивает:", null, "https://cdn.discordapp.com/attachments/942520425936719952/1120044046983893155/mideev.gif");
                eb.setDescription("### " + params[0]);
                eb.setTimestamp(Instant.now());
                Message message1 = channel.sendMessage("<@&980016919581171763>").addEmbeds(eb.build()).setActionRow(button).submit().get();
                Main.DATABASE.getJdbi().useExtension(AnswersBranchesDao.class, dao -> {
                    ThreadChannel threadChannel = channel.createThreadChannel(params[0]).submit().get();
                    threadChannel.getManager().setLocked(true).queue();
                    threadChannel.sendMessage("**Ответы участников сервера на вопрос \"`" + params[0] + "`\":**").queue();
                    dao.setAnswerBranch(threadChannel.getIdLong(), message1.getIdLong(), params[0], params[1], params[2]);
                });
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        if (event.getComponentId().equals("answer")) {
            long userId = event.getUser().getIdLong();
            long questionId = event.getMessage().getIdLong();
            boolean hasAnswered = Main.DATABASE.getJdbi().withExtension(AnswersBranchesDao.class, dao -> dao.hasAnswered(questionId, userId));
            if (hasAnswered) {
                event.reply("**Вы уже отвечали на этот вопрос!**").setEphemeral(true).queue();
            } else {
                Main.DATABASE.getJdbi().useExtension(AnswersBranchesDao.class, dao -> event.replyModal(Modal.create("answer", dao.getParam0())
                        .addComponents(ActionRow.of(TextInput.create("body", dao.getParam1(), TextInputStyle.PARAGRAPH)
                                .setPlaceholder(dao.getParam2())
                                .setMinLength(4)
                                .setMaxLength(1000)
                                .build()))
                        .build()).queue());
            }
        }
    }

    EmbedBuilder si = new EmbedBuilder();

    @Override
    public void onModalInteraction(ModalInteractionEvent event) {
        if (event.getModalId().equals("answer")) {
            long answerBranchId = Main.DATABASE.getJdbi().withExtension(AnswersBranchesDao.class, AnswersBranchesDao::getAnswerBranchId);

            button.withDisabled(true);

            String body = event.getValue("body").getAsString();

            si.setColor(Color.decode("0xdcddde"));
            si.setAuthor("Ответ от " + UtilLang.userNameFormat(event.getUser()) + ":", null, event.getUser().getAvatarUrl());
            si.setDescription("```" + body + "```");
            si.setFooter(event.getUser().getId());
            si.setTimestamp(Instant.now());

            event.getGuild().getThreadChannelById(answerBranchId).sendMessageEmbeds(si.build()).queue(message1 -> {
                message1.addReaction(Emoji.fromUnicode("\uD83D\uDC4D")).queue();
                message1.addReaction(Emoji.fromUnicode("\uD83D\uDC4E")).queue();
            });

            event.deferReply().setEphemeral(true).addContent("**Благодарим за ответ!**").submit();

            Main.DATABASE.getJdbi().useExtension(AnswersBranchesDao.class, dao -> dao.addAnswer(event.getMessage().getIdLong(), event.getUser().getIdLong()));
        }
    }
}

