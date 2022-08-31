package com.javarush.island.population.abstracts;

import com.javarush.island.population.Liveable;
import java.util.List;
import java.util.Random;

public class AbstractAnimalImpl{
    private final int SIDES = 4;
    private final int UP = 0;
    private final int RIGHT = 1;
    private final int DOWN = 2;
    private final int LEFT = 3;

    public boolean multiply(AbstractAnimal animal,List<Liveable> partners) {
        if (animal.isAlreadyMultiplied()) {
            for (Liveable liveable : partners) {
                var partner = (AbstractAnimal) liveable;
                if (checkPartner(animal, partner)) {
                    animal.setAlreadyMultiplied(false);
                    partner.setAlreadyMultiplied(false);
                    break;
                }
            }
            return new Random().nextBoolean();
        }return false;
    }

    private boolean checkPartner(AbstractAnimal animal, AbstractAnimal partner) {
        return animal != partner && partner.isAlreadyMultiplied();
    }

    public int[] move(AbstractAnimal animal, int x, int y, int width, int height) {
        int newX = x;
        int newY = y;
        for (int i = 0; i < animal.getSpeed(); i++) {

            int course = new Random().nextInt(SIDES);
            switch (course) {
                case UP -> newY--;
                case RIGHT -> newX++;
                case DOWN -> newY++;
                case LEFT -> newX--;
            }
        }
        if (newX < 0) newX = 0;
        if (newY < 0) newY = 0;
        if (newX > width - 1) newX = width - 1;
        if (newY > height - 1) newY = height - 1;
        return new int[]{newX, newY};
    }
}
