package it.unibo.geometrybash.commons.io;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public final class ResourceLoader {

    private ResourceLoader() {
    }
    
    public static String loadText(final String path){

        try(InputStream is = ResourceLoader.class.getResourceAsStream(path)) {
            if (is == null) {
                throw new IllegalArgumentException("Resource not found: " + path);
            }


           
        
        return new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining("\n"));
         } catch (Exception e) {
           throw new IllegalStateException("Error loading resource: " + path, e);
        }
    }


}
