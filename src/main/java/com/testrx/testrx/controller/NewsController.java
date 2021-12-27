package com.testrx.testrx.controller;

import com.testrx.testrx.model.News;
import com.testrx.testrx.service.PublisherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * Класс NewsController
 *
 * @author Max Ivanov
 * created 27.12.2021
 */
@Slf4j
@RestController
@RequestMapping("${app.http.news}")
@RequiredArgsConstructor
public class NewsController {

    private final PublisherService publService;

    /**
     * отправляем уведомление о новой статье на сайте
     * @param id id новости
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<News> getNews(@RequestParam int id) {
        log.trace("[POST] getNews({})", id);
        return publService.send(id);
    }
}
