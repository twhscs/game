package io.github.twhscs.game;

import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.View;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

/**
 * Camera that can center on various objects.
 * @author Robert
 *
 */
public class Camera {
  /**
   * The window the camera is affecting.
   */
  private RenderWindow window;
  /**
   * The default view.
   */
  private View defaultView;
  
  /**
   * Create a new camera for window w.
   * @param w The window for which to create the camera.
   */
  public Camera(RenderWindow w) {
    window = w;
    defaultView = (View) window.getDefaultView();
  }
  
  /**
   * Center the camera on the position of an animated sprite.
   * @param s The sprite to center on.
   */
  public void centerOn(AnimatedSprite s) {
    // Copy the default view
    View newView = new View(defaultView.getCenter(), defaultView.getSize()); 
    Vector2f spritePos = s.getSprite().getPosition(); // Get the vector position of the sprite
    Vector2i spriteSize = s.getSpriteSize(); // Get the size of the sprite
    // Convert the size to a float
    Vector2f newSpriteSize = new Vector2f(spriteSize.x, spriteSize.y); 
    newSpriteSize = Vector2f.div(newSpriteSize, 2); // Divide the size in half (for centering)
    // Combine half the sprite size with the position
    Vector2f cameraPos = Vector2f.add(spritePos, newSpriteSize); 
    // Round the final position to prevent graphical artifacts
    System.out.println(cameraPos);
    cameraPos = new Vector2f((float)Math.floor(cameraPos.x), (float)Math.floor(cameraPos.y));
    newView.setCenter(cameraPos); // Set the new position
    window.setView(newView); // Set the view
  }
  
  /**
   * Center the camera on the default view.
   */
  public void centerOnDefault() {
    window.setView(defaultView);
  }
}
