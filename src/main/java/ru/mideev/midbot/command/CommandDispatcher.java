package ru.mideev.midbot.command;

import lombok.Getter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.reflections.Reflections;
import ru.mideev.midbot.command.exception.CommandException;
import ru.mideev.midbot.command.filter.IFilter;
import ru.mideev.midbot.util.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Getter
public class CommandDispatcher {

    public static final CommandDispatcher INSTANCE = new CommandDispatcher();

    private final List<AbstractCommand> commands = new ArrayList<>();

    public void register(AbstractCommand command) {
        commands.add(command);
    }

    public void registerAll() {
        Reflections reflections = new Reflections();
        reflections.getSubTypesOf(AbstractCommand.class).forEach(commandClass -> {
            try {
                register(commandClass.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }

    public void fallback(MessageReceivedEvent event) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(Objects.requireNonNull(event.getMember()).getColor());
        eb.addField("Ошибка!", "Команда не найдена", true);
        event.getMessage().getTextChannel().sendMessageEmbeds(eb.build()).queue();
        event.getMessage().delete().queueAfter(3, TimeUnit.SECONDS);
    }

    public void dispatch(MessageReceivedEvent event) {
        String command = event.getMessage().getContentDisplay();

        if (command.startsWith(Constants.COMMAND_PREFIX)) {
            String commandWithoutPrefix = command.substring(1);
            String[] rawArgs = commandWithoutPrefix.split(" ");
            String commandName = rawArgs[0];
            String[] args = new String[rawArgs.length - 1];
            System.arraycopy(rawArgs, 1, args, 0, args.length);

            for (AbstractCommand abstractCommand : commands) {
                if (abstractCommand.getName().contains(commandName.toLowerCase())) {
                    for (IFilter filter : abstractCommand.filters) {
                        if (!filter.test(event)) {
                            filter.onError(event);
                            return;
                        }
                    }

                    try {
                        abstractCommand.execute(event, args);
                        return;
                    } catch (CommandException exception) {
                        EmbedBuilder eb = new EmbedBuilder();
                        eb.setColor(Objects.requireNonNull(event.getMember()).getColor());
                        eb.addField("Ошибка!", exception.getMessage(), true);
                        event.getMessage().getTextChannel().sendMessageEmbeds(eb.build()).queue();
                        event.getMessage().delete().queueAfter(3, TimeUnit.SECONDS);
                        return;
                    }
                }
            }

            fallback(event);
        }

    }
}
