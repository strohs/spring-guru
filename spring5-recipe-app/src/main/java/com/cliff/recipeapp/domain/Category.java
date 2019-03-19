package com.cliff.recipeapp.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.Set;

/**
 * uses project lombok
 *
 * @author Cliff
 * 10/5/17
 */
@Getter
@Setter
//exclude the recipe property from hashCode/equals. This will get rid of circular dependencies in JPA bidirectional relationships
@EqualsAndHashCode( exclude = {"recipes"}, callSuper = true)
@Entity
public class Category extends AbstractDomainClass {

    private String description;

    //we are defining a bi-directional ManyToMany relationship with Recipe as the owner. Define a mappedBy here to
    // indicate this is not the owner and to prevent Hibernate from creating another join table. This side will use
    // the join table's 'category.id' column to get corresponding recipes
    @ManyToMany(mappedBy = "categories")
    private Set<Recipe> recipes;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder( "Category{" );
        sb.append( "description='" ).append( description ).append( '\'' );
        sb.append( ", id=" ).append( id );
        sb.append( '}' );
        return sb.toString();
    }
}
