package com.javarush.island;

import com.diogonunes.jcolor.Attribute;
import com.javarush.island.factory.Creator;
import com.javarush.island.population.Liveable;
import com.javarush.island.population.abstracts.AbstractAnimal;
import com.javarush.island.population.abstracts.Entity;

import java.io.PrintStream;
import java.util.*;
import static com.diogonunes.jcolor.Ansi.colorize;


public class Statistics {

    private final List<Liveable> dictionaryEntities = (new Creator()).getAllEntity();
    private List<Entity> diedAnimals = new ArrayList<>();

    public List<String> getStatistics(Island island, PrintStream output) {
        Map<String, Integer> currentStatisticsIsland = new HashMap<>();

        for (Liveable typeLive : dictionaryEntities) {
            int newAmount = 0;
            var entity = (Entity) typeLive;

            for (int i = 0; i < island.getWidth(); i++) {
                for (int j = 0; j < island.getHeight(); j++) {

                    if (!currentStatisticsIsland.containsKey(typeLive)) {
                        currentStatisticsIsland.put(entity.getName(), 0);
                    }
                    newAmount = newAmount + island.getMapCell()[i][j].get(typeLive).size();
                }
            }

            checkAnimalGameOver(output, newAmount, entity);
            currentStatisticsIsland.put(((Entity) typeLive).getName(), newAmount);
        }
        return prepare(currentStatisticsIsland);
    }

    private void checkAnimalGameOver(PrintStream output, int newAmount, Entity entity) {
        if (newAmount == 0 && entity instanceof AbstractAnimal && !diedAnimals.contains(entity)) {
            String deadAnimal = entity.getName() + " - all animals died. Continue...";
            output.println(colorize( deadAnimal, Attribute.RED_TEXT()));
            diedAnimals.add(entity);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public List<String> prepare(Map<String, Integer> statistics) {
        List<String> tableRows = new ArrayList<>();
        List<Integer> widthCollums = new ArrayList<>();

        Set<Map.Entry<String, Integer>> set = statistics.entrySet();
        String nameAnimals = "";
        for (Map.Entry<String, Integer> pair : set) {
            var addData = "   " + pair.getKey();
            widthCollums.add(addData.length());
            nameAnimals = nameAnimals + addData;
        }
        tableRows.add(nameAnimals);

        String nameRowPattern = "";
        for (Integer width : widthCollums) {
            nameRowPattern = nameRowPattern + "%" + width + "s";
        }

        String amountAnimals = "";
        for (Map.Entry<String, Integer> pair : set) {
            int index = nameRowPattern.indexOf('s');
            String amountRowPattern = nameRowPattern.substring(0, index + 1);
            nameRowPattern = nameRowPattern.substring(index + 1);
            amountAnimals = amountAnimals + amountRowPattern.formatted(pair.getValue());
        }
        tableRows.add(amountAnimals);

        return tableRows;
    }
}
