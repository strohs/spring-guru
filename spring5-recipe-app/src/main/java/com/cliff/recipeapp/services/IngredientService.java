package com.cliff.recipeapp.services;

import com.cliff.recipeapp.commands.IngredientCommand;

/**
 * @author Cliff
 * 10/9/17
 */
public interface IngredientService {

    IngredientCommand findByRecipeIdAndIngredientId( Long recipeId, Long ingredientId );

    IngredientCommand saveIngredientCommand( IngredientCommand ingredientCommand );

    void deleteByRecipeIdAndIngredientId( Long recipeId, Long ingredientId );

}
