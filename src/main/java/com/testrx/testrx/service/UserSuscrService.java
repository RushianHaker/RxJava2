package com.testrx.testrx.service;

import com.testrx.testrx.model.User;
import com.testrx.testrx.repository.UserRepository;
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
public class UserSuscrService {

    //beans
    protected final UserRepository repo;


    /**
     * Возвращает запись по идентификатору
     *
     * @param id идентификатор записи
     * @return запрашиваемая запись
     */
    public User getById(int id) {
        return repo.getById(id);
    }

    /**
     * Возвращает список записей
     *
     * @return запрашиваемая запись
     */
    public List<User> getUserList() {
        return repo.getUserList();
    }

    /**
     * Вставка новой записи
     *
     * @param entity новая запись
     */
    public void insert(User entity) {
        repo.insert(entity);
    }

    /**
     * Обновление записи
     *
     * @param entity обновляемая запись
     */
    public void update(User entity) {
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
    public void delete(User entity) {
        var savedEntity = getById(entity.getId());
        log.trace("delete({})", savedEntity.getId());
        repo.delete(entity);
        log.trace("delete({}) done", savedEntity.getId());
    }
}
