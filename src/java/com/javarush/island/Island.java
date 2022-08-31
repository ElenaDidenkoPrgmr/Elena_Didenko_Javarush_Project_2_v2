package com.javarush.island;

import com.diogonunes.jcolor.Attribute;
import com.javarush.island.factory.Creator;
import com.javarush.island.population.Liveable;
import com.javarush.island.population.abstracts.AbstractAnimal;
import com.javarush.island.population.abstracts.AbstractPlant;
import com.javarush.island.population.abstracts.Entity;
import com.javarush.island.service.SimulationSettings;
import lombok.Getter;

import java.io.PrintStream;
import java.util.*;
import java.util.concurrent.Phaser;

import static com.diogonunes.jcolor.Ansi.colorize;

public class Island {
    private final int DAYS_LIVE_WITHOUT_EAT = 4;
    private final String LABEL_DAY = " -  DAY ";
    private final String LABEL_DEATH_WITHOUT_FOOD = " -  after the death of hungry animals";
    private final String LABEL_HUNTING = " -  after hunting";
    private final String LABEL_MULTIPLY = " -  after multiply animals and growing plants";
    private final String LABEL_INITIALIZATION = " island after initialization";
    private final int X = 0;
    private final int Y = 1;
    @Getter
    int width;
    @Getter
    int height;
    int simulationDay;

    @Getter
    private final Map<Liveable, List<Liveable>>[][] mapCell;
    @Getter
    private final List<Liveable> dictionaryEntities = (new Creator()).getAllEntity();

    public Island() {
        width = new SimulationSettings().getIntProperties("island.width");
        height = new SimulationSettings().getIntProperties("island.height");
        simulationDay = new SimulationSettings().getIntProperties("simulation.days");
        mapCell = new HashMap[width][height];
    }

    public Island(int width, int height, int simulationDay) {
        this.width = width;
        this.height = height;
        this.simulationDay = simulationDay;
        mapCell = new HashMap[width][height];
    }

    public void lifeCycleIslandWithPhaser(PrintStream output) {
        Statistics statistics = new Statistics();
        initializationIsland();
        printInitialStatistics(output, statistics);

        for (int i = 0; i < simulationDay; i++) {
            Phaser phaser = new Phaser( 1);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    PhaseThread phaseThread = new PhaseThread(phaser,x,y);
                    new Thread(phaseThread).start();
                }
            }

            phaser.arriveAndAwaitAdvance(); // updateAnimalPropertiesOnCell
            printDayStatistics(output, statistics, LABEL_DAY + (i + 1) + LABEL_DEATH_WITHOUT_FOOD);

            phaser.arriveAndAwaitAdvance(); //growPlantOnCell
            phaser.arriveAndAwaitAdvance(); //animalsMultiplyOnCell
            printDayStatistics(output, statistics, LABEL_DAY + (i + 1) + LABEL_MULTIPLY);

            phaser.arriveAndAwaitAdvance(); //huntAnimalsOnCell

            printDayStatistics(output, statistics, LABEL_DAY + (i + 1) + LABEL_HUNTING);
            phaser.arriveAndAwaitAdvance();
            phaser.arriveAndDeregister();

            animalsMove();

            if (need_repeatHeader(i)) {
                output.println(colorize(statistics.getStatistics(this,output).get(0), Attribute.GREEN_TEXT()));
            }
        }
    }

    class PhaseThread implements Runnable {
        Phaser phaser;
        int x;
        int y;

        PhaseThread(Phaser p, int x, int y) {
            this.phaser = p;
            this.x = x;
            this.y = y;
            phaser.register();
        }

        @Override
        public void run() {
            updateAnimalPropertiesOnCell(this.x,this.y);
            phaser.arriveAndAwaitAdvance();

            growPlantOnCell(this.x,this.y);
            phaser.arriveAndAwaitAdvance();

            animalsMultiplyOnCell(this.x,this.y);
            phaser.arriveAndAwaitAdvance();

            huntAnimalsOnCell(this.x,this.y);
            phaser.arriveAndDeregister();
        }
    }

    private boolean need_repeatHeader(int i) {
        return i % 5 == 0 && i != 0;
    }

    private void printDayStatistics(PrintStream output, Statistics statistics, String LABEL_DAY) {
        output.print(statistics.getStatistics(this,output).get(1));
        output.println(LABEL_DAY);
    }

    private void printInitialStatistics(PrintStream output, Statistics statistics) {
        output.println(colorize(statistics.getStatistics(this,output).get(0), Attribute.GREEN_TEXT()));
        printDayStatistics(output, statistics, LABEL_INITIALIZATION);
    }

    private void initializationIsland() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                mapCell[i][j] = new HashMap<>();

                for (Liveable typeLive : dictionaryEntities) {
                    var entity = (Entity) typeLive;
                    var maxAmount = entity.getMaxAmount();
                    var amountCreatedEntity = new Random().nextInt(maxAmount);
                    mapCell[i][j].put(entity, createEntities(entity, amountCreatedEntity));
                }
            }
        }
    }

    private List<Liveable> createEntities(Entity entity, int amount) {
        List<Liveable> result = new ArrayList<>();
        for (int k = 0; k < amount; k++) {
            result.add((Liveable) entity.clone());
        }
        return result;
    }

    private void updateAnimalPropertiesOnCell(int i, int j) {
        for (Liveable typeLive : dictionaryEntities) {
            var cellIterator = mapCell[i][j].get(typeLive).iterator();

            while (cellIterator.hasNext()) {
                var entity = cellIterator.next();
                if (entity instanceof AbstractAnimal animal) {
                    updateSatietyAndMultiplyPossible(animal);
                    if (animal.getSatiety() < 0) {
                        cellIterator.remove();
                    }
                }
            }

        }
    }

    private void updateSatietyAndMultiplyPossible(AbstractAnimal animal) {
        animal.setAlreadyMultiplied(true);
        double starve = animal.getFullSatiety() / DAYS_LIVE_WITHOUT_EAT;
        animal.setSatiety(animal.getSatiety() - starve);
    }

    private void growPlantOnCell(int i, int j) {
        for (Liveable typeLive : dictionaryEntities) {
            if (typeLive instanceof AbstractPlant plant) {
                int maxPlantAmount = (plant.getMaxAmount());
                int plantAmount = new Random().nextInt(maxPlantAmount);
                if (maxPlantAmount > plantAmount + mapCell[i][j].get(plant).size()) {
                    mapCell[i][j].get(plant).addAll(createEntities(plant, plantAmount));
                } else {
                    mapCell[i][j].get(plant).addAll(createEntities(plant, maxPlantAmount - mapCell[i][j].get(plant).size()));
                }
            }
        }
    }

    private void huntAnimalsOnCell(int i, int j) {
        for (var typeLive : dictionaryEntities) {
            for (var entity : mapCell[i][j].get(typeLive)) {
                if (isHungryAnimal(entity)) {
                    AbstractAnimal hunter = (AbstractAnimal) entity;
                    var victim = hunter.getVictim(mapCell[i][j]);
                    if (victim != null) {
                        double victimWeight = victim.getWeight();
                        double newSatiety = hunter.getSatiety() + victimWeight;
                        hunter.setSatiety(Math.min(newSatiety, hunter.getFullSatiety()));

                        mapCell[i][j].get(victim).remove(victim);
                    }
                }
            }
        }
    }

    private boolean isHungryAnimal(Liveable entity) {
        return entity instanceof AbstractAnimal hunter && hunter.getSatiety() < hunter.getFullSatiety();
    }

    private void animalsMove() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {

                animalsMoveOnCell(i, j);
            }
        }
    }

    private void animalsMoveOnCell(int i, int j) {
        for (Liveable typeLive : dictionaryEntities) {
            Iterator<Liveable> cellIterator = mapCell[i][j].get(typeLive).iterator();

            while (cellIterator.hasNext()) {
                Liveable entity = cellIterator.next();
                if (entity instanceof AbstractAnimal animal) {
                    int[] newCell = animal.move(i, j, width, height);

                    if ((i != newCell[X] || j != newCell[Y]) && mapCell[newCell[0]][newCell[1]].get(entity).size() < animal.getMaxAmount()) {
                        cellIterator.remove();
                        mapCell[newCell[X]][newCell[Y]].get(entity).add(animal);
                    }
                }
            }
        }
    }

    private void animalsMultiplyOnCell(int i, int j){
        for (Liveable typeLive : dictionaryEntities) {
            if (typeLive instanceof AbstractAnimal animal) {
                {
                    int childrenAmount = 0;
                    for (var entity : mapCell[i][j].get(typeLive)) {
                        List<Liveable> partners = mapCell[i][j].get(animal);
                        if (((AbstractAnimal) entity).multiply(partners)) {
                            childrenAmount++;
                        }
                    }

                    if (animal.getMaxAmount() >= childrenAmount + mapCell[i][j].get(animal).size()) {
                        mapCell[i][j].get(animal).addAll(createEntities(animal, childrenAmount));
                    } else {
                        mapCell[i][j].get(animal).addAll(createEntities(animal, animal.getMaxAmount() - mapCell[i][j].get(animal).size()));
                    }
                }
            }
        }
    }
}
