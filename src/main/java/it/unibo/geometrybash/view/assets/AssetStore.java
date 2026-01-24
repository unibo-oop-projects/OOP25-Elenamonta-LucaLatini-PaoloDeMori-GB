package it.unibo.geometrybash.view.assets;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import javax.imageio.ImageIO;

/**
 * Loads and caches view assets using a {@link ResourceLoader}.
 *
 * <p>
 * This allows you to upload and decode an image only once.
 * </p>
 */
final class AssetStore {

    private final ResourceLoader loader;
    private final Map<String, BufferedImage> imageCache = new ConcurrentHashMap<>();

    /**
     * Creates a new {@code AssetStore}.
     *
     * @param loader the resource loader used to access classpath resources
     * @throws NullPointerException if {@code loader} is {@code null}
     */
    AssetStore(final ResourceLoader loader) {
        this.loader = Objects.requireNonNull(loader, "resource loader must not be null");
    }

    /**
     * Returns the image associated with the given resource name.
     * The image is loaded from the classpath and cached on first request.
     *
     * @param resourceName the classpath resource name
     * @return the loaded {@link BufferedImage}
     * @throws NullPointerException if {@code resourceName} is {@code null}
     * @throws IllegalArgumentException if the resource cannot be read as an image
     */
    BufferedImage getImage(final String resourceName) {
        Objects.requireNonNull(resourceName, "resource name must not be null");
        return imageCache.computeIfAbsent(resourceName, this::loadImage);
    }

    private BufferedImage loadImage(final String resourceName) {
        try (InputStream in = loader.openStream(resourceName)) {
            final BufferedImage image = ImageIO.read(in);
            if (image == null) {
                throw new IllegalArgumentException(
                    "Unsupported or corrupted image resource: " + resourceName
                );
            }
            return image;
        } catch (final IOException e) {
            throw new IllegalArgumentException(
                "Error while loading image resource: " + resourceName, e
            );
        }
    }
}

