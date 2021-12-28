package com.testrx.testrx.controller;

import com.testrx.testrx.model.News;
import com.testrx.testrx.service.NewsPublService;
import io.reactivex.Single;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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

    private final NewsPublService newsService;

    @GetMapping(value = "/by_id")
    public Single<News> getNews(@RequestParam("id") int id){
        log.trace("[GET] getNews({})", id);
        return newsService.getById(id);
    }

    @GetMapping(value = "/list")
    public List<News> getNewsList(){
        log.trace("[GET] getNewsList()");
        return  newsService.getNewsList();
    }

    @PostMapping(value = "/post")
    public void postNews(
            @RequestParam("name") String name,
            @RequestParam("newsNum") String newsNum
    ){
        log.trace("[POST] postNews({},{})", name, newsNum);
        newsService.insert(new News(name, newsNum));
        log.trace("[POST] postNews({},{}) inserted", name, newsNum);
    }

    @PatchMapping(value = "/update")
    public void updateNews(
            @RequestParam("id") int id,
            @RequestParam("name") String name,
            @RequestParam("newsNum") String newsNum
    ){
        log.trace("[POST] updateNews({},{}, {})", id, name, newsNum);
        newsService.update(new News(id, name, newsNum));
        log.trace("[POST] updateNews({},{}, {}) updated", id, name, newsNum);
    }
}
