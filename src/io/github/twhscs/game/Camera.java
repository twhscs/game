package io.github.twhscs.game;

import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.View;
import org.jsfml.system.Vector2f;

public class Camera {
  public int[] currentPos = {0,0};
  private static RenderWindow window;
  public Camera(RenderWindow newWindow) {
    window = newWindow;
  }
  public static void SetWindow(RenderWindow newWindow) {
    window = newWindow;
  }
  public static void MoveTo(Vector2f toLoc, float step) {
    View defaultView = (View) window.getDefaultView();
    View view = new View(vectorLerp(defaultView.getCenter(), toLoc, step), defaultView.getSize());
    window.setView(view);
  }
  public static float Lerp(float x0, float x1, float m) {
    return x0 + m * (x1 - x0);
  }
  public static Vector2f vectorLerp(Vector2f v0, Vector2f v1, float m) {
    return new Vector2f(Lerp(v0.x, v1.x, m), Lerp(v0.y, v1.y, m));
  }
  public static void Rotate(){}
}
