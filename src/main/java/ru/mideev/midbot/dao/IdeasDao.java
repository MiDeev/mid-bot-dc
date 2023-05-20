package ru.mideev.midbot.dao;

import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.transaction.Transaction;

@Transaction
public interface IdeasDao {

    @SqlUpdate("insert into ideas (snowflake, message_id) values (?, ?)")
    void insertIdea(long snowflake, long messageId);

    @SqlQuery("select snowflake from ideas where message_id = ?")
    long getSnowflakeByMessageId(long messageId);
}
