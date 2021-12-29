package com.testrx.testrx.service;

import com.testrx.testrx.model.User;
import com.testrx.testrx.repository.UserRepository;
import io.reactivex.Completable;
import io.reactivex.Single;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Класс UserSuscrService
 *
 * @author Max Ivanov
 * created 27.12.2021
 */
@Service
@Slf4j
@AllArgsConstructor
public class UserSuscrService {

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
    public Completable getUserList() {
        return Completable.fromAction(() -> repo.getUserList());
    }

    /**
     * Вставка новой записи
     *
     * @param entity новая запись
     */
    public Completable insert(User entity) {
        return Completable.fromAction(() -> repo.insert(entity));
    }

    /**
     * Обновление записи
     *
     * @param entity обновляемая запись
     */
    public Completable update(User entity) {
        return Completable.fromAction(() -> repo.update(entity));

    }
}
