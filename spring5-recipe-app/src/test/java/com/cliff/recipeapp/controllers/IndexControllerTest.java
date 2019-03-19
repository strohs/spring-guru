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

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * @author Cliff
 * 10/7/17
 */
public class IndexControllerTest {

    IndexController indexController;

    @Mock
    RecipeService recipeService;

    @Mock
    Model model;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks( this );

        indexController = new IndexController( recipeService );
        //be sure to set ControllerAdvice to enable global controller exception handling
        mockMvc = MockMvcBuilders.standaloneSetup( indexController )
                .setControllerAdvice( new ControllerExceptionHandler() )
                .build();
    }

    /**
     * prefered way to test MVC controllers is to use MockMVC
     * @throws Exception
     */
    @Test
    public void testMockMvc() throws Exception {
        //preferred way to test MVC controllers is to use MockMVC and MockMvcBuilders
        //standAloneSetup is fast, more configurable, than .webAppContextSetup (which brings up the WebAppContext)
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup( indexController ).build();

        mockMvc.perform( get( "/") )
                .andExpect( status().isOk() )
                .andExpect( view().name("index") );
    }

    @Test
    public void getIndexPage() throws Exception {
        String indexStrData = "index";
        //given
        Set<Recipe> recipes = new HashSet<>();
        Recipe recipe1 = new Recipe();
        Recipe recipe2 = new Recipe();
        recipe1.setId( 1L );
        recipe2.setId( 2L );
        recipes.add( recipe1 );
        recipes.add( recipe2 );

        when( recipeService.getAllRecipes() ).thenReturn( recipes );

        //use this argument captor to hold the Set of recipes added to the Model via addAttribute
        ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class);

        //when
        String viewName = indexController.getIndexPage( model );

        assertEquals( indexStrData, viewName );
        verify( recipeService, times(1) ).getAllRecipes();
        verify( model, times(1) ).addAttribute( eq("recipes"), argumentCaptor.capture() );

        Set<Recipe>  setInController = argumentCaptor.getValue();
        //verify our dummy set of empty recipes of size 2, has been added to the model
        assertEquals( 2,setInController.size() );
    }



}