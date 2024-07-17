package ru.mideev.midbot.dao;

import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.transaction.Transaction;
import ru.mideev.midbot.entity.User;

import java.util.List;
import java.util.Optional;

@Transaction
public interface UsersDao {

    @SqlUpdate("create table if not exists users (snowflake bigint not null primary key, exp bigint not null, level bigint not null, nickname varchar(128), voice bigint not null default 0);")
    void createTable();

    @SqlQuery("select * from users where snowflake = ?;")
    @RegisterConstructorMapper(User.class)
    User findUser(long snowflake);

    @SqlUpdate("insert into users (snowflake, exp, level, nickname, voice) values (:snowflake, :exp, :level, :nickname, :voice) on conflict (snowflake) do update set exp = :exp, level = :level, voice = :voice;")
    void saveUser(@BindBean User user);

    default User findUserOrCreate(long snowflake) {
        return Optional.ofNullable(findUser(snowflake)).orElse(new User(snowflake, 0, 0, "", 0));
    }

    @SqlQuery("SELECT COUNT (*) FROM users where exp > 0")
    int getTotalUsers();

    @SqlQuery("select * from users where exp > 0 order by exp desc limit 10 offset :offset")
    @RegisterConstructorMapper(User.class)
    List<User> getExpLeaders(@Bind("offset") int offset);

}
