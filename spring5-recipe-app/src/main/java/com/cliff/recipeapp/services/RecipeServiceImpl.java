package com.cliff.recipeapp.services;

import com.cliff.recipeapp.commands.RecipeCommand;
import com.cliff.recipeapp.converters.RecipeCommandToRecipe;
import com.cliff.recipeapp.converters.RecipeToRecipeCommand;
import com.cliff.recipeapp.domain.Recipe;
import com.cliff.recipeapp.exceptions.NotFoundException;
import com.cliff.recipeapp.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * 
 * @author Cliff
 * 10/5/17
 */
@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    @Autowired
    public RecipeServiceImpl( RecipeRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe, RecipeToRecipeCommand recipeToRecipeCommand ) {
        this.recipeRepository = recipeRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }


    @Override
    public Set<Recipe> getAllRecipes() {
        log.debug( "I'm in recipe service" );
        
        Set<Recipe> recipes = new HashSet<>();
        recipeRepository.findAll().forEach(recipes::add);
        return recipes;
    }

    @Override
    public Recipe findById( Long id ) {
        log.debug("finding recipe with id {}",id );

        Optional<Recipe> recipeOptional = recipeRepository.findById( id );
        if ( !recipeOptional.isPresent() ) {
            throw new NotFoundException( "recipe not found for id:" + id.toString() );
        }

        return recipeOptional.get();
    }

    @Override
    @Transactional
    public RecipeCommand saveRecipeCommand( RecipeCommand command ) {
        Recipe detachedRecipe = recipeCommandToRecipe.convert( command );
        Recipe savedRecipe = recipeRepository.save( detachedRecipe );
        log.debug( "Saved recipe id:{}",savedRecipe.getId() );
        return recipeToRecipeCommand.convert( savedRecipe );
    }

    @Override
    @Transactional //transactional because doing a conversion outside the scope and we have lazily loaded properties
    public RecipeCommand findCommandById( Long id ) {
        log.debug("finding recipe command id:{}",id);
        return recipeToRecipeCommand.convert( findById( id ) );
    }

    @Override
    public void deleteById( Long id ) {
        log.debug( "deleting recipe id:{}",id );
        recipeRepository.deleteById( id );
    }


}
