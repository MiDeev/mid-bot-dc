package ru.mideev.midbot.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.beans.ConstructorProperties;

@Data
@AllArgsConstructor(onConstructor_ = @ConstructorProperties({"snowflake", "exp", "level"}))
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {

    long snowflake;
    long exp;
    long level;

}
