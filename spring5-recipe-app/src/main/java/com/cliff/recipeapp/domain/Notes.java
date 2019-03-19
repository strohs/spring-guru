package com.cliff.recipeapp.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import java.util.Set;

/**
 * @author Cliff
 * 10/5/17
 */
@Getter
@Setter
//exclude the recipe property from hashCode/equals. This will get rid of circular dependencies in JPA bidirectional relationships
@EqualsAndHashCode( exclude = {"recipe"}, callSuper = true)
@Entity
public class Notes extends AbstractDomainClass {


    @OneToOne
    private Recipe recipe;          //no cascade specified, we want Recipe to own this. If we delete a Notes we don't
                                    //want to delete a recipe

    //want ability to add more than 255 characters in the DB recipeNotes column. Because this a java String type,
    // JPA should map it to a CLOB
    @Lob
    private String recipeNotes;


}
