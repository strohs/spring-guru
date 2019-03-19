package com.cliff.recipeapp.services;

import com.cliff.recipeapp.commands.IngredientCommand;
import com.cliff.recipeapp.converters.IngredientCommandToIngredient;
import com.cliff.recipeapp.converters.IngredientToIngredientCommand;
import com.cliff.recipeapp.domain.Ingredient;
import com.cliff.recipeapp.domain.Recipe;
import com.cliff.recipeapp.repositories.RecipeRepository;
import com.cliff.recipeapp.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author Cliff
 * 10/9/17
 */
@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    @Autowired
    public IngredientServiceImpl( IngredientToIngredientCommand ingredientToIngredientCommand,
                                  IngredientCommandToIngredient ingredientCommandToIngredient,
                                  RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository ) {
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    //TODO see of there is a JPA query method that will get a single ingredient directly
    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {

        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if (!recipeOptional.isPresent()){
            //todo impl error handling
            log.error("recipe id not found. Id: " + recipeId);
        }

        Recipe recipe = recipeOptional.get();

        Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .map( ingredient -> ingredientToIngredientCommand.convert(ingredient)).findFirst();

        if(!ingredientCommandOptional.isPresent()){
            //todo impl error handling
            log.error("Ingredient id not found: " + ingredientId);
        }

        return ingredientCommandOptional.get();
    }

    @Override
    @Transactional
    public IngredientCommand saveIngredientCommand(IngredientCommand command) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(command.getRecipeId());

        if(!recipeOptional.isPresent()){

            //todo toss error if not found!
            log.error("Recipe not found for id: " + command.getRecipeId());
            return new IngredientCommand();
        } else {
            Recipe recipe = recipeOptional.get();

            //check to see if this ingredient already exists
            Optional<Ingredient> ingredientOptional = recipe
                    .getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(command.getId()))
                    .findFirst();

            //ingredient exists so merge the changes
            if(ingredientOptional.isPresent()){
                Ingredient ingredientFound = ingredientOptional.get();
                ingredientFound.setDescription(command.getDescription());
                ingredientFound.setAmount(command.getAmount());
                ingredientFound.setUom(unitOfMeasureRepository
                        .findById(command.getUom().getId())
                        .orElseThrow(() -> new RuntimeException("UOM NOT FOUND"))); //todo address this
            } else {
                //add new Ingredient
                Ingredient ingredient = ingredientCommandToIngredient.convert(command);
                ingredient.setRecipe(recipe);
                recipe.addIngredient(ingredient);
            }

            Recipe savedRecipe = recipeRepository.save(recipe);

            //brand new ingredients won't have an Id value,so we are using 'check by description' below
            Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream()
                    .filter(recipeIngredients -> recipeIngredients.getId().equals(command.getId()))
                    .findFirst();

            //check by description
            if(!savedIngredientOptional.isPresent()){
                //todo write unit test for this scenario
                //not totally safe... But best guess, could have more than one ingredient equal to this
                savedIngredientOptional = savedRecipe.getIngredients().stream()
                        .filter(recipeIngredients -> recipeIngredients.getDescription().equals(command.getDescription()))
                        .filter(recipeIngredients -> recipeIngredients.getAmount().equals(command.getAmount()))
                        .filter(recipeIngredients -> recipeIngredients.getUom().getId().equals(command.getUom().getId()))
                        .findFirst();
            }

            //todo check for fail
            return ingredientToIngredientCommand.convert(savedIngredientOptional.get());
        }

    }

    @Override
    @Transactional
    public void deleteByRecipeIdAndIngredientId( Long recipeId, Long ingredientId ) {
        Optional<Recipe> recipeOptional = recipeRepository.findById( recipeId );
        if ( !recipeOptional.isPresent() ) {
            throw new RuntimeException( "can not find recipe with id:" + recipeId );
        }
        Recipe recipe = recipeOptional.get();

        Ingredient ingredient = recipe.getIngredients().stream()
                .filter( ingred -> ingred.getId().equals( ingredientId ))
                .findFirst().orElseThrow( () -> new RuntimeException( "Ingredient not found with id:" + ingredientId ) );

        ingredient.setRecipe( null );
        recipeOptional.get().getIngredients().remove( ingredient );
        recipeRepository.save( recipe );
    }


}
