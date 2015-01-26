package io.github.twhscs.game;

import io.github.twhscs.game.util.ResourceManager;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Clock;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;

class App {
    private final RenderWindow WINDOW;
    private final Player PLAYER;
    private final ResourceManager RESOURCE_MANAGER;

    private App() {
        WINDOW = new RenderWindow();
        PLAYER = new Player();
        RESOURCE_MANAGER = new ResourceManager("io.github.twhscs.game", "images", "png", "textures", "png", "fonts", "ttf", "sound_buffers", "wav");
        WINDOW.create(new VideoMode(640, 480), "Game");
        String[] imageNames = {"icon", "kyle"};
        String[] textureNames = {"player", "tiles"};
        String[] fontNames = {"free_mono", "free_sans", "free_serif"};
        String[] soundBufferNames = {"collision", "interact_failure", "interact_success"};
        RESOURCE_MANAGER.loadImages(imageNames);
        RESOURCE_MANAGER.loadTextures(textureNames);
        RESOURCE_MANAGER.loadFonts(fontNames);
        RESOURCE_MANAGER.loadSoundBuffers(soundBufferNames);
        WINDOW.setIcon(RESOURCE_MANAGER.getImage("icon"));
        run();
    }

    public static void main(String[] args) {
        new App();
    }

    private void run() {
        
        /*
        
        Game Loop: http://gameprogrammingpatterns.com/game-loop.html
        
        The game loop runs as long as the window is open. Each iteration the elapsed time of the previous iteration is measured in seconds and stored in 'elapsed'. This elapsed time is added to both 'lag' and 'frameTime'. Both 'lag' and 'frameTime' keep running totals of elapsed seconds in order to determine when to update the game or calculate FPS. The user input is then processed. Now it is time to determine whether or not to update. If 'lag' is less than SECONDS_PER_UPDATE, it is not time to update yet because not enough time has passed. If 'lag' is equal to or slightly greater than SECONDS_PER_UPDATE, it is time to update. If 'lag' is twice as large as SECONDS_PER_UPDATE or even greater, the game is behind by more than one update. To remedy this the game updates multiple times until caught up. Finally, the game is rendered. If one second has passed, the FPS is calculated.
        
         */

        final float SECONDS_PER_UPDATE = 1 / 20f; // Fixed rate at which the game updates. 1 / 20f = 20 Hz = 20 updates per second.
        Clock clock = new Clock();
        float lag = 0f;
        float frameTime = 0f;
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

            render(lag / SECONDS_PER_UPDATE); // Render the game passing in a float between 0.0 and 1.0. 0.0 represents the last update while 1.0 represents the next update. 'betweenUpdates' represents how far between updates the game is.
            framesDrawn++;

            if (frameTime >= 1) {
                int fps = (int) (framesDrawn / frameTime); // FPS = frames per second
                System.out.println("FPS: " + fps);
                framesDrawn = 0;
                frameTime = 0f;
            }
        }
    }

    private void processInput() {
        for (Event event : WINDOW.pollEvents()) {
            switch (event.type) {
                case CLOSED:
                    WINDOW.close();
                    break;
            }
        }
    }

    private void update() {
        //TODO: add update method
    }

    private void render(float betweenUpdates) {
        WINDOW.clear();
        //TODO: draw player
        WINDOW.display();
    }
}
