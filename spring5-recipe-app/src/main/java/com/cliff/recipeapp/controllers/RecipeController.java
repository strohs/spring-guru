package com.cliff.recipeapp.controllers;

import com.cliff.recipeapp.commands.RecipeCommand;
import com.cliff.recipeapp.exceptions.NotFoundException;
import com.cliff.recipeapp.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

/**
 * @author Cliff
 * 10/8/17
 */
@Slf4j
@Controller
public class RecipeController {

    private static final String RECIPE_RECIPEFORM_URL = "recipe/recipeform";

    private final RecipeService recipeService;

    @Autowired
    public RecipeController( RecipeService recipeService ) {
        this.recipeService = recipeService;
    }

    @GetMapping( "/recipe/{id}/show" )
    public String showRecipe( @PathVariable String id, Model model ) {
        log.debug("showing recipe for id:{}",id);

        model.addAttribute( "recipe", recipeService.findById( new Long( id ) ) );

        return "recipe/show";
    }

    @GetMapping( "recipe/new" )
    public String newRecipe( Model model ) {
        model.addAttribute( "recipe", new RecipeCommand() );

        return RECIPE_RECIPEFORM_URL;
    }

    @GetMapping( "recipe/{id}/update" )
    public String updateRecipe( @PathVariable String id, Model model ) {
        model.addAttribute( "recipe", recipeService.findCommandById( Long.valueOf( id ) ) );

        return RECIPE_RECIPEFORM_URL;
    }

    @PostMapping( "recipe" )
    public String saveOrUpdate( @Valid @ModelAttribute("recipe") RecipeCommand command, BindingResult bindingResult ) {

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach( objectError -> {
                log.debug( objectError.toString() );
            } );
            //recipeCommand will have the bindingResult with it (in the Model) so we can display errors on the page
            return RECIPE_RECIPEFORM_URL;
        }

        RecipeCommand savedCommand = recipeService.saveRecipeCommand( command );

        return "redirect:/recipe/" + savedCommand.getId() + "/show";
    }

    @GetMapping( "recipe/{id}/delete" )
    public String deleteRecipe( @PathVariable String id, Model model ) {
        recipeService.deleteById( Long.valueOf( id ) );
        return "redirect:/";
    }

    /**
     * handles NotFoundException
     *
     * @return ModelAndView with the view name set to the 404error page, and sets the Response Status code to 404
     */
    @ResponseStatus( HttpStatus.NOT_FOUND )
    @ExceptionHandler( NotFoundException.class )
    public ModelAndView handleNotFound( Exception exception ) {

        log.error( "Handling not found exception" );
        log.error( exception.getMessage() );

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName( "404error" );
        modelAndView.addObject( "exception", exception );


        return modelAndView;
    }


}
