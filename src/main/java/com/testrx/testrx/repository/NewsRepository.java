package com.testrx.testrx.repository;

import com.testrx.testrx.model.News;
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
 * Класс NewsRepository
 *
 * @author Max Ivanov
 * created 27.12.2021
 */
@Slf4j
@Repository
public class NewsRepository {
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
    public List<News> getNews() {
            return template.query(SQL_SELECT_LIST, NEWS_MAPPER);
    }

    /**
     * Вставка новой записи
     *
     * @param entity новая запись
     */
    public void insert(News entity) {
            var result = template.update(SQL_INSERT, entity.getNewsNum(), entity.getId());
            if (result != 1) new SQLExceptionSubclassTranslator();
            log.trace("insert({}) result={}", entity, result);
    }

    /**
     * Обновление записи
     *
     * @param entity обновляемая запись
     */
    public void update(News entity) {
        var result = template.update(SQL_UPDATE, entity.getNewsNum(), entity.getName(), entity.getId());
        if (result != 1) new SQLExceptionSubclassTranslator();
        log.trace("update({}) result={}", entity, result);
    }

    /**
     * Удаление записи
     *
     * @param entity удаляемая запись
     */
    public void delete(News entity) {
        // в параметры запроса идентификатор
        var result = template.update(SQL_DELETE, entity.getId());
        if (result != 1) new SQLExceptionSubclassTranslator();
        log.trace("delete({}) result={}", entity, result);
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
