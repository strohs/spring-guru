package com.cliff.recipeapp.controllers;

import com.cliff.recipeapp.domain.Recipe;
import com.cliff.recipeapp.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * @author Cliff
 * 10/11/17
 */
public class IndexControllerTest2 {

    @Mock
    RecipeService recipeService;

    @Mock
    Model model;

    IndexController indexController;



    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks( this );
        indexController = new IndexController( recipeService );
    }

    @Test
    public void returnAllRecipesWhenIndexRequestIsMade() throws Exception {
        //given
        String indexViewName = "index";
        String recipesAttribute = "recipes";
        int allPersonsCount = 2;
        Set<Recipe> dummyRecipes = new HashSet <>();
        Recipe recipe1 = new Recipe();
        Recipe recipe2 = new Recipe();
        recipe1.setId( 1L );
        recipe2.setId( 2L );

        //stub getAllRecipes
        when( recipeService.getAllRecipes() ).thenReturn( dummyRecipes );

        ArgumentCaptor<Set<Recipe>> modelRecipesCaptor = ArgumentCaptor.forClass(Set.class);

        //when
        String returnedView = indexController.getIndexPage( model );

        //then
        assertThat( returnedView, is( equalTo( indexViewName ) ) );
        verify( recipeService ).getAllRecipes();
        verify( model ).addAttribute( eq(recipesAttribute), modelRecipesCaptor.capture() );
        Set<Recipe> modelRecipes = modelRecipesCaptor.getValue();
        assertEquals( dummyRecipes.size(),modelRecipes.size() );

    }

    @Test
    public void returnIndexViewOnGetRequest() throws Exception {
        String indexViewName = "index";
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup( indexController ).build();

        mockMvc.perform( get( "/") )
                .andExpect( status().isOk() )
                .andExpect( view().name(indexViewName) );

        mockMvc.perform( get( "") )
                .andExpect( status().isOk() )
                .andExpect( view().name(indexViewName) );


    }
}