package ru.mideev.midbot.database;

import org.postgresql.ds.PGSimpleDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Database {

    private final String host;
    private final int port;
    private final String username;
    private final String password;
    private final String database;

    private Connection connection;

    public Database(String host, int port, String username, String password, String database) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.database = database;
    }

    public Connection getConnection() {
        return connection;
    }

    public void connect() {
        try {
            PGSimpleDataSource dataSource = new PGSimpleDataSource();

            dataSource.setServerName(host);
            dataSource.setPortNumber(port);
            dataSource.setUser(username);
            dataSource.setPassword(password);
            dataSource.setDatabaseName(database);

            connection = dataSource.getConnection();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public void init() {
        connect();

        try (Statement statement = connection.createStatement()) {
            statement.addBatch("create table if not exists `commandcounter` (" +
                    "`snowflake` bigint not null," +
                    "`command` text not null" +
                    ");");

            statement.addBatch("create table if not exists `ideas` (" +
                    "`snowflake` bigint not null," +
                    "`message_id` bigint not null" +
                    ");");

            statement.executeBatch();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public void insertCommandUsage(long snowflake, String command) {
        try (PreparedStatement statement = connection.prepareStatement(
                "insert into `commandcounter` (snowflake, command) values (?, ?)"
        )) {
            statement.setLong(1, snowflake);
            statement.setString(2, command);

            statement.executeUpdate();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public void insertIdea(long snowflake, long messageId) {
        try (PreparedStatement statement = connection.prepareStatement(
                "insert into `ideas` (snowflake, message_id) values (?, ?)"
        )) {
            statement.setLong(1, snowflake);
            statement.setLong(2, messageId);

            statement.executeUpdate();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public long getSnowflakeByMessageId(long messageId) {
        try (PreparedStatement statement = connection.prepareStatement(
                "select * from `ideas` where message_id = ?"
        )) {
            statement.setLong(1, messageId);

            ResultSet resultSet = statement.executeQuery();

            resultSet.next();

            return resultSet.getLong(1);
        } catch (Throwable t) {
            t.printStackTrace();
            return 0;
        }
    }

    public int countAllCommandUsages() {
        try (PreparedStatement statement = connection.prepareStatement(
                "select count(*) from `commandcounter`"
        )) {
            ResultSet resultSet = statement.executeQuery();

            resultSet.next();

            return resultSet.getInt(1);
        } catch (Throwable t) {
            t.printStackTrace();
            return 0;
        }
    }
}
