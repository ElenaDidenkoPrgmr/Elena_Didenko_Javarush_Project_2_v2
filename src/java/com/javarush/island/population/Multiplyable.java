package com.javarush.island.population;

import java.util.List;

public interface Multiplyable extends Liveable {
    boolean multiply(List<Liveable> partners);
}
