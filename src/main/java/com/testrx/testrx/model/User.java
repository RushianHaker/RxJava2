package com.testrx.testrx.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * Класс User
 *
 * @author Max Ivanov
 * created 27.12.2021
 */
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class User {
    /**
     * Идентификатор элемента
     */
    @JsonProperty("id")
    private int id;
    /**
     * Имя
     */
    @JsonProperty("name")
    private final String name;
    /**
     * Любимые новости
     */
    @JsonProperty("favoritesNews")
    private final String favoritesNews;

    /**
     * Возвращает строковое представление объекта в формате JSON
     *
     * @return строковое представление объекта в формате JSON
     */
    @Override
    public String toString() {
        return "User{" + "id=" + id +", name=" + name + ", favoritesNews=" + favoritesNews + '}';
    }
}
