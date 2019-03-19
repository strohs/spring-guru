package com.cliff.recipeapp.repositories;

import com.cliff.recipeapp.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;

/**
 * 
 * User: Cliff
 * Date: 10/8/2017
 * Time: 11:08 AM
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class UnitOfMeasureRepositoryTestIT {

    @Autowired
    UnitOfMeasureRepository unitOfMeasureRepository;

    @Before
    public void setUp() throws Exception {
        
    }

    @Test
    //dirtiesContext causes a reload of the SpringContext, this test does not dirty the context
    //@DirtiesContext
    public void findByDescription() throws Exception {
        //test that we can get "Teaspoon" from our uomRepository
        Optional<UnitOfMeasure> uomOptional = unitOfMeasureRepository.findByDescription( "Teaspoon" );
        assertEquals( "Teaspoon",uomOptional.get().getDescription() );
    }

    @Test
    public void findByDescriptionCup() throws Exception {
        //test that we can get "Teaspoon" from our uomRepository
        Optional<UnitOfMeasure> uomOptional = unitOfMeasureRepository.findByDescription( "Cup" );
        assertEquals( "Cup",uomOptional.get().getDescription() );
    }

}