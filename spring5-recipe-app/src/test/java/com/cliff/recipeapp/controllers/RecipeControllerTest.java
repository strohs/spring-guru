package com.cliff.recipeapp.controllers;

import com.cliff.recipeapp.commands.RecipeCommand;
import com.cliff.recipeapp.domain.Recipe;
import com.cliff.recipeapp.exceptions.NotFoundException;
import com.cliff.recipeapp.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * @author Cliff
 * 10/8/17
 */
public class RecipeControllerTest {


    @Mock
    RecipeService recipeService;

    RecipeController controller;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks( this );

        controller = new RecipeController( recipeService );
        //be sure to set ControllerAdvice to enable global controller exception handling
        mockMvc = MockMvcBuilders.standaloneSetup( controller )
                .setControllerAdvice( new ControllerExceptionHandler() )
                .build();
    }

    @Test
    public void testGetRecipe() throws Exception {
        //create one test recipe
        Recipe recipe = new Recipe();
        recipe.setId( 1L );


        //tell our Mock recipeService to return our test recipe when any long is passed into it
        when( recipeService.findById( anyLong() ) ).thenReturn( recipe );

        //mock up a get request to retrieve a recipe with ID of 1, expect an ok status code, expect the view to be
        // recipe/show  and expect the model to have an attribute 'recipe'
        mockMvc.perform( get( "/recipe/1/show" ) )
                .andExpect( status().isOk() )
                .andExpect( view().name( "recipe/show" ) )
                .andExpect( model().attributeExists( "recipe" ) );
    }

    @Test
    public void returnHttpStatusNotFoundWhenRecipeNotFound() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setId( 1L );

        //tell our Mock recipeService to throw an Exception
        when( recipeService.findById( anyLong() ) ).thenThrow( NotFoundException.class );

        mockMvc.perform( get( "/recipe/111/show" ) )
                .andExpect( status().isNotFound() )
                .andExpect( view().name( "404error" ) );
    }

    @Test
    public void returnBadRequestErrorPageWhenRecipeIdIsNotLong() throws Exception {
        String alphaNumeric = "45gfg";

        mockMvc.perform( get( "/recipe/" + alphaNumeric + "/show") )
                .andExpect( status().isBadRequest() )
                .andExpect( view().name( "400error" ) );
    }

    @Test
    public void testGetNewRecipeForm() throws Exception {
        RecipeCommand command = new RecipeCommand();

        mockMvc.perform( get( "/recipe/new" ) )
                .andExpect( status().isOk() )
                .andExpect( view().name( "recipe/recipeform" ) )
                .andExpect( model().attributeExists( "recipe" ) );
    }

    @Test
    public void testPostNewRecipeFormValidationFail() throws Exception {
        RecipeCommand command = new RecipeCommand();
        command.setId(2L);

        when(recipeService.saveRecipeCommand(any())).thenReturn(command);

        mockMvc.perform(post("/recipe")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("cookTime", "3000")

        )
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"))
                .andExpect(view().name("recipe/recipeform"));
    }

    @Test
    public void testPostNewRecipeForm() throws Exception {
        RecipeCommand command = new RecipeCommand();
        command.setId( 2L );

        when( recipeService.saveRecipeCommand( any() ) ).thenReturn( command );

        mockMvc.perform( post( "/recipe" )
                .contentType( MediaType.APPLICATION_FORM_URLENCODED )
                .param( "id", "" )
                .param( "description", "some string" )
                .param( "directions","some directions" )
        )
                .andExpect( status().is3xxRedirection() )
                .andExpect( view().name( "redirect:/recipe/2/show" ) );
    }



    @Test
    public void testGetUpdateView() throws Exception {
        RecipeCommand command = new RecipeCommand();
        command.setId( 2L );

        when( recipeService.findCommandById( anyLong() ) ).thenReturn( command );

        mockMvc.perform( get( "/recipe/1/update" ) )
                .andExpect( status().isOk() )
                .andExpect( view().name( "recipe/recipeform" ) )
                .andExpect( model().attributeExists( "recipe" ) );
    }

    @Test
    public void testDeleteAction() throws Exception {
        mockMvc.perform(get("/recipe/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

        verify(recipeService, times(1)).deleteById(anyLong());
    }
}
