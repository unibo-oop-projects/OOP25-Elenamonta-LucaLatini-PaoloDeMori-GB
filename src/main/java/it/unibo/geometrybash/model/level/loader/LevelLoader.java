package it.unibo.geometrybash.model.level.loader;

import java.io.InputStream;

import it.unibo.geometrybash.model.level.Level;
import it.unibo.geometrybash.model.level.loader.exception.LoadingFileException;

/**
 * A class that handle the parsing of the level from file.
 */
@FunctionalInterface
public interface LevelLoader {
    /**
     * A level that accepts an Input stream representing the file and parses it into a {@link Level} class implementation.
     * 
     * @param file the input file representing the level
     * @return An implementation of the level.
     * @throws LoadingFileException if some problems occured while parsing the file level.
     */
    Level levelLoaderFromInputStream(InputStream file) throws LoadingFileException;
}
