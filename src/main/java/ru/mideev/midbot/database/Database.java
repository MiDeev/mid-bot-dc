package ru.mideev.midbot.database;

import lombok.Getter;
import org.jdbi.v3.cache.caffeine.CaffeineCachePlugin;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.postgresql.ds.PGSimpleDataSource;
import ru.mideev.midbot.dao.CommandCounterDao;
import ru.mideev.midbot.dao.IdeasDao;
import ru.mideev.midbot.dao.UsersDao;

public class Database {

    private final String host;
    private final int port;
    private final String username;
    private final String password;
    private final String database;

    @Getter
    private Jdbi jdbi;

    public Database(String host, int port, String username, String password, String database) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.database = database;
    }

    public void connect() {
        try {
            PGSimpleDataSource dataSource = new PGSimpleDataSource();

            dataSource.setServerName(host);
            dataSource.setPortNumber(port);
            dataSource.setUser(username);
            dataSource.setPassword(password);
            dataSource.setDatabaseName(database);

            jdbi = Jdbi.create(dataSource);

            jdbi.installPlugin(new SqlObjectPlugin());
            jdbi.installPlugin(new CaffeineCachePlugin());
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public void init() {
        connect();

        jdbi.useTransaction(handle -> {
            handle.execute("create table if not exists commandcounter (" +
                    "snowflake bigint not null," +
                    "command text not null" +
                    ");");

            handle.execute("create table if not exists ideas (" +
                    "snowflake bigint not null," +
                    "message_id bigint not null" +
                    ");");

            jdbi.useExtension(UsersDao.class, UsersDao::createTable);
        });
    }

    public void insertCommandUsage(long snowflake, String command) {
        jdbi.useExtension(CommandCounterDao.class, dao -> dao.insertCommandUsage(snowflake, command));
    }

    public void insertIdea(long snowflake, long messageId) {
        jdbi.useExtension(IdeasDao.class, dao -> dao.insertIdea(snowflake, messageId));
    }

    public long getSnowflakeByMessageId(long messageId) {
        return jdbi.withExtension(IdeasDao.class, dao -> dao.getSnowflakeByMessageId(messageId));
    }

    public int countAllCommandUsages() {
        return jdbi.withExtension(CommandCounterDao.class, CommandCounterDao::countAllCommandUsages);
    }
}
