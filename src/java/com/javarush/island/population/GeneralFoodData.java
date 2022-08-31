package com.javarush.island.population;

import com.javarush.island.population.carnivore.*;
import com.javarush.island.population.herbivore.*;
import com.javarush.island.population.plants.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GeneralFoodData {
    @Getter
    private final Map<AbleToEat, ArrayList<VictimAndChance>> allFood = new HashMap<>();
    {
        ArrayList<VictimAndChance> victimsWulf = new ArrayList<>() {
            {
                add(new VictimAndChance(new Horse(), 10));
                add(new VictimAndChance(new Deer(), 15));
                add(new VictimAndChance(new Rabbit(), 60));
                add(new VictimAndChance(new Mouse(), 80));
                add(new VictimAndChance(new Goat(), 60));
                add(new VictimAndChance(new Sheep(), 70));
                add(new VictimAndChance(new Boar(), 15));
                add(new VictimAndChance(new Bull(), 10));
                add(new VictimAndChance(new Duck(), 40));
            }
        };
        allFood.put(new Wolf(), victimsWulf);
        ArrayList<VictimAndChance> victimsBoa = new ArrayList<>() {
            {
                add(new VictimAndChance(new Fox(), 15));
                add(new VictimAndChance(new Rabbit(), 20));
                add(new VictimAndChance(new Mouse(), 40));
                add(new VictimAndChance(new Duck(), 10));
            }
        };
        allFood.put(new Boa(), victimsBoa);
        ArrayList<VictimAndChance> victimsFox = new ArrayList<>() {
            {
                add(new VictimAndChance(new Rabbit(), 70));
                add(new VictimAndChance(new Mouse(), 90));
                add(new VictimAndChance(new Duck(), 60));
                add(new VictimAndChance(new Caterpillar(), 40));
            }
        };
        allFood.put(new Fox(), victimsFox);
        ArrayList<VictimAndChance> victimsBear = new ArrayList<>() {
            {
                add(new VictimAndChance(new Boa(), 80));
                add(new VictimAndChance(new Horse(), 40));
                add(new VictimAndChance(new Deer(), 80));
                add(new VictimAndChance(new Rabbit(), 80));
                add(new VictimAndChance(new Mouse(), 90));
                add(new VictimAndChance(new Goat(), 70));
                add(new VictimAndChance(new Sheep(), 70));
                add(new VictimAndChance(new Boar(), 50));
                add(new VictimAndChance(new Bull(), 20));
                add(new VictimAndChance(new Duck(), 10));
            }
        };
        allFood.put(new Bear(), victimsBear);
        ArrayList<VictimAndChance> victimsEagle = new ArrayList<>() {
            {
                add(new VictimAndChance(new Fox(), 10));
                add(new VictimAndChance(new Rabbit(), 90));
                add(new VictimAndChance(new Mouse(), 90));
                add(new VictimAndChance(new Duck(), 80));
            }
        };
        allFood.put(new Eagle(), victimsEagle);
        ArrayList<VictimAndChance> victimsMouse = new ArrayList<>() {
            {
                add(new VictimAndChance(new Carrot(), 100));
                add(new VictimAndChance(new Grass(), 100));
                add(new VictimAndChance(new Caterpillar(), 90));
            }
        };
        allFood.put(new Mouse(), victimsMouse);
        ArrayList<VictimAndChance> victimsBoar = new ArrayList<>() {
            {
                add(new VictimAndChance(new Mouse(), 50));
                add(new VictimAndChance(new Caterpillar(), 90));
                add(new VictimAndChance(new Carrot(), 100));
                add(new VictimAndChance(new Grass(), 100));
            }
        };
        allFood.put(new Boar(), victimsBoar);
        ArrayList<VictimAndChance> victimsDuck = new ArrayList<>() {
            {
                add(new VictimAndChance(new Caterpillar(), 90));
                add(new VictimAndChance(new Carrot(), 100));
                add(new VictimAndChance(new Grass(), 100));
            }
        };
        allFood.put(new Duck(), victimsDuck);
        ArrayList<VictimAndChance> victimsHorse = new ArrayList<>() {
            {
                add(new VictimAndChance(new Carrot(), 100));
                add(new VictimAndChance(new Grass(), 100));
            }
        };
        allFood.put(new Horse(), victimsHorse);
        allFood.put(new Rabbit(), victimsHorse);
        allFood.put(new Goat(), victimsHorse);
        allFood.put(new Sheep(), victimsHorse);
        allFood.put(new Bull(), victimsHorse);
        allFood.put(new Caterpillar(), victimsHorse);
        allFood.put(new Deer(), victimsHorse);
    }
}
