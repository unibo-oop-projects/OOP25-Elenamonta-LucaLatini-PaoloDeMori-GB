package it.unibo.geometrybash.commons.assets;

import java.io.InputStream;
import java.util.Objects;

/**
 * This class is a concrete implementation of {@link ResourceLoader}
 * that loads resources from the application classpath.
 *
 * <p>
 * This implementation relies on the system {@link ClassLoader} and uses
 * {@link ClassLoader#getSystemResourceAsStream(String)} to retrieve resources
 * in a location-independent way.
 * </p>
 */
public final class ResourceLoaderImpl implements ResourceLoader {

    /**
     * Creates new {@code ResourceLoaderImpl}
     */
    public ResourceLoaderImpl(){
        // Default constructor.
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InputStream openStream(final String name) {
        final String resourceName = normalizeResourceName(name);

        return Objects.requireNonNull(
                ClassLoader.getSystemResourceAsStream(resourceName),
                "Resource not found in classpath: " + resourceName);
    }

    /**
     * This method normalizes a resource name so that it can be safely used with the
     * system {@link ClassLoader}.
     *
     * <p>
     * In particular, this method removes a leading {@code '/'} character if
     * present.
     * This prevents inconsistencies caused by different calling conventions and
     * ensures that the same logical resource is always addressed using a canonical
     * name.
     * </p>
     *
     * @param name the original resource name
     * @return the normalized resource name without a leading {@code '/'}
     */
    private static String normalizeResourceName(final String name) {
        Objects.requireNonNull(name, "resource name must not be null");
        return name.startsWith("/") ? name.substring(1) : name;
    }
}
