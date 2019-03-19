package com.cliff.recipeapp.services;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author Cliff
 * 10/10/17
 */
public interface ImageService {

    void saveImageFile( Long recipeId, MultipartFile file );

}
