package com.cliff.recipeapp.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Cliff
 * 10/5/17
 */
@Getter
@Setter
@Entity
@EqualsAndHashCode(callSuper = true)
public class Recipe extends AbstractDomainClass {


    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;

    @Lob
    private String directions;

    //using EnumType.String in order to make adding new Difficulty enum values less of a hassle
    @Enumerated(value = EnumType.STRING)
    private Difficulty difficulty;

    //Bi-directional relationship. mappedBy-> Ingredient.recipe, (the child), is the owner of this relationship
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
    private Set<Ingredient> ingredients = new HashSet<>();

    //JPA should create this is a BLOB in the DB
    //using the Byte wrapper type vs primitive byte since this is a recommendation from the hibernate team
    @Lob
    private Byte[] image;

    //Recipe is the owner of this relationship, if recipe is deleted, corresponding notes will be deleted
    @OneToOne(cascade = CascadeType.ALL)
    private Notes notes;

    //explicitly define a single join_table. 'recipe_id' is a foreign key to this Recipe.id. 'category_id' is a foreign
    // key to Category.id
    @ManyToMany
    @JoinTable(name = "recipe_category",
            joinColumns = @JoinColumn(name="recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<>();


    public void setNotes( Notes notes ) {
        if ( notes != null) {
            this.notes = notes;
            notes.setRecipe(this);
        }
    }

    public Recipe addIngredient( Ingredient ingredient ) {
        ingredient.setRecipe( this );
        this.ingredients.add( ingredient );
        return this;
    }

}
