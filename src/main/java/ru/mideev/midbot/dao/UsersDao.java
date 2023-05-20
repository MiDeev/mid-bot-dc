package ru.mideev.midbot.dao;

import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.transaction.Transaction;
import ru.mideev.midbot.entity.User;

import java.util.Optional;

@Transaction
public interface UsersDao {

    @SqlUpdate("create table if not exists users (snowflake bigint not null primary key, exp bigint not null, level bigint not null);")
    void createTable();

    @SqlQuery("select * from users where snowflake = ?;")
    @RegisterConstructorMapper(User.class)
    User findUser(long snowflake);

    @SqlUpdate("insert into users (snowflake, exp, level) values (:snowflake, :exp, :level) on conflict (snowflake) do update set exp = :exp, level = :level;")
    void saveUser(@BindBean User user);

    default User findUserOrCreate(long snowflake) {
        return Optional.ofNullable(findUser(snowflake)).orElse(new User(snowflake, 0, 0));
    }
}
