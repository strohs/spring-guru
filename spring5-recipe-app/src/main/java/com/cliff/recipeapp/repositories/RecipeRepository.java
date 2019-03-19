package com.cliff.recipeapp.repositories;

import com.cliff.recipeapp.domain.Recipe;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository for our Recipe Entities
 * @author Cliff
 * 10/5/17
 */
public interface RecipeRepository extends CrudRepository<Recipe,Long> {
}
