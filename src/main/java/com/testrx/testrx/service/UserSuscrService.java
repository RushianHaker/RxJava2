package com.testrx.testrx.service;

import com.testrx.testrx.model.User;
import com.testrx.testrx.repository.UserRepository;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.subscribers.DisposableSubscriber;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Класс UserSuscrService
 *
 * @author Max Ivanov
 * created 27.12.2021
 */
@Service
@Slf4j
@AllArgsConstructor
public class UserSuscrService extends DisposableSubscriber<Integer> {

    //beans
    protected final UserRepository repo;


    /**
     * Возвращает запись по идентификатору
     *
     * @param id идентификатор записи
     * @return запрашиваемая запись
     */
    public Single<User> getById(int id) {
        return Single.just(repo.getById(id)).doOnSuccess(System.out::println);
    }

    /**
     * Возвращает список записей
     *
     * @return запрашиваемая запись
     */
    public List<User> getUserList() {
        Observable.just(repo.getUserList())
                .map(s -> repo.getUserList().size())
                .doOnSubscribe(s -> System.out.println("Size of list - " + s));
        return repo.getUserList();
    }

    /**
     * Вставка новой записи
     *
     * @param entity новая запись
     */
    public void insert(User entity) {
        if (entity != null)
            repo.insert(entity);
    }

    /**
     * Обновление записи
     *
     * @param entity обновляемая запись
     */
    public void update(User entity) {
        if (entity != null)
            repo.update(entity);
    }



    @Override
    public void onNext(Integer integer) {
        getById(integer).doOnError(this::onError);
    }

    @Override
    public void onError(Throwable t) {
        log.error("onError: Произошла ошибка сервиса - " + t);
    }

    @Override
    public void onComplete() {
        log.info("users list: ");
        repo.getUserList();
    }
}
