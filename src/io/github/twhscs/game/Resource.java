package io.github.twhscs.game;

import java.io.InputStream;

/**
 * Base resource loader and container.
 * @author Robert
 *
 */
public abstract class Resource {
  
  protected InputStream getStream(String path) {
    InputStream stream = 
        getClass().getClassLoader().getResourceAsStream(path);
    return stream;
  }
}
