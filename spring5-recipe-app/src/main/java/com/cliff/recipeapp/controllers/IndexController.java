package com.cliff.recipeapp.controllers;

import com.cliff.recipeapp.domain.Recipe;
import com.cliff.recipeapp.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;

/**
 * Controller for the index page
 */
@Slf4j
@Controller
public class IndexController {

    private RecipeService recipeService;

    @Autowired
    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping({"","/"})
    public String getIndexPage( Model model ) {
        log.debug( "getting index page" );
        Set<Recipe> recipes = recipeService.getAllRecipes();
        model.addAttribute("recipes",recipes);

        return "index";
    }
}
