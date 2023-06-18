package ru.mideev.midbot.dao;

import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.transaction.Transaction;

@Transaction
public interface AnswersBranchesDao {
    @SqlUpdate("create table if not exists answersbranches (snowflake bigint not null, old bigint not null default 0, primary key(snowflake));")
    void createTable();
    @SqlQuery("select snowflake from answersbranches limit 1")
    long getAnswerBranchId();
    @SqlQuery("select old from answersbranches limit 1")
    long getOldId();
    @SqlUpdate("update answersbranches set snowflake = :snowflake, old = :old;")
    void setAnswerBranch(@Bind("snowflake") long snowflake, @Bind("old") long old);
    @SqlUpdate("create table if not exists answers (question_id bigint not null, user_id bigint not null, primary key (question_id, user_id));")
    void createAnswersTable();
    @SqlQuery("select exists (select 1 from answers where question_id = :question_id and user_id = :user_id)")
    boolean hasAnswered(@Bind("question_id") long questionId, @Bind("user_id") long userId);
    @SqlUpdate("insert into answers (question_id, user_id) values (:question_id, :user_id)")
    void addAnswer(@Bind("question_id") long questionId, @Bind("user_id") long userId);
}
