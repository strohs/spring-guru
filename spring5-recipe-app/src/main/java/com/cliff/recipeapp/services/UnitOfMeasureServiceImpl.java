package com.cliff.recipeapp.services;

import com.cliff.recipeapp.commands.UnitOfMeasureCommand;
import com.cliff.recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.cliff.recipeapp.repositories.UnitOfMeasureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @author Cliff
 * 10/9/17
 */
@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    private final UnitOfMeasureRepository unitOfMeasureRepository;

    private final UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

    @Autowired
    public UnitOfMeasureServiceImpl( UnitOfMeasureRepository unitOfMeasureRepository,
                                     UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand ) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.unitOfMeasureToUnitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand;
    }

    @Override
    public Set<UnitOfMeasureCommand> listAllUoms() {

        return StreamSupport.stream( unitOfMeasureRepository.findAll().spliterator(), false )
                .map(unitOfMeasureToUnitOfMeasureCommand::convert)
                .collect( Collectors.toSet() );
    }
}
