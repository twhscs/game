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
        RESOURCE_MANAGER = new ResourceManager("images", "png", "textures", "png", "fonts", "ttf", "sound_buffers", "wav");
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
        final float SECONDS_PER_UPDATE = 1 / 20f;
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

            render(lag / SECONDS_PER_UPDATE);
            framesDrawn++;

            if (frameTime >= 1) {
                int fps = (int) (framesDrawn / frameTime);
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

    //TODO: rename variable f
    private void render(float f) {
        WINDOW.clear();
        //TODO: draw player
        WINDOW.display();
    }
}
