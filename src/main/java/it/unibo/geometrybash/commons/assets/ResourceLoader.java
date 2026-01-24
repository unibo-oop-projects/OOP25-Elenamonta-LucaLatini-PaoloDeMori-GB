package it.unibo.geometrybash.commons.assets;

import java.io.InputStream;

/**
 * This interface defines an abstraction for loading read-only resources
 * identified by a logical name.
 */
@FunctionalInterface
public interface ResourceLoader {

    /**
     * Opens an {@link InputStream} to the specified resource located in the
     * classpath.
     *
     * @param name the name of the resource in the classpath
     * @return an {@link InputStream} for reading the resource content
     * @throws NullPointerException if the resource cannot be found in the classpath
     */
    InputStream openStream(String name);
}
