package com.testrx.testrx.service;

import com.testrx.testrx.model.News;
import com.testrx.testrx.repository.NewsRepository;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.functions.Consumer;
import io.reactivex.subscribers.TestSubscriber;
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
@AllArgsConstructor
public class NewsPublService {

    private NewsRepository repo;
    private UserSuscrService subscriber;

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
    public List<News> getNewsList() {
        Observable.create(subscriber -> subscriber.onComplete());

        Observable.just(repo.getNewsList())
                .map(s -> repo.getNewsList().size())
                .doOnSubscribe(s -> System.out.println("Size of list - " + s));

        return repo.getNewsList();

    }

    /**
     * Вставка новой записи
     *
     * @param entity новая запись
     */
    public void insert(News entity) {
        if (entity != null)
            repo.insert(entity);
    }

    /**
     * Обновление записи
     *
     * @param entity обновляемая запись
     */
    public void update(News entity) {
        if (entity != null)
            repo.update(entity);
    }

}

