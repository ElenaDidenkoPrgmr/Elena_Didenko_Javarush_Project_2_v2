package com.javarush.island.factory;

import com.javarush.island.population.Liveable;
import com.javarush.island.population.abstracts.*;
import com.javarush.island.population.carnivore.*;
import com.javarush.island.population.herbivore.*;
import com.javarush.island.population.plants.*;
import com.javarush.island.service.SimulationSettings;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Creator {
    public final String WEIGHT = ".weight";
    public final String MAX_AMOUNT = ".max.amount";
    public final String SPEED = ".speed";
    public final String FULL_SATIETY = ".full.satiety";

    public List<Liveable> dictionaryEntities = Stream.of(
            new Wolf(),
            new Fox(),
            new Eagle(),
            new Boa(),
            new Bear(),
            new Boar(),
            new Bull(),
            new Caterpillar(),
            new Deer(),
            new Duck(),
            new Goat(),
            new Horse(),
            new Mouse(),
            new Rabbit(),
            new Sheep(),
            new Grass(),
            new Carrot()
    ).collect(Collectors.toList());

    public List<Liveable> getAllEntity() {
       //List<Liveable> allEntity = new CopyOnWriteArrayList<>();
        List<Liveable> allEntity = new ArrayList<>();
        for (Liveable liveable : dictionaryEntities) {
            allEntity.add(createLive(liveable));
        }
        return allEntity;
    }

    public Liveable createLive(Liveable entity) {
        Liveable result = null;
        result = getIsWolf(entity,result);
        result = getIsFox(entity,result);
        result = getIsEagle(entity,result);
        result = getIsBoa(entity,result);
        result = getIsBear(entity,result);
        result = getIsBoar(entity,result);
        result = getIsBull(entity,result);
        result = getIsCaterpillar(entity,result);
        result = getIsDeer(entity,result);
        result = getIsDuck(entity,result);
        result = getIsGoat(entity,result);
        result = getIsHorse(entity,result);
        result = getIsMouse(entity,result);
        result = getIsRabbit(entity,result);
        result = getIsSheep(entity,result);
        result = getIsGrass(entity,result);
        result = getIsCarrot(entity,result);

        setEntityProperties(result);
        if (result instanceof AbstractAnimal animal) {
            setAnimalProperties(animal);
        }
        return result;
    }

    private Liveable getIsCarrot(Liveable entity, Liveable result) {
        if (entity instanceof Carrot)
            result = new Carrot();
        return result;
    }

    private Liveable getIsGrass(Liveable entity, Liveable result) {
        if (entity instanceof Grass)
            result = new Grass();
        return result;
    }

    private Liveable getIsSheep(Liveable entity, Liveable result) {
        if (entity instanceof Sheep)
            result = new Sheep();
        return result;
    }

    private Liveable getIsRabbit(Liveable entity, Liveable result) {
        if (entity instanceof Rabbit)
            result = new Rabbit();
        return result;
    }

    private Liveable getIsMouse(Liveable entity, Liveable result) {
        if (entity instanceof Mouse)
            result = new Mouse();
        return result;
    }

    private Liveable getIsHorse(Liveable entity, Liveable result) {
        if (entity instanceof Horse)
            result = new Horse();
        return result;
    }

    private Liveable getIsGoat(Liveable entity, Liveable result) {
        if (entity instanceof Goat)
            result = new Goat();
        return result;
    }

    private Liveable getIsDuck(Liveable entity, Liveable result) {
        if (entity instanceof Duck)
            result = new Duck();
        return result;
    }

    private Liveable getIsDeer(Liveable entity, Liveable result) {
        if (entity instanceof Deer)
            result = new Deer();
        return result;
    }

    private Liveable getIsCaterpillar(Liveable entity, Liveable result) {
        if (entity instanceof Caterpillar)
            result = new Caterpillar();
        return result;
    }

    private Liveable getIsBull(Liveable entity, Liveable result) {
        if (entity instanceof Bull)
            result = new Bull();
        return result;
    }

    private Liveable getIsBoar(Liveable entity, Liveable result) {
        if (entity instanceof Boar)
            result = new Boar();
        return result;
    }

    private Liveable getIsBear(Liveable entity, Liveable result) {
        if (entity instanceof Bear)
            result = new Bear();
        return result;
    }

    private Liveable getIsBoa(Liveable entity, Liveable result) {
        if (entity instanceof Boa)
            result = new Boa();
        return result;
    }

    private Liveable getIsEagle(Liveable entity, Liveable result) {
        if (entity instanceof Eagle)
            result = new Eagle();
        return result;
    }

    private Liveable getIsWolf(Liveable entity,Liveable result) {
        if (entity instanceof Wolf)
            result = new Wolf();
        return result;
    }

    private Liveable getIsFox(Liveable entity,Liveable result) {
        if (entity instanceof Fox)
            result = new Fox();
        return result;
    }
    private void setEntityProperties(Liveable liveable) {
        if (liveable instanceof Entity entity) {
            entity.setName(entity.getClass().getSimpleName().toLowerCase(Locale.ROOT));
            var settings = new SimulationSettings();

            var weightProperty = entity.getName() + WEIGHT;
            entity.setWeight(settings.getDoubleProperties(weightProperty));

            var maxAmountProperty = entity.getName() + MAX_AMOUNT;
            entity.setMaxAmount(settings.getIntProperties(maxAmountProperty));
        }
    }

    private void setAnimalProperties(AbstractAnimal animal) {
        var settings = new SimulationSettings();

        var satietyProperty = animal.getName() + FULL_SATIETY;
        animal.setSatiety(settings.getDoubleProperties(satietyProperty));
        animal.setFullSatiety(settings.getDoubleProperties(satietyProperty));

        var speedProperty = animal.getName() + SPEED;
        animal.setSpeed(settings.getIntProperties(speedProperty));
    }
}
