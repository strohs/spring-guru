package com.cliff.recipeapp.domain;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * @author Cliff
 * 10/7/17
 */
public class CategoryTest {

    Category category;

    @Before
    public void setUp() {
        category = new Category();
    }

    @Test
    public void getDescription() throws Exception {
    }

    @Test
    public void getRecipes() throws Exception {
    }

    @Test
    public void getId() throws Exception {
        Long idValue = 4L;

        category.setId( 4L );
        //assertEquals( idValue, category.getId() );
        assertThat( category.getId(), is( equalTo( idValue ) ) );
    }

}