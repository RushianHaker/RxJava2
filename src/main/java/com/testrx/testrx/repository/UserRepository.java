package com.testrx.testrx.repository;

import com.testrx.testrx.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.SQLExceptionSubclassTranslator;
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
public class UserRepository {
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
    public List<User> getUserList() {
            return template.query(SQL_SELECT_LIST, USER_MAPPER);
    }

    /**
     * Вставка новой записи
     *
     * @param entity новая запись
     */
    public void insert(User entity) {
            var result = template.update(SQL_INSERT, entity.getFavoritesNews(), entity.getName());
            if (result != 1) new SQLExceptionSubclassTranslator();
            log.trace("insert({}) result={}", entity, result);
    }

    /**
     * Обновление записи
     *
     * @param entity обновляемая запись
     */
    public void update(User entity) {
        var result = template.update(SQL_UPDATE, entity.getFavoritesNews(), entity.getName(), entity.getId());
        if (result != 1) new SQLExceptionSubclassTranslator();
        log.trace("update({}) result={}", entity, result);
    }

    /**
     * Удаление записи
     *
     * @param entity удаляемая запись
     */
    public void delete(User entity) {
        // в параметры запроса идентификатор
        var result = template.update(SQL_DELETE, entity.getId());
        if (result != 1) new SQLExceptionSubclassTranslator();
        log.trace("delete({}) result={}", entity, result);
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
