package ru.mideev.midbot.dao;

import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.transaction.Transaction;

@Transaction
public interface CommandCounterDao {

    @SqlUpdate("insert into commandcounter (snowflake, command) values (?, ?)")
    void insertCommandUsage(long snowflake, String command);

    @SqlQuery("select count(*) from commandcounter")
    int countAllCommandUsages();
}
