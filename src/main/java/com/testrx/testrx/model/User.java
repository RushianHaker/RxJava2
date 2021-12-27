package com.testrx.testrx.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Класс User
 *
 * @author Max Ivanov
 * created 27.12.2021
 */
@Data
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
    private String name;
    /**
     * Любимые новости
     */
    @JsonProperty("favoritesNews")
    private String favoritesNews;

    /**
     * Возвращает строковое представление объекта в формате JSON
     *
     * @return строковое представление объекта в формате JSON
     */
    @Override
    public String toString() {
        return "News{" + "id=" + id +", name=" + name + ", favoritesNews=" + favoritesNews + '}';
    }
}
