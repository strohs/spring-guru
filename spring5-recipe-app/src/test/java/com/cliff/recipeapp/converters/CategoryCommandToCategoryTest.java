package com.cliff.recipeapp.converters;


import com.cliff.recipeapp.commands.CategoryCommand;
import com.cliff.recipeapp.domain.Category;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class CategoryCommandToCategoryTest {

    public static final Long ID_VALUE = 1L;
    public static final String DESCRIPTION = "description";
    CategoryCommandToCategory conveter;

    @Before
    public void setUp() throws Exception {
        conveter = new CategoryCommandToCategory();
    }

    @Test
    public void testNullObject() throws Exception {
        assertThat( conveter.convert( null ), is( nullValue()));
        //assertNull(conveter.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertThat( conveter.convert( new CategoryCommand() ), is( notNullValue() ) );
        //assertNotNull(conveter.convert(new CategoryCommand()));
    }

    @Test
    public void convert() throws Exception {
        //given
        CategoryCommand categoryCommand = new CategoryCommand();
        categoryCommand.setId( ID_VALUE );
        categoryCommand.setDescription(DESCRIPTION);

        //when
        Category category = conveter.convert(categoryCommand);

        //then
        assertThat( category.getId(), is( equalTo( ID_VALUE )));
        //assertEquals(ID_VALUE, category.getId());
        assertThat( category.getDescription(), is( equalTo( DESCRIPTION )));
        //assertEquals(DESCRIPTION, category.getDescription());
    }

}