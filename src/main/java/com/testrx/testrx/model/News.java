package com.testrx.testrx.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * Класс News
 *
 * @author Max Ivanov
 * created 27.12.2021
 */
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class News {
    /**
     * Идентификатор элемента
     */
    @JsonProperty("id")
    private int id;
    /**
     * номер новости
     */
    @JsonProperty("newsNum")
    private final String newsNum;
    /**
     * Имя новости
     */
    @JsonProperty("name")
    private final String name;

    /**
     * Возвращает строковое представление объекта в формате JSON
     *
     * @return строковое представление объекта в формате JSON
     */
    @Override
    public String toString() {
        return "News{" + "id=" + id +", newsNum=" + newsNum + ", name=" + name + '}';
    }
}
