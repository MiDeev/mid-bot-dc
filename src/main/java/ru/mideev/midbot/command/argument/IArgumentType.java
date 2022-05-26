package ru.mideev.midbot.command.argument;

@FunctionalInterface
public interface IArgumentType<T> {

    T parse(String[] args, int currentIndex);
}
