package com.cliff.recipeapp.services;

import com.cliff.recipeapp.commands.UnitOfMeasureCommand;

import java.util.Set;

/**
 * @author Cliff
 * 10/9/17
 */
public interface UnitOfMeasureService {

    Set<UnitOfMeasureCommand> listAllUoms();
}
