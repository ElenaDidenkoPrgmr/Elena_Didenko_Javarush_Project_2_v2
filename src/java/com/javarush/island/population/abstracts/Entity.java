package com.javarush.island.population.abstracts;

import com.javarush.island.population.Liveable;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter

public abstract class Entity implements Liveable,Cloneable {
    private final String ERROR_CLONE = "Error clone object: ";

    private String name;
    private int maxAmount;
    private double weight;

    public Object clone()
    {
        try {
            return super.clone();
        } catch (CloneNotSupportedException ex) {
            throw new WrongEntityException(ERROR_CLONE + ex.getMessage(), ex);
        }
    }
}
