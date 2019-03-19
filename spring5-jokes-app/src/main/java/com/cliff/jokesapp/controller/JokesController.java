package com.cliff.jokesapp.controller;

import com.cliff.jokesapp.service.JokeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Main controller for this web app
 */
@Controller
public class JokesController {

    private JokeService jokeService;

    @Autowired
    public JokesController(JokeService jokeService) {
        this.jokeService = jokeService;
    }

    @RequestMapping({"/",""})
    public String chuckNorrisJoke( Model model ) {
        String joke = jokeService.getJoke();
        model.addAttribute("joke",joke);

        return "chucknorris";
    }
}
