package io.github.twhscs.game;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Vector2i;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.KeyEvent;

/**
 * 
 * @author Robert
 *
 */

public class Game {
  
  /**
   * main window where everything is drawn to
   */
  private RenderWindow window = new RenderWindow();
  
  /**
   * window title
   */
  private final String windowTitle = "Game";
  
  /**
   * screen resolution (width, height)
   */
  private final Vector2i screenResolution = new Vector2i(640, 480);
  
  /**
   * local player
   */
  private Player player = new Player();
  
  public static void main(String[] args) {
    Game g = new Game(); // create temporary object of self
    g.run(); // invoke run
  }
  
  /**
   * start running game
   */
  public void run() {
    initialize();
    
    // main loop
    while (window.isOpen()) {
      window.clear(Color.BLACK);
      window.draw(player.getSprite());
      window.display();
      
      // event handling
      for (Event event : window.pollEvents()) {
        if (event.type == Event.Type.CLOSED) {
          // user clicked window close (x) button
          window.close();
        } else if (event.type == Event.Type.KEY_PRESSED) {
          KeyEvent keyEv = event.asKeyEvent();
          switch(keyEv.key) {
            case W:
              player.move(Direction.NORTH);
              break;
            case S:
              player.move(Direction.SOUTH);
              break;
            case A:
              player.move(Direction.WEST);
              break;
            case D:
              player.move(Direction.EAST);
          }
        }
      }
    }
  }
  
  /**
   * configure settings once at startup
   */
  public void initialize() {
    window.create(new VideoMode(screenResolution.x, screenResolution.y), windowTitle);
  }

}
