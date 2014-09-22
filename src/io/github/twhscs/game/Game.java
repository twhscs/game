package io.github.twhscs.game;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.TextStyle;
import org.jsfml.system.Clock;
import org.jsfml.system.Vector2i;
import org.jsfml.window.Keyboard;
import org.jsfml.window.Keyboard.Key;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;

/**
 * The main class of the game. Contains the main loop and pieces everything together.
 * This file should ideally be as short as possible.
 * @author Robert
 *
 */
public class Game { 
  /**
   * Main window where everything is drawn to. Handles all input.
   */
  private final RenderWindow window = new RenderWindow();
  /**
   * Sets the title that displays in the title-bar of the window.
   */
  private final String windowTitle = "Game";
  /**
   * Sets the dimensions (resolution) the window is created with.
   */
  private final Vector2i windowDimensions = new Vector2i(640, 480);
  /**
   * The main object representing the player.
   */
  private final Player player = new Player();
  /**
   * The UI element responsible for displaying the FPS on screen.
   */
  private final UITextElement fpsUI = 
      new UITextElement(InterfacePosition.TOP_LEFT, Color.YELLOW, 24, TextStyle.BOLD);
  /**
   * Represents whether or not the user has the window opened and focused.
   */
  private boolean windowFocus = true;
  
  /**
   * Creates an instance of the game and runs it.
   * @param args Command line arguments passed in at run-time.
   */
  public static void main(String[] args) {
    Game g = new Game();
    g.run();
  }
  
  /**
   * Configures one time settings at start-up.
   */
  public void handleInitialization() {
    window.create(new VideoMode(windowDimensions.x, windowDimensions.y), windowTitle);
    player.changeMap(new Map(10, 10, Tile.SAND));
  }
  
  /**
   * Initializes the game and holds the main loop.
   */
  public void run() {
    handleInitialization();
    int framesDrawn = 0; // Count each frame that is drawn
    float updateRate = 20; // Limit the logic loop to update at 20Hz (times per second)
    Clock updateClock = new Clock(); // Clock used to restrict update loop to a fixed rate
    Clock frameClock = new Clock(); // Clock used to calculate average FPS
    updateClock.restart(); // Reset update clock
    long nextUpdate = 
        updateClock.getElapsedTime().asMilliseconds(); // Calculate next update time in milliseconds
    while (window.isOpen()) { // Run main loop as long as window is open
      handleInput();
      long updateTime = 
          updateClock.getElapsedTime().asMilliseconds(); // Make note of the current update time
      while ((updateTime - nextUpdate) >= updateRate) { // Update loop
        handleLogic();
        nextUpdate += updateRate;  // Computer next appropriate update time
      }
      handleDrawing();
      framesDrawn++; // Increment; a frame has been drawn
      // Calculate how long it has been since last calculating FPS
      float elapsedTime = 
          frameClock.getElapsedTime().asSeconds(); 
      if (elapsedTime >= 1.0f) { // If it has been one second
        // Divide the frames drawn by one second, aka FPS
        fpsUI.updateString("FPS: " + (int) (framesDrawn / elapsedTime));
        framesDrawn = 0; // Reset frame count
        frameClock.restart(); // Reset frame clock
      }
    }
  }
  
  /**
   * Responds to any user input (keyboard or mouse).
   */
  public void handleInput() {
    
    /* Window based event queue (slight ms lag)
     * Good for single-press actions, bad for repeated actions
     */
    
    for (Event event : window.pollEvents()) {
      switch(event.type) {
        case CLOSED:
          window.close(); // Close the window if the user clicks the X button
          break;
        case GAINED_FOCUS:
          windowFocus = true; // Update windowFocus if the user focuses the window
          break;
        case LOST_FOCUS:
          windowFocus = false; // Update windowFocus if the user unfocuses the window
          break;
        default:
          break;
      }
    }
    
    /* Real-time input (no lag)
     * Good for repeated actions, bad for single-press actions
     */
    
    // isKeyPressed will work whether the window is focused or not, therefore we must check manually
    if (windowFocus) { 
      if (Keyboard.isKeyPressed(Key.W)) {
        player.move(Direction.NORTH); // W moves the player up (north)
      } else if (Keyboard.isKeyPressed(Key.S)) {
        player.move(Direction.SOUTH); // S moves the player down (south)
      } else if (Keyboard.isKeyPressed(Key.A)) {
        player.move(Direction.WEST); // A moves the player left (west)
      } else if (Keyboard.isKeyPressed(Key.D)) {
        player.move(Direction.EAST); // D moves the player right (east)
      }
    }
    
  }
  
  /**
   * Updates at a fixed rate (20Hz).
   */
  public void handleLogic() {
    player.update();
  }
  
  /**
   * Updates as fast as possible. Draws all objects onto the screen.
   */
  public void handleDrawing() {
    // The window has automatic double buffering
    window.clear(); // Wipe everything from the window
    // Draw each object like layers, background to foreground
    window.draw(player.getMap()); 
    window.draw(player);
    window.draw(fpsUI);
    window.display(); // Show the window to the user
  }
}
