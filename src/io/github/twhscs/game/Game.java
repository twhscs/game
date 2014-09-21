package io.github.twhscs.game;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.Font;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Text;
import org.jsfml.system.Clock;
import org.jsfml.system.Vector2i;
import org.jsfml.window.Keyboard;
import org.jsfml.window.Keyboard.Key;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;

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
  private ArrayList<Map> maps = new ArrayList<Map>();
  private boolean windowFocused = true;
  private Font pixel = new Font();
  private int fps;
  private static boolean limitFPS = false;
  
  public static void main(String[] args) {
    Game g = new Game();
    g.run();
  }
  
  public Map getRandomMap() {
    Collections.shuffle(maps);
    return maps.get(0);
  }
  
  public void handleInitialization() {
    try {
      pixel.loadFromFile(Paths.get("resources/kenpixel.ttf"));
    } catch(IOException ex) {
      ex.printStackTrace();
    }
    renderWindow.create(new VideoMode(renderWindowDimensions.x, renderWindowDimensions.y), 
                                                                    renderWindowTitle);
    if(limitFPS) {
      renderWindow.setFramerateLimit(60);
    }
    player = new Player();

    maps.add(new Map(10, 10, Tile.SAND));
    maps.add(new Map (5, 4, Tile.WATER));
    maps.add(new Map(15, 20, Tile.GRASS));
    
    player.changeMap(getRandomMap());
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
          windowFocused = true;
          break;
        case LOST_FOCUS:
          windowFocused = false;
        default:
          break;
      }
    }
    
    if(windowFocused) {
      if(Keyboard.isKeyPressed(Key.W)) {
        player.move(Direction.NORTH);
      } else if(Keyboard.isKeyPressed(Key.S)) {
        player.move(Direction.SOUTH);
      } else if(Keyboard.isKeyPressed(Key.A)) {
        player.move(Direction.WEST);
      } else if(Keyboard.isKeyPressed(Key.D)) {
        player.move(Direction.EAST);
      }
      
      if(Keyboard.isKeyPressed(Key.N)) {
        player.resetLocation();
        player.changeMap(getRandomMap());
      }
    }
    
  }
  
  public void handleLogic() {
    player.update();
  }
  
  public void handleDrawing() {
    Text fpsCount = new Text("FPS: " + fps, pixel, 24);
    fpsCount.setColor(Color.YELLOW);
    fpsCount.setStyle(Text.BOLD);
    fpsCount.setPosition(0, 0);
    renderWindow.clear();
    renderWindow.draw(player.getMap());
    renderWindow.draw(player);
    renderWindow.draw(fpsCount);
    renderWindow.display();
  }
}
