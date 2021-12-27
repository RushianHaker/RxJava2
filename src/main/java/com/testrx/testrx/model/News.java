package com.testrx.testrx.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Класс News
 *
 * @author Max Ivanov
 * created 27.12.2021
 */
@Data
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
    private String newsNum;
    /**
     * Имя новости
     */
    @JsonProperty("name")
    private String name;

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
