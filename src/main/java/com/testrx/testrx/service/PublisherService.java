package com.testrx.testrx.service;

import com.testrx.testrx.model.News;
import com.testrx.testrx.repository.NewsRepository;
import io.reactivex.Observable;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Класс UserService
 *
 * @author Max Ivanov
 * created 27.12.2021
 */

@Service
@Slf4j
@AllArgsConstructor
public class PublisherService {
    private NewsRepository repo;

    /**
     * send порождает строку, и завершает свою работу.
     */
    public List<News> send(String word) {
         Observable.just(word).subscribe(x -> System.out.println(repo.getNews()));
        return repo.getNews();
    }
}

