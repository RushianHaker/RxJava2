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
     * @param entity новость
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<News> getNews(@RequestParam String entity) {
        log.trace("[POST] getNews({})", entity);
        return publService.send(entity);
    }
}
