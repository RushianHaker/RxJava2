package com.testrx.testrx.controller;

import com.testrx.testrx.model.User;
import com.testrx.testrx.service.UserSuscrService;
import io.reactivex.Completable;
import io.reactivex.Single;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Класс UserController
 *
 * @author Max Ivanov
 * created 28.12.2021
 */
@Slf4j
@RestController
@RequestMapping("${app.http.user}")
@RequiredArgsConstructor
public class UserController {

    private final UserSuscrService usrService;

    @GetMapping(value = "/by_id")
    public Single<User> getUser(@RequestParam("id") int id){
        log.trace("[GET] getUser({})", id);
        return usrService.getById(id);
    }

    @GetMapping(value = "/list")
    public Completable getUserList(){
        log.trace("[GET] getUserList()");
        return  usrService.getUserList();
    }

    @PostMapping(value = "/post")
    public void postUser(
            @RequestParam("name") String name,
            @RequestParam("favoritesNews") String favoritesNews
    ){
        log.trace("[POST] postUser({},{})", name, favoritesNews);
        usrService.insert(new User(name, favoritesNews));
        log.trace("[POST] postUser({},{}) inserted", name, favoritesNews);
    }

    @PatchMapping(value = "/update")
    public void updateUser(
            @RequestParam("id") int id,
            @RequestParam("name") String name,
            @RequestParam("favoritesNews") String favoritesNews
    ){
        log.trace("[POST] updateUser({},{})", id, name);
        usrService.update(new User(id, name, favoritesNews));
        log.trace("[POST] updateUser({},{}) updated", id, name);
    }
}
