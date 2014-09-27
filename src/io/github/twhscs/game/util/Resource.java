package io.github.twhscs.game.util;

import java.io.InputStream;

/**
 * Base resource loader and container.
 * @author Robert
 *
 */
public abstract class Resource {
  
  protected InputStream getStream(String path) {
    return getClass().getClassLoader().getResourceAsStream(path);
  }
  
}
