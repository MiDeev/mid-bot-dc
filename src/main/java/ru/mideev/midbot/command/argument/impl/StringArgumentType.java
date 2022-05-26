package ru.mideev.midbot.command.argument.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import ru.mideev.midbot.command.argument.IArgumentType;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class StringArgumentType implements IArgumentType<String> {

    private final StringType type;

    public static StringArgumentType string(StringType type) {
        return new StringArgumentType(type);
    }

    @Override
    public String parse(String[] args, int currentIndex) {
        switch (type) {
            case WORD:
                return args[currentIndex];
            case GREEDY:
                int len = args.length - currentIndex + 1;
                String[] copy = new String[len];
                System.arraycopy(args, currentIndex, copy, 0, len);
                return String.join(" ", copy);
            case QUOTED:
                throw new UnsupportedOperationException();
            default:
                throw new IllegalArgumentException();
        }
    }

    private enum StringType {
        GREEDY,
        WORD,
        QUOTED
    }
}
