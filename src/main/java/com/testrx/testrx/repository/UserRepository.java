package com.testrx.testrx.repository;

import com.testrx.testrx.model.User;
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
 * Класс UserRepository
 *
 * @author Max Ivanov
 * created 28.12.2021
 */
@Slf4j
@Repository
public class UserRepository extends SqlClient {
    private static final String SQL_SELECT_BY_ID = "" +
            "SELECT id, name, favoritesNews FROM user_news WHERE id = ?";
    private static final String SQL_SELECT_LIST = "" +
            "SELECT id, name, favoritesNews FROM user_news";
    private static final String SQL_INSERT = "" +
            "INSERT INTO user_news (name, favoritesNews) VALUES (?, ?)";
    private static final String SQL_UPDATE = "" +
            "UPDATE user_news SET name = ?, favoritesNews = ? WHERE id = ?";
    private static final String SQL_DELETE = "" +
            "DELETE FROM user_news WHERE id = ?";

    protected final static UserMapper USER_MAPPER = new UserMapper();

    // beans
    protected final JdbcTemplate template;

    /**
     * Req-args constructor for Spring DI
     */
    public UserRepository(@Qualifier("news") JdbcTemplate template) {
        this.template = template;
    }

    /**
     * Возвращает запись по идентификатору
     *
     * @param id идентификатор записи
     * @return запрашиваемая запись
     */
    public User getById(int id) {
            return DataAccessUtils.singleResult(
                    template.query(SQL_SELECT_BY_ID, USER_MAPPER, id));
    }

    /**
     * Возвращает список записей
     *
     * @return запрашиваемая запись
     */
    public Single<List<User>> getUserList() {
        return execWithParams(SQL_SELECT_LIST, new JsonArray())
                .map(resultSet -> resultSet.getRows()).toObservable().flatMapIterable(list -> list)
                .map(json -> new User(
                        json.getInteger("id"),
                        json.getString("name"),
                        json.getString("favoritesNews")
                ))
                .toList()
                .doOnSuccess(list -> log.trace("Metadata created: [{}]", list));
    }

    /**
     * Вставка новой записи
     *
     * @param entity новая запись
     */
    public Single<Integer> insert(User entity) {
        return execWithParams(SQL_INSERT,
                new JsonArray().add(entity.getId()).add(entity.getFavoritesNews()).add(entity.getName()))
                .map(resultSet -> resultSet.getRows().get(0).getInteger("id"))
                .doOnSuccess(id -> log.debug("Saved entity=[{}] with id=[{}]", entity, id));
    }

    /**
     * Обновление записи
     *
     * @param entity обновляемая запись
     */
    public Single<Integer> update(User entity) {
        return execWithParams(SQL_UPDATE,
                new JsonArray().add(entity.getId()).add(entity.getFavoritesNews()).add(entity.getName()))
                .map(resultSet -> resultSet.getRows().get(0).getInteger("id"))
                .doOnSuccess(id -> log.debug("Saved entity=[{}] with id=[{}]", entity, id));
    }

    /**
     * Удаление записи
     *
     * @param entity удаляемая запись
     */
    public Single<Integer> delete(User entity) {
        return execWithParams(SQL_DELETE,
                new JsonArray().add(entity.getId())).map(resultSet -> resultSet.getRows().get(0).getInteger("id"))
                .doOnSuccess(id -> log.debug("Saved entity=[{}] with id=[{}]", entity, id));
    }

    /**
     * {@link User} mapper
     */
    protected static class UserMapper implements RowMapper<User> {
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            var entity = new User(
                    rs.getInt("id"),
                    rs.getString("favoritesNews"),
                    rs.getString("name")
            );
            log.trace("UserMapper(): entity = [{}]", entity);
            return entity;
        }
    }
}
