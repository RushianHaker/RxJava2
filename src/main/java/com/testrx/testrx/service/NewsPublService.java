package com.testrx.testrx.service;

import com.testrx.testrx.model.News;
import com.testrx.testrx.repository.NewsRepository;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.functions.Consumer;
import io.reactivex.subscribers.TestSubscriber;
import io.vertx.reactivex.ext.sql.SQLClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.reflect.Executable;
import java.util.List;

/**
 * Класс UserService
 *
 * @author Max Ivanov
 * created 27.12.2021
 */

@Service
@Slf4j
public class NewsPublService {

    private NewsRepository repo;

    /**
     * Возвращает запись по идентификатору
     *
     * @param id идентификатор записи
     * @return запрашиваемая запись
     */
    public Single<News> getById(int id) {
        return Single.just(repo.getById(id)).doOnSuccess(System.out::println);
    }

    /**
     * Возвращает список записей
     *
     * @return запрашиваемая запись
     */
    public Completable getNewsList() {
       return Completable.fromAction(() -> repo.getNewsList());
    }

    /**
     * Вставка новой записи
     *
     * @param entity новая запись
     */
    public Completable insert(News entity) {
        return Completable.fromAction(() -> repo.insert(entity));
    }

    /**
     * Обновление записи
     *
     * @param entity обновляемая запись
     */
    public Completable update(News entity) {
        return Completable.fromAction(() -> repo.update(entity));
    }

}

