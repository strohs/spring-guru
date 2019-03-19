package com.cliff.recipeapp.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.math.BigDecimal;

/**
 * @author Cliff
 * 10/5/17
 */
@Getter
@Setter
//exclude the recipe property from hashCode/equals. This will get rid of circular dependencies in JPA bidirectional relationships
@EqualsAndHashCode( exclude = {"recipe"}, callSuper = true )
@Entity
public class Ingredient extends AbstractDomainClass {

    private String description;
    private BigDecimal amount;

    //uni-directional. no cascade, don't want to delete a uom if we delete an ingredient
    //explicitly setting fetch type for example purposes
    @OneToOne(fetch = FetchType.EAGER)
    private UnitOfMeasure uom;

    //Bi-directional relationship. no cascade here, deleting an ingredient should not delete a recipe
    @ManyToOne
    private Recipe recipe;


}
