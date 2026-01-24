package it.unibo.geometrybash.view.assets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

/**
 * This class is a utility class responsible for loading text-based resources from the application classpath.
 */
public final class TextAssetReader {

    private final ResourceLoader loader;

    /**
     * Creates a new {@code TextAssetReader} that uses the given {@link ResourceLoader} to access text resources.
     *
     * @param loader the resource loader used to open classpath resources
     * @throws NullPointerException if {@code loader} is {@code null}
     */
    public TextAssetReader(final ResourceLoader loader) {
        this.loader = loader;
    }

    /**
     * Reads the entire content of the specified text resource and returns it as a single {@link String}.
     *
     * @param resourceName the logical name of the text resource in the classpath
     * @return the full textual content of the resource
     * @throws IllegalArgumentException if the resource cannot be read or does not exist
     */
    public String readAll(final String resourceName) {
        try (var in = loader.openStream(resourceName);
                var br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {

            return br.lines().collect(Collectors.joining("\n"));
        } catch (final IOException e) {
            throw new IllegalArgumentException("Error while reading text resource: " + resourceName, e);
        }
    }
}
