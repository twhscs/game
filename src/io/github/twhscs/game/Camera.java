package io.github.twhscs.game;

import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.View;
import org.jsfml.system.Vector2f;

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
   * Center the camera on the following position.
   * @param pos The position to center the camera on.
   * @param step Kyle ?
   */
  public void centerOn(Vector2f pos, float step) {
    /* View newView = 
     * new View(vectorLerp(defaultView.getCenter(), pos, step), defaultView.getSize());
     */
    View newView = new View(defaultView.getCenter(), defaultView.getSize());
    newView.setCenter(pos);
    window.setView(newView);
  }
  
  /**
   * Center the camera on the default view.
   */
  public void centerOnDefault() {
    window.setView(defaultView);
  }
  
  /**
   * Kyle ?
   * @param x0
   * @param x1
   * @param m
   * @return
   */
  public float lerp(float x0, float x1, float m) {
    return x0 + m * (x1 - x0);
  }
  
  /**
   * Kyle ?
   * @param v0
   * @param v1
   * @param m
   * @return
   */
  public Vector2f vectorLerp(Vector2f v0, Vector2f v1, float m) {
    return new Vector2f(lerp(v0.x, v1.x, m), lerp(v0.y, v1.y, m));
  }
}
