package it.unibo.geometrybash.commons.assets;

import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Loads and caches audio resources from the classpath.
 * Supports music via {@link Clip}.
 */
public final class AudioStore {

    private final ResourceLoader loader;
    private final Map<String, Clip> clipCache = new ConcurrentHashMap<>();

    /**
     * Creates a new {@code AudioStore}.
     *
     * @param loader the resource loader used to load audio resources
     * @throws NullPointerException if {@code loader} is {@code null}
     */
    public AudioStore(final ResourceLoader loader) {
        this.loader = Objects.requireNonNull(loader, "resource loader must not be null");
    }

    /**
     * Returns a cached {@link Clip} for the given audio resource.
     *
     * @param resourceName classpath resource name
     * @return a ready-to-use {@link Clip}
     */
    public Clip getClip(final String resourceName) {
        Objects.requireNonNull(resourceName, "resourceName must not be null");
        return clipCache.computeIfAbsent(resourceName, this::loadClip);
    }

    private Clip loadClip(final String resourceName) {
        try (InputStream in = loader.openStream(resourceName);
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(in)) {

            final Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            return clip;

        } catch (final IOException e) {
            throw new IllegalArgumentException(
                    "I/O error while loading audio resource: " + resourceName, e);
        } catch (final UnsupportedAudioFileException e) {
            throw new IllegalArgumentException(
                    "Unsupported audio format for resource: " + resourceName, e);
        } catch (final LineUnavailableException e) {
            throw new IllegalArgumentException(
                    "Audio line unavailable while loading resource: " + resourceName, e);
        }
    }
}
