package com.testrx.testrx.repository;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.sql.ResultSet;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.ext.jdbc.JDBCClient;
import io.vertx.reactivex.ext.sql.SQLClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Класс SqlClient
 *
 * @author Max Ivanov
 * created 29.12.2021
 */

@Slf4j
@RequiredArgsConstructor
public class SqlClient {

    // locals
    private SQLClient sqlClient;

    public SqlClient(Vertx vertx) {
        sqlClient = JDBCClient.create(
                vertx,
                new JsonObject()
                        .put("url", "jdbc:postgresql://localhost:5432/sa")
                        .put("driver_class", "org.postgresql.Driver")
                        .put("user", "sa")
                        .put("password", "sa")
                        .put("max_pool_size", 5)
                        .put("max_idle_time", 3000));
    }


    /**
     * Выполнение SQL
     *
     * @param sql SQL для запроса
     */
    public Completable exec(String sql) {
        return sqlClient
                .rxGetConnection()
                .flatMapCompletable(connection -> connection.rxExecute(sql)
                        .doOnEvent(error -> connection.close()));
    }


    /**
     * Выполнение SQL с параметрами
     *
     * @param sql    SQL для запроса
     * @param params параметры запроса SQL
     */
    public Single<ResultSet> execWithParams(String sql, JsonArray params) {
        return sqlClient
                .rxGetConnection()
                .flatMap(connection -> connection.rxQueryWithParams(sql, params)
                        .doOnEvent((event, error) -> connection.close()));
    }

}
