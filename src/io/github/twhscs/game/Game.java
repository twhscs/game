package io.github.twhscs.game;

import java.io.IOException;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.Font;
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
 * @author Robert
 *
 */
public class Game { 
  private RenderWindow renderWindow = new RenderWindow();
  private final String renderWindowTitle = "Game";
  private final Vector2i renderWindowDimensions = new Vector2i(640, 480);
  private Player player;
  private boolean renderWindowFocused = true;
  private Font pixel = new Font();
  private int fps;
  private static boolean limitFPS = false;
  private UITextElement fpsCounter;
  
  public static void main(String[] args) {
    Game g = new Game();
    g.run();
  }
  
  public void handleInitialization() {
    
    try {
      Settings.grabSettings();
    } catch(IOException ex) {
      ex.printStackTrace();
    }
    limitFPS = Settings.List.get("limitfps").equals("true");
    String[] resolution = Settings.List.get("resolution").split("x");
    renderWindow.create(new VideoMode(Integer.parseInt(resolution[0]),Integer.parseInt(resolution[1])), renderWindowTitle);
    
    fpsCounter = new UITextElement(InterfacePosition.TOP_LEFT, Color.YELLOW, 24, TextStyle.BOLD);
    if(limitFPS) {
      renderWindow.setFramerateLimit(Integer.parseInt(Settings.List.get("fps")));
    }
    player = new Player();
    player.changeMap(new Map(10, 10, Tile.SAND));
  }
  
  public void run() {
    handleInitialization();
    int framesDrawn = 0;
    float updateRate = 20.0f; // 20 hz ( i think)
    long maxUpdates = 1; // ???
    Clock updateClock = new Clock();
    Clock frameClock = new Clock();
    updateClock.restart();
    long nextUpdate = updateClock.getElapsedTime().asMilliseconds();
    while (renderWindow.isOpen()) {
      long updates = 0;
      handleInput();
      long updateTime = updateClock.getElapsedTime().asMilliseconds();
      while((updateTime - nextUpdate) >= updateRate && updates++ < maxUpdates) {
        handleLogic();
        nextUpdate += updateRate;
      }
      handleDrawing();
      framesDrawn++;
      
      float elapsedTime = frameClock.getElapsedTime().asSeconds();
      if(elapsedTime >= 1.0f) {
        fps = (int) (framesDrawn / elapsedTime);
        fpsCounter.updateString("FPS: " + fps);
        framesDrawn = 0;
        frameClock.restart();
      }
    }
  }
  
  public void handleInput() {
    for (Event event : renderWindow.pollEvents()) {
      switch(event.type) {
        case CLOSED:
          renderWindow.close();
          break;
        case GAINED_FOCUS:
          renderWindowFocused = true;
          break;
        case LOST_FOCUS:
          renderWindowFocused = false;
        default:
          break;
      }
    }
    
    if(renderWindowFocused) {
      if(Keyboard.isKeyPressed(Key.W)) {
        player.move(Direction.NORTH);
      } else if(Keyboard.isKeyPressed(Key.S)) {
        player.move(Direction.SOUTH);
      } else if(Keyboard.isKeyPressed(Key.A)) {
        player.move(Direction.WEST);
      } else if(Keyboard.isKeyPressed(Key.D)) {
        player.move(Direction.EAST);
      }
    }
    
  }
  
  public void handleLogic() {
    player.update();
  }
  
  public void handleDrawing() {
    renderWindow.clear();
    renderWindow.draw(player.getMap());
    renderWindow.draw(player);
    renderWindow.draw(fpsCounter);
    renderWindow.display();
  }
}
