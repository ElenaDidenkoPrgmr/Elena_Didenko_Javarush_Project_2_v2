package com.javarush.island.population.abstracts;

import com.javarush.island.population.AbleToEat;
import com.javarush.island.population.Liveable;
import com.javarush.island.population.Moveable;
import com.javarush.island.population.Multiplyable;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public abstract class AbstractAnimal extends Entity implements AbleToEat, Moveable, Multiplyable {

    private double satiety;
    private boolean alreadyMultiplied = true;
    private double fullSatiety;
    private int speed;

    @Override
    public int[] move(int x, int y, int weight, int height) {
        return (new AbstractAnimalImpl()).move(this, x,  y,  weight,  height);
    }

    @Override
    public boolean multiply(List<Liveable> partners) {
        return (new AbstractAnimalImpl()).multiply(this,partners);
    }
}
