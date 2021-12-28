package com.testrx.testrx.service;

import io.reactivex.Observable;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Класс PublisherServiceTest
 *
 * @author Max Ivanov
 * created 27.12.2021
 */

public class NewsPublServiceTest {
    String result="";

    // Simple subscription to a fix value
    @Test
    public void returnAValue(){
        result = "";
        Observable<String> observer = Observable.just("Hello");
        observer.subscribe(s -> result=s);
        assertTrue(result.equals("Hello"));
    }
}
