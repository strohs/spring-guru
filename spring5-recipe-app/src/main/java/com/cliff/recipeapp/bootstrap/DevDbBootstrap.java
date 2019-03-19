package com.cliff.recipeapp.bootstrap;

import com.cliff.recipeapp.domain.Category;
import com.cliff.recipeapp.domain.Difficulty;
import com.cliff.recipeapp.domain.Recipe;
import com.cliff.recipeapp.domain.UnitOfMeasure;
import com.cliff.recipeapp.repositories.CategoryRepository;
import com.cliff.recipeapp.repositories.RecipeRepository;
import com.cliff.recipeapp.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


/**
 * Bootstrap the development DB with some sample Recipe data
 * @author Cliff
 * 10/5/17
 */
@Slf4j
@Component
@Profile( "default" )
public class DevDbBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private RecipeRepository recipeRepository;
    private CategoryRepository categoryRepository;
    private UnitOfMeasureRepository unitOfMeasureRepository;

    @Autowired
    public DevDbBootstrap(RecipeRepository recipeRepository, CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    private UnitOfMeasure getUom(String description) {
        return unitOfMeasureRepository.findByDescription( description ).get();
    }

    private Category getCategory( String description ) {
        return categoryRepository.findByDescription( description ).get();
    }

    @Autowired
    private List<Recipe> initRecipes() {
        List<Recipe> recipes = new ArrayList<>();

        //build Spicy Grilled Chicken Tacos
        RecipeBuilder builder = new RecipeBuilder();

        builder.setCategory( getCategory("Mexican") );
        builder.setCategory( getCategory("American") );
        builder.addIngredient(2.0,getUom("Tablespoon"),"ancho chili powder");
        builder.addIngredient(1.0,getUom("Teaspoon"),"dried oregano");
        builder.addIngredient(1.0,getUom("Teaspoon"),"dried cumin");
        builder.addIngredient(1.0,getUom("Teaspoon"),"sugar");
        builder.addIngredient(0.5,getUom("Teaspoon"),"salt");
        builder.addIngredient(1.0,getUom("Clove"),"garlic");
        builder.addIngredient(1.0,getUom("Tablespoon"),"finely grated orange zest");
        builder.addIngredient(3.0,getUom("Tablespoon"),"fresh squeezed orange juice");
        builder.addIngredient(2.0,getUom("Tablespoon"),"olive oil");
        builder.addIngredient(1.25,getUom("Pound"),"boneless chicken thighs");
        builder.addIngredient(8.0,getUom("Small"),"corn tortillas");
        builder.addIngredient(3.0,getUom("Cup"),"packed baby arugula");
        builder.addIngredient(2.0,getUom("Medium"),"ripe avocados, sliced");
        builder.addIngredient(4.0,getUom("Small"),"radishes, thinly sliced");
        builder.addIngredient(0.5,getUom("Pint"),"cherry tomatoes, halved");
        builder.addIngredient(0.25,getUom("Cup"),"red onion, thinly sliced");
        builder.addIngredient(1.0,getUom("Bushel"),"roughly chopped cilantro");
        builder.addIngredient(0.5,getUom("Cup"),"sour cream");
        builder.addIngredient(1.0,getUom("Medium"),"Lime cut into wedges");
        builder.addDirections( "1 Prepare a gas or charcoal grill for medium-high, direct heat.\n" +
                "2 Make the marinade and coat the chicken: In a large bowl, stir together the chili powder, oregano, cumin, sugar, salt, garlic \n" +
                "and orange zest. Stir in the orange juice and olive oil to make a loose paste. Add the chicken to the bowl and toss to coat all over.\n" +
                "Set aside to marinate while the grill heats and you prepare the rest of the toppings.\n" +
                "3 Grill the chicken: Grill the chicken for 3 to 4 minutes per side, or until a thermometer inserted into the thickest part of the meat\n" +
                "registers 165F. Transfer to a plate and rest for 5 minutes.\n" +
                "4 Warm the tortillas: Place each tortilla on the grill or on a hot, dry skillet over medium-high heat. As soon as you see pockets of the \n" +
                "air start to puff up in the tortilla, turn it with tongs and heat for a few seconds on the other side.\n" +
                "Wrap warmed tortillas in a tea towel to keep them warm until serving.\n" +
                "5 Assemble the tacos: Slice the chicken into strips. On each tortilla, place a small handful of arugula. Top with chicken slices, \n" +
                "sliced avocado, radishes, tomatoes, and onion slices. Drizzle with the thinned sour cream. Serve with lime wedges." );
        builder.addNotes("Look for ancho chile powder with the Mexican ingredients at your grocery store, on buy it online. " +
                "(If you can't find ancho chili powder, you replace the ancho chili, the oregano, and the cumin with 2 1/2 tablespoons " +
                "regular chili powder, though the flavor won't be quite the same.)");
        Recipe tacos = builder.buildRecipe("Spicy Grilled Chicken Tacos",20,15,6,
                "Sally Vargas","http://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/",
                Difficulty.MODERATE,new Byte[1]);
        recipes.add( tacos );

        //build Perfect Guacamole
        builder = new RecipeBuilder();
        builder.setCategory( getCategory("Mexican") );
        builder.addIngredient(2.0,getUom("Medium"),"ripe avocados");
        builder.addIngredient(0.5,getUom("Teaspoon"),"kosher salt");
        builder.addIngredient(1.0,getUom("Tablespoon"),"fresh lime juice");
        builder.addIngredient(2.0,getUom("Tablespoon"),"minced red onion");
        builder.addIngredient(2.0,getUom("Unit"),"serrano chilles,stems and seed removed");
        builder.addIngredient(2.0,getUom("Tablespoon"),"cilantro finely chopped");
        builder.addIngredient(1.0,getUom("Dash"),"freshly grated black pepper");
        builder.addIngredient(0.5,getUom("Medium"),"ripe tomato");
        builder.addDirections("1 Cut avocado, remove flesh: Cut the avocados in half. Remove seed. Score the inside of the avocado with a blunt knife and \n" +
                "scoop out the flesh with a spoon. (See How to Cut and Peel an Avocado.) Place in a bowl.\n" +
                "2 Mash with a fork: Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)\n" +
                "3 Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide \n" +
                "some balance to the richness of the avocado and will help delay the avocados from turning brown.\n" +
                "Add the chopped onion, cilantro, black pepper, and chiles. Chili peppers vary individually in their hotness. So, start with a \n" +
                "half of one chili pepper and add to the guacamole to your desired degree of hotness.\n" +
                "Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and \n" +
                "adjust to your taste.\n" +
                "4 Cover with plastic and chill to store: Place plastic wrap on the surface of the guacamole cover it and to prevent air \n" +
                "reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.) Refrigerate until ready to serve.\n" +
                "Chilling tomatoes hurts their flavor, so if you want to add chopped tomato to your guacamole, add it just before serving.");
        builder.addNotes("The BEST guacamole! So easy to make with ripe avocados, salt, serrano chiles, cilantro and lime. Garnish " +
                "with red radishes or jicama. Serve with tortilla chips.");
        Recipe guacamole = builder.buildRecipe("Perfect Guacamole",10,0,4,"Elise Bauer",
                "http://www.simplyrecipes.com/recipes/perfect_guacamole/",
                Difficulty.EASY,new Byte[1]);
        recipes.add( guacamole );
        return recipes;

    }

    @Override
    //explicitly create a transaction boundary to prevent lazy initialization errors with Hibernate
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.debug( "Loading bootstrap data..." );
        List<Recipe> recipes = initRecipes();
        recipeRepository.saveAll( recipes );

    }
}
