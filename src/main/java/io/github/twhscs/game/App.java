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

import java.util.ArrayList;
import java.util.List;

class App {
    private final int TILE_SIZE = 32;
    private final float ZOOM = 0.5f;
    private final RenderWindow WINDOW;
    private final ConstView DEFAULT_VIEW;
    private final View GAME_VIEW;
    private final ResourceManager RESOURCE_MANAGER;
    private final Map MAP;
    private final Player PLAYER;
    private final List<Entity> ENTITIES;

    private App() {
        WINDOW = new RenderWindow(new VideoMode(640, 480), "Game");
        DEFAULT_VIEW = WINDOW.getDefaultView();
        GAME_VIEW = new View(DEFAULT_VIEW.getCenter(), DEFAULT_VIEW.getSize());
        GAME_VIEW.zoom(ZOOM);
        // Construct resource manager with main package.
        RESOURCE_MANAGER = new ResourceManager("io.github.twhscs.game", "images", "png", "textures", "png", "fonts", "ttf", "sound_buffers", "wav");
        String[] imageNames = {"icon", "kyle"};
        RESOURCE_MANAGER.loadImages(imageNames);
        // Set the window icon.
        WINDOW.setIcon(RESOURCE_MANAGER.getImage("icon"));
        String[] textureNames = {"player", "tiles", "leviathan", "ryuk", "drax", "hulk"};
        RESOURCE_MANAGER.loadTextures(textureNames);
        String[] fontNames = {"free_mono", "free_sans", "free_serif"};
        RESOURCE_MANAGER.loadFonts(fontNames);
        String[] soundBufferNames = {"collision", "interact_failure", "interact_success"};
        RESOURCE_MANAGER.loadSoundBuffers(soundBufferNames);
        PLAYER = new Player(RESOURCE_MANAGER.getTexture("ryuk"), GAME_VIEW, TILE_SIZE, 4, 2);
        ENTITIES = new ArrayList<Entity>();
        ENTITIES.add(
                new NonPlayableCharacter(RESOURCE_MANAGER.getTexture("ryuk"), TILE_SIZE, 4, 2)
        );
        MAP = new Map(100, 100, TILE_SIZE, ZOOM, 25, RESOURCE_MANAGER.getTexture("tiles"), WINDOW);
        MAP.setPlayer(PLAYER);
        for (Entity e : ENTITIES) {
            MAP.setEntity("test", e);
        }
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
        Both 'lag' and 'frameTime' keep running totals of elapsed seconds in order to determine when to update the game or calculate FPS.
        The user input is then processed.
        Now it is time to determine whether or not to update.
        If 'lag' is less than SECONDS_PER_UPDATE, it is not time to update yet because not enough time has passed.
        If 'lag' is equal to or slightly greater than SECONDS_PER_UPDATE, it is time to update.
        If 'lag' is twice as large as SECONDS_PER_UPDATE or even greater, the game is behind by more than one update.
        To remedy this the game updates multiple times until caught up.
        Finally, the game is rendered.
        If one second has passed, the FPS is calculated.
        
         */

        // Fixed rate at which the game updates.
        // 1 / 20.0f = 20 Hz = 20 updates per second.
        final float SECONDS_PER_UPDATE = 1 / 20.0f;
        Clock clock = new Clock();
        float lag = 0.0f;
        float frameTime = 0.0f;
        int framesDrawn = 0;

        while (WINDOW.isOpen()) {
            float elapsed = clock.restart().asSeconds();
            lag += elapsed;
            frameTime += elapsed;

            processInput();

            while (lag >= SECONDS_PER_UPDATE) {
                update();
                lag -= SECONDS_PER_UPDATE;
            }

            // Render the game, passing in a float between 0.0f and 1.0f.
            // 0.0f represents the last update while 1.0f represents the next update.
            // 'betweenUpdates' represents how far between updates the game is.
            render(lag / SECONDS_PER_UPDATE);
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
        for (Event event : WINDOW.pollEvents()) {
            switch (event.type) {
                case CLOSED:
                    WINDOW.close();
                    break;
                case RESIZED:
                    Vector2i size = event.asSizeEvent().size;
                    GAME_VIEW.reset(new FloatRect(0.0f, 0.0f, size.x, size.y));
                    GAME_VIEW.zoom(ZOOM);
                    PLAYER.updateSprite();
                    for (Entity e : ENTITIES) {
                        e.updateSprite();
                    }
                    break;
                case KEY_PRESSED:
                    switch (event.asKeyEvent().key) {
                    }
                    break;
            }
        }
        if (Keyboard.isKeyPressed(Keyboard.Key.W)) {
            PLAYER.move(Direction.NORTH);
        } else if (Keyboard.isKeyPressed(Keyboard.Key.A)) {
            PLAYER.move(Direction.WEST);
        } else if (Keyboard.isKeyPressed(Keyboard.Key.S)) {
            PLAYER.move(Direction.SOUTH);
        } else if (Keyboard.isKeyPressed(Keyboard.Key.D)) {
            PLAYER.move(Direction.EAST);
        }
    }

    private void update() {
        MAP.update();
        PLAYER.update();
    }

    private void render(float positionBetweenUpdates) {
        WINDOW.setView(GAME_VIEW);
        WINDOW.clear();
        WINDOW.draw(MAP);
        PLAYER.interpolate(positionBetweenUpdates);
        WINDOW.draw(PLAYER);
        WINDOW.display();
    }
}
