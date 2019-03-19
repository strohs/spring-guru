package com.cliff.recipeapp.services;

import com.cliff.recipeapp.commands.RecipeCommand;
import com.cliff.recipeapp.domain.Recipe;

import java.util.Set;


/**
 * @author Cliff
 * 10/5/17
 */
public interface RecipeService {

    Set<Recipe> getAllRecipes();

    Recipe findById( Long id );

    RecipeCommand saveRecipeCommand(RecipeCommand command);

    RecipeCommand findCommandById( Long id );

    void deleteById( Long id );
}
