package com.cliff.recipeapp.bootstrap;

import com.cliff.recipeapp.domain.*;

import java.math.BigDecimal;

/**
 * Build a dummy recipe
 * 1. Notes, Ingredients and Categories have to be added first via their corresponding add methods
 * 2. then call RecipeBuilder.buildRecipe to build the new recipe
 * @author Cliff
 * 10/5/17
 */
public class RecipeBuilder {

    private Recipe recipe;

    public RecipeBuilder() {
        this.recipe = new Recipe();
    }

    public Notes addNotes( String recipeNotes ) {
        Notes notes = new Notes();
        notes.setRecipeNotes( recipeNotes );
        recipe.setNotes( notes );
        return notes;
    }

    public Ingredient addIngredient( Double amount, UnitOfMeasure uom, String description ) {
        Ingredient ingredient = new Ingredient();
        ingredient.setAmount( new BigDecimal(amount) );
        ingredient.setDescription( description );
        ingredient.setUom( uom );
        recipe.addIngredient( ingredient );
        return ingredient;
    }

    public void addDirections( String directions ) {
        recipe.setDirections( directions );
    }

    public void setCategory( Category category ) {
        recipe.getCategories().add( category );
        //category.getRecipes().add( this );
    }

    public Recipe buildRecipe(String recipeDescription, Integer prepTime, Integer cookTime, Integer servings, String source,
                              String url, Difficulty difficulty, Byte[] image) {
        recipe.setDescription( recipeDescription );
        recipe.setPrepTime( prepTime );
        recipe.setCookTime( cookTime );
        recipe.setServings( servings );
        recipe.setSource( source );
        recipe.setUrl( url );
        recipe.setDifficulty( difficulty );
        //recipe.setImage( image );
        return recipe;
    }


}
