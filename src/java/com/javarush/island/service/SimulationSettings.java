package com.javarush.island.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;

public class SimulationSettings {
    private final String ERROR_FILE = "Error during file reading; path: ";
    private final String ERROR_SETTINGS = "Error during load settings from file. ";

    public Properties appProperties() {
        File appPropertiesFile = new File("app.properties");
        FileReader fileReader = null;

        try {
            fileReader = new FileReader(appPropertiesFile);
        } catch (FileNotFoundException ex) {
            throw new WrongFileException(ERROR_FILE + appPropertiesFile + ex.getMessage(), ex);
        }

        Properties propertiesFromFile = new Properties();
        try {
            propertiesFromFile.load(fileReader);
        } catch (IOException ex) {
            throw new WrongFileException(ERROR_SETTINGS + appPropertiesFile + ex.getMessage(), ex);
        }

        return propertiesFromFile;
    }

    public int getIntProperties(String property) {
        return Integer.parseInt(appProperties().getProperty(property.toLowerCase(Locale.ROOT)));
    }

    public double getDoubleProperties(String property) {
        return Double.parseDouble(appProperties().getProperty(property.toLowerCase(Locale.ROOT)));
    }
}
