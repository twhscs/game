package io.github.twhscs.game;

import io.github.twhscs.game.util.Direction;
import io.github.twhscs.game.util.ResourceManager;
import org.jsfml.graphics.ConstView;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.View;
import org.jsfml.system.Clock;
import org.jsfml.system.Vector2i;
import org.jsfml.window.Keyboard;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;

class App {

    private static final int TILE_SIZE = 32;
    private static final float ZOOM = 0.5f;
    private final RenderWindow window;
    private final ConstView defaultView;
    private final View gameView;
    private final Map map;
    private final Player player;

    private App() {
        ResourceManager.loadImages(new String[]{"icon"});
        ResourceManager.loadTextures(new String[]{"ryuk", "tiles"});

        window = new RenderWindow(new VideoMode(640, 480), "Game");
        window.setIcon(ResourceManager.getImage("icon"));

        defaultView = window.getDefaultView();
        gameView = new View(defaultView.getCenter(), defaultView.getSize());
        gameView.zoom(ZOOM);

        player = new Player(ResourceManager.getTexture("ryuk"), gameView, TILE_SIZE, 4, 2);
        map = new Map(new IslandGenerator(new Vector2i(100, 100), 3), TILE_SIZE, ZOOM, 25,
                      ResourceManager.getTexture("tiles"), window, 3, 3);
        map.setPlayer(player);
        
        // Start the main loop.
        run();
    }

    public static void main(String[] args) {
        new App();
    }

    private void run() {
        
        /*
        
        Game Loop: http://gameprogrammingpatterns.com/game-loop.html
        
        The game loop runs as long as the window is open.
        Each iteration the elapsed time of the previous iteration is measured in seconds and stored in 'elapsed'.
        This elapsed time is added to both 'lag' and 'frameTime'.
        Both 'lag' and 'frameTime' keep running totals of elapsed seconds in order to determine when to update the 
        game or calculate FPS.
        The user input is then processed.
        Now it is time to determine whether or not to update.
        If 'lag' is less than secondsPerUpdate, it is not time to update yet because not enough time has passed.
        If 'lag' is equal to or slightly greater than secondsPerUpdate, it is time to update.
        If 'lag' is twice as large as secondsPerUpdate or even greater, the game is behind by more than one update.
        To remedy this the game updates multiple times until caught up.
        Finally, the game is rendered.
        If one second has passed, the FPS is calculated.
        
         */

        // Fixed rate at which the game updates.
        // 1 / 20.0f = 20 Hz = 20 updates per second.
        final float secondsPerUpdate = 1 / 20.0f;
        Clock clock = new Clock();
        float lag = 0.0f;
        float frameTime = 0.0f;
        int framesDrawn = 0;

        while (window.isOpen()) {
            float elapsed = clock.restart().asSeconds();
            lag += elapsed;
            frameTime += elapsed;

            processInput();

            while (lag >= secondsPerUpdate) {
                update();
                lag -= secondsPerUpdate;
            }

            // Render the game, passing in a float between 0.0f and 1.0f.
            // 0.0f represents the last update while 1.0f represents the next update.
            // 'betweenUpdates' represents how far between updates the game is.
            render(lag / secondsPerUpdate);
            framesDrawn++;

            if (frameTime >= 1.0) {
                // FPS = frames per second.
                int fps = (int) (framesDrawn / frameTime);
                System.out.println("FPS: " + fps);
                framesDrawn = 0;
                frameTime = 0.0f;
            }
        }
    }

    private void processInput() {
        for (Event event : window.pollEvents()) {
            switch (event.type) {
                case CLOSED:
                    window.close();
                    break;
                case RESIZED:
                    Vector2i size = event.asSizeEvent().size;
                    gameView.reset(new FloatRect(0.0f, 0.0f, size.x, size.y));
                    gameView.zoom(ZOOM);
                    player.updateSprite();
                    break;
                case KEY_PRESSED:
                    switch (event.asKeyEvent().key) {
                    }
                    break;
            }
        }
        if (Keyboard.isKeyPressed(Keyboard.Key.W)) {
            player.move(Direction.NORTH);
        } else if (Keyboard.isKeyPressed(Keyboard.Key.A)) {
            player.move(Direction.WEST);
        } else if (Keyboard.isKeyPressed(Keyboard.Key.S)) {
            player.move(Direction.SOUTH);
        } else if (Keyboard.isKeyPressed(Keyboard.Key.D)) {
            player.move(Direction.EAST);
        }
    }

    private void update() {
        map.update();
        player.update();
    }

    private void render(float positionBetweenUpdates) {
        window.setView(gameView);
        window.clear();
        window.draw(map);
        player.interpolate(positionBetweenUpdates);
        window.draw(player);
        window.display();
    }

}
