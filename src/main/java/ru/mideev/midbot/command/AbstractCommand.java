package ru.mideev.midbot.command;

import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.mideev.midbot.command.filter.IFilter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Data
public abstract class AbstractCommand implements ICommand {

    private final List<String> name;
    private final String description;
    private final boolean visible;

    protected final List<IFilter> filters = new ArrayList<>();

    protected AbstractCommand(String name, String description, boolean visible) {
        this(Collections.singletonList(name), description, visible);
    }
}
