package com.testrx.testrx.repository;

import com.testrx.testrx.model.News;
import io.reactivex.Single;
import io.vertx.core.json.JsonArray;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Класс NewsRepository
 *
 * @author Max Ivanov
 * created 27.12.2021
 */
@Slf4j
@Repository
public class NewsRepository extends SqlClient {
    private static final String SQL_SELECT_BY_ID = "" +
            "SELECT id, newsnum, name FROM news WHERE id = ?";
    private static final String SQL_SELECT_LIST = "" +
            "SELECT id, newsnum, name FROM news";
    private static final String SQL_INSERT = "" +
            "INSERT INTO news (newsnum, name) VALUES (?, ?)";
    private static final String SQL_UPDATE = "" +
            "UPDATE news SET newsnum = ?, name = ? WHERE id = ?";
    private static final String SQL_DELETE = "" +
            "DELETE FROM news WHERE id = ?";

    protected final static NewsMapper NEWS_MAPPER = new NewsMapper();

    // beans
    protected final JdbcTemplate template;


    /**
     * Req-args constructor for Spring DI
     */
    public NewsRepository(@Qualifier("news") JdbcTemplate template) {
        this.template = template;
    }

    /**
     * Возвращает запись по идентификатору
     *
     * @param id идентификатор записи
     * @return запрашиваемая запись
     */
    public News getById(int id) {
        return DataAccessUtils.singleResult(
                template.query(SQL_SELECT_BY_ID, NEWS_MAPPER, id));
    }

    /**
     * Возвращает список записей
     *
     * @return запрашиваемая запись
     */
    public Single<List<News>> getNewsList() {
        return execWithParams(SQL_SELECT_LIST, new JsonArray())
                .map(resultSet -> resultSet.getRows()).toObservable().flatMapIterable(list -> list)
                .map(json -> new News(
                        json.getInteger("id"),
                        json.getString("name"),
                        json.getString("newsnum")
                ))
                .toList()
                .doOnSuccess(list -> log.trace("Metadata created: [{}]", list));
    }

    /**
     * Вставка новой записи
     *
     * @param entity новая запись
     */
    public Single<Integer> insert(News entity) {
        return execWithParams(SQL_INSERT,
                new JsonArray().add(entity.getId()).add(entity.getNewsNum()).add(entity.getName()))
                .map(resultSet -> resultSet.getRows().get(0).getInteger("id"))
                .doOnSuccess(id -> log.debug("Saved entity=[{}] with id=[{}]", entity, id));
    }

    /**
     * Обновление записи
     *
     * @param entity обновляемая запись
     */
    public Single<Integer> update(News entity) {
        return execWithParams(SQL_UPDATE,
                new JsonArray().add(entity.getId()).add(entity.getNewsNum()).add(entity.getName()))
                .map(resultSet -> resultSet.getRows().get(0).getInteger("id"))
                .doOnSuccess(id -> log.debug("Saved entity=[{}] with id=[{}]", entity, id));
    }

    /**
     * Удаление записи
     *
     * @param entity удаляемая запись
     */
    public Single<Integer> delete(News entity) {
        return execWithParams(SQL_DELETE,
                new JsonArray().add(entity.getId())).map(resultSet -> resultSet.getRows().get(0).getInteger("id"))
                .doOnSuccess(id -> log.debug("Saved entity=[{}] with id=[{}]", entity, id));
    }

    /**
     * {@link News} mapper
     */
    protected static class NewsMapper implements RowMapper<News> {
        public News mapRow(ResultSet rs, int rowNum) throws SQLException {
            var entity = new News(
                    rs.getInt("id"),
                    rs.getString("newsnum"),
                    rs.getString("name")
            );
            log.trace("NewsMapper(): entity = [{}]", entity);
            return entity;
        }
    }
}
