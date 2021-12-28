package com.testrx.testrx.service;

import com.testrx.testrx.model.News;
import com.testrx.testrx.repository.NewsRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Класс UserService
 *
 * @author Max Ivanov
 * created 27.12.2021
 */

@Service
@Slf4j
@AllArgsConstructor
public class NewsPublService {
    private NewsRepository repo;

    /**
     * send порождает строку, и завершает свою работу.
     */
/*    public List<News> send(int id) {
        Observable.just(id).map(s -> repo.getNews().size()).subscribe(s -> System.out.println("Size of list - " + s));
        return repo.getNews();
    }*/


    /**
     * Возвращает запись по идентификатору
     *
     * @param id идентификатор записи
     * @return запрашиваемая запись
     */
    public News getById(int id) {
        return repo.getById(id);
    }

    /**
     * Возвращает список записей
     *
     * @return запрашиваемая запись
     */
    public List<News> getNewsList() {
        return repo.getNewsList();
    }

    /**
     * Вставка новой записи
     *
     * @param entity новая запись
     */
    public void insert(News entity) {
        repo.insert(entity);
    }

    /**
     * Обновление записи
     *
     * @param entity обновляемая запись
     */
    public void update(News entity) {
        var savedEntity = getById(entity.getId());
        log.trace("update({})", savedEntity.getId());
        repo.update(entity);
        log.trace("update({}) done", entity);
    }

    /**
     * Удаление записи
     *
     * @param entity удаляемая запись
     */
    public void delete(News entity) {
        var savedEntity = getById(entity.getId());
        log.trace("delete({})", savedEntity.getId());
        repo.delete(entity);
        log.trace("delete({}) done", savedEntity.getId());
    }
}

