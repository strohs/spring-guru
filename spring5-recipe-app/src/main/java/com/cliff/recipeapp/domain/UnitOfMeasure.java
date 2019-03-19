package com.cliff.recipeapp.domain;

import lombok.*;

import javax.persistence.Entity;

/**
 * @author Cliff
 * 10/5/17
 */
@Getter
@Setter
@Entity
@EqualsAndHashCode(callSuper = true)
public class UnitOfMeasure extends AbstractDomainClass {

    private String description;


}
