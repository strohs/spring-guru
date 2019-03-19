package com.cliff.recipeapp.services;

import com.cliff.recipeapp.domain.Recipe;
import com.cliff.recipeapp.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

/**
 * @author Cliff
 * 10/10/17
 */
@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    private RecipeRepository recipeRepository;

    @Autowired
    public ImageServiceImpl( RecipeRepository recipeRepository ) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void saveImageFile( Long recipeId, MultipartFile imageFile ) {
        log.debug( "received a file" );
        //get recipe from repository
        Optional<Recipe> recipeOptional = recipeRepository.findById( recipeId );
        Recipe recipe = recipeOptional.orElseThrow( () -> new RuntimeException( "recipe not found id:" +recipeId ) );


        try {
            //get the image bytes into a Byte Array
            Byte [] imageBytes = new Byte[ imageFile.getBytes().length ];
            int i = 0;
            for ( byte b : imageFile.getBytes() ) {
                imageBytes[ i++ ] = b;
            }
            recipe.setImage( imageBytes );

            //save the image file
            recipeRepository.save( recipe );
        } catch ( IOException e ) {
            //todo better error handling here
            log.error( "could not save image file" );
        }

    }
}
