package com.cliff.recipeapp.domain;

import lombok.EqualsAndHashCode;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Parent domain class that holds properties common to all of our entity classes
 * @author Cliff
 * 10/5/17
 */
@MappedSuperclass
@EqualsAndHashCode
public class AbstractDomainClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
