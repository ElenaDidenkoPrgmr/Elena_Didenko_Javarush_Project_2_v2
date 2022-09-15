package com.javarush.island.population;

import com.javarush.island.population.abstracts.Entity;
import lombok.Getter;

import java.util.*;

public interface AbleToEat {
    int boundChance = 101;

    default Entity getVictim(Map<Liveable, List<Liveable>> allEntitiesOnCell) {
        var victimAndChance = getVictimAndChance();
        if (checkSuccessHunting(victimAndChance, allEntitiesOnCell)) {
            return (Entity) allEntitiesOnCell.get(victimAndChance.getVictim()).get(0);
        }
        return null;
    }

    private boolean checkSuccessHunting(VictimAndChance victimAndChance, Map<Liveable, List<Liveable>> allEntitiesOnCell) {
        var victim = victimAndChance.getVictim();
        int chanceVictim = victimAndChance.getChance();
        int chanceHunter = new Random().nextInt(boundChance);
        return chanceHunter <= chanceVictim && !allEntitiesOnCell.get(victim).isEmpty();
    }

    private VictimAndChance getVictimAndChance() {
        ArrayList<VictimAndChance> foodsAnimal = new GeneralFoodData().getAllFood().get(this);
        int numberVictim = new Random().nextInt(foodsAnimal.size());
        return foodsAnimal.get(numberVictim);
    }
}

@Getter
class VictimAndChance {
    private Entity victim;
    private Integer chance;

    public VictimAndChance(Entity eaten, Integer chance) {
        this.victim = eaten;
        this.chance = chance;
    }
}
