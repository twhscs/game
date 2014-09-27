package io.github.twhscs.game;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.TextStyle;
import org.jsfml.system.Clock;
import org.jsfml.system.Vector2i;
import org.jsfml.window.ContextSettings;
import org.jsfml.window.Joystick;
import org.jsfml.window.Joystick.Axis;
import org.jsfml.window.Keyboard;
import org.jsfml.window.Keyboard.Key;
import org.jsfml.window.VideoMode;
import org.jsfml.window.WindowStyle;
import org.jsfml.window.event.Event;

import io.github.twhscs.game.ui.DialogueUIElement;
import io.github.twhscs.game.ui.MainMenu;
import io.github.twhscs.game.ui.TextUIElement;
import io.github.twhscs.game.ui.UIPosition;
import io.github.twhscs.game.util.ImageResource;

/**
 * The main class of the game. Contains the main loop and pieces everything
 * together. This file should ideally be as short as possible.
 * 
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
    private Player player;
    /**
     * The UI element responsible for displaying the FPS on screen.
     */
    private final TextUIElement fpsUI = new TextUIElement(UIPosition.TOP_LEFT, Color.YELLOW, 24, TextStyle.BOLD);
    /**
     * Represents whether or not the user has the window opened and focused.
     */
    private boolean windowFocus = true;
    /**
     * Allows the window to shift focus.
     */
    private Camera camera;
    /**
     * The current map the player is on. This is the map that will be rendered.
     */
    private Map currentMap;
    /**
     * A UI element for displaying dialogue between NPCs and players.
     */
    private final DialogueUIElement dialogueUI = new DialogueUIElement(windowDimensions);
    /**
     * The level of anti-aliasing for the GPU to use when rendering. From the
     * crude tests I've performed this appears to do nothing...
     */
    private final int antialiasingLevel = 0;

    private boolean usingController;

    /**
     * The main menu of the game
     */
    private MainMenu menu;

    /**
     * Creates an instance of the game and runs it.
     * 
     * @param args
     *            Command line arguments passed in at run-time.
     */
    public static void main(String[] args) {
	Game g = new Game();
	g.run();
    }

    /**
     * Configures one time settings at start-up.
     */
    public void handleInitialization() {
	// Set the resolution
	VideoMode videoMode = new VideoMode(windowDimensions.x, windowDimensions.y);
	// Set the window options
	int windowStyle = WindowStyle.CLOSE | WindowStyle.TITLEBAR;
	// Create a new window with specified resolution, options and
	// anti-aliasing
	window.create(videoMode, windowTitle, windowStyle, new ContextSettings(antialiasingLevel));
	menu = new MainMenu(windowDimensions, window); // Create the main menu
	currentMap = new Map(10, 10, Tile.SAND); // Create the main map
	player = new Player(currentMap.getRandomValidLocation(), dialogueUI); // Create
									      // the
									      // player
	currentMap.addEntity(player); // Add the player to the map
	// Create NPC and add dialogue
	NonPlayerCharacter npc = new NonPlayerCharacter(currentMap.getRandomValidLocation(), "Creatine Chris", "npc3");
	npc.addDialogue("Getting swole is my life.");
	npc.addDialogue("That's why I consume copious amounts of creatine.");
	currentMap.addEntity(npc); // Add the NPC to the map
	// Get a random location and create another NPC there
	Location randLoc = currentMap.getRandomValidLocation();
	currentMap.addEntity(new NonPlayerCharacter(randLoc, "Joe", "npc2"));
	// Create a delicious and non-kosher NPC
	NonPlayerCharacter pig = new NonPlayerCharacter(currentMap.getRandomValidLocation(), "Porky Chase", "npc4");
	// Add some dialogue guaranteed to make you squeal. Are you boared yet?
	pig.addDialogue("I'll send you to the slaughterhouse!");
	// Finally add the NPC to the map, with lots of hogs and kisses
	currentMap.addEntity(pig);
	// Create another NPC bursting with swag
	NonPlayerCharacter swag = new NonPlayerCharacter(currentMap.getRandomValidLocation(), "Ryan May", "npc5");
	// The swag lord indeed. A fitting title.
	swag.addDialogue("They call me the swag lord.");
	// This map just got swagged out
	currentMap.addEntity(swag);
	// Get another random location
	Location randLoc2 = currentMap.getRandomValidLocation();
	// This NPC is so smart IRL he won't even need to read this comment
	NonPlayerCharacter hunter = new NonPlayerCharacter(randLoc2, "Hunter \"Skumbag\" Brown", "npc6");
	// Master programmers never read comments, right Hunter?
	hunter.addDialogue("I'm a master programmer!");
	hunter.addDialogue("For my mommy tells me so!");
	hunter.addDialogue("Right Mr. S?");
	// Add this master programmer to the map
	currentMap.addEntity(hunter);
	// This line may or not have been "borrowed"
	NonPlayerCharacter ke = new NonPlayerCharacter(currentMap.getRandomValidLocation(), "Ke Ma", "npc7");
	// So believable
	ke.addDialogue("That's definitely my code Mr. Smith, I swear.");
	currentMap.addEntity(ke); // You know the drill
	camera = new Camera(window); // Create the default camera
	// window.setFramerateLimit(60);
	window.setKeyRepeatEnabled(false); // Prevent user from holding down
					   // keys in menus
	ImageResource icon = new ImageResource("icon"); // Create a shiny new
							// icon image
	window.setIcon(icon.getImage()); // Set the window to use this shiny new
					 // icon
	usingController = Joystick.isConnected(0);
    }

    /**
     * Initializes the game and holds the main loop. Magic below. (Not really)
     */
    public void run() {
	handleInitialization(); // Initial configuration of various things
	int framesDrawn = 0; // Count each frame that is drawn
	float updateRate = 20; // Limit the logic loop to update at 20Hz (times
			       // per second)
	Clock updateClock = new Clock(); // Clock used to restrict update loop
					 // to a fixed rate
	Clock frameClock = new Clock(); // Clock used to calculate average FPS
	updateClock.restart(); // Reset update clock
	long nextUpdate = updateClock.getElapsedTime().asMilliseconds(); // Calculate
									 // next
									 // update
									 // time
									 // in
									 // milliseconds
	while (window.isOpen()) { // Run main loop as long as window is open
	    handleInput(); // Process input
	    long updateTime = updateClock.getElapsedTime().asMilliseconds(); // Make
									     // note
									     // of
									     // the
									     // current
									     // update
									     // time
	    while ((updateTime - nextUpdate) >= updateRate) { // Update loop
		handleLogic(); // Process fixed-time logic
		nextUpdate += updateRate; // Computer next appropriate update
					  // time
	    }
	    handleDrawing(); // Draw everything to the window
	    framesDrawn++; // Increment; a frame has been drawn
	    // Calculate how long it has been since last calculating FPS
	    float elapsedTime = frameClock.getElapsedTime().asSeconds();
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

	/*
	 * Window based event queue (slight ms lag) Good for single-press
	 * actions, bad for repeated actions
	 */

	for (Event event : window.pollEvents()) {
	    switch (event.type) {
	    case CLOSED:
		window.close(); // Close the window if the user clicks the X
				// button
		break;
	    case GAINED_FOCUS:
		windowFocus = true; // Update windowFocus if the user focuses
				    // the window
		break;
	    case LOST_FOCUS:
		windowFocus = false; // Update windowFocus if the user
				     // un-focuses the window
		break;
	    case KEY_PRESSED:
		if (!usingController) {
		    switch (event.asKeyEvent().key) { // If a non-movement key
						      // is pressed
		    case E:
		    case NUMPAD9:
			if (player.getCurrentAction() == PlayerAction.NONE
				|| player.getCurrentAction() == PlayerAction.TALKING) {
			    player.interact(); // Interact with entities by
					       // pressing E
			} else if (player.getCurrentAction() == PlayerAction.IN_MENU) {
			    // select the current option
			    menu.selectCurrentOption();
			}
			break;
		    case ESCAPE:
			if (player.getCurrentAction() == PlayerAction.NONE) {
			    menu.setVisible(true); // Set the menu's visibility
						   // opposite to the current
						   // visibility
			    player.setCurrentAction(PlayerAction.IN_MENU);
			} else if (player.getCurrentAction() == PlayerAction.IN_MENU) {
			    menu.setVisible(false);
			    player.setCurrentAction(PlayerAction.NONE);
			}
			break;
		    case W:
			if (player.getCurrentAction() == PlayerAction.IN_MENU) {
			    menu.goUp(); // Move the selected button up one
			}
			break;
		    case S:
			if (player.getCurrentAction() == PlayerAction.IN_MENU) {
			    menu.goDown(); // Move the selected button down one
			}
			break;
		    default:
			break;
		    }
		}
		break;
	    case JOYSTICK_BUTTON_PRESSED:
		switch (event.asJoystickButtonEvent().button) {
		case 0:
		    player.interact();
		    break;
		}
		break;
	    case JOYSTICK_CONNECETED:
		usingController = true;
		break;
	    case JOYSTICK_DISCONNECTED:
		usingController = false;
		break;
	    }
	}

	/*
	 * Real-time input (no lag) Good for repeated actions, bad for
	 * single-press actions Chances are you should be adding your key above,
	 * NOT below
	 */

	// isKeyPressed will work whether the window is focused or not,
	// therefore we must check manually
	if (windowFocus && player.getCurrentAction() != PlayerAction.IN_MENU) {
	    if (!usingController) {
		if (Keyboard.isKeyPressed(Key.W) || Keyboard.isKeyPressed(Key.NUMPAD8)) {
		    player.move(Direction.NORTH); // W moves the player up
						  // (north)
		} else if (Keyboard.isKeyPressed(Key.S) || Keyboard.isKeyPressed(Key.NUMPAD5)) {
		    player.move(Direction.SOUTH); // S moves the player down
						  // (south)
		} else if (Keyboard.isKeyPressed(Key.A) || Keyboard.isKeyPressed(Key.NUMPAD4)) {
		    player.move(Direction.WEST); // A moves the player left
						 // (west)
		} else if (Keyboard.isKeyPressed(Key.D) || Keyboard.isKeyPressed(Key.NUMPAD6)) {
		    player.move(Direction.EAST); // D moves the player right
						 // (east)
		}
	    } else {
		if (Joystick.getAxisPosition(0, Axis.POV_X) == 100) {
		    player.move(Direction.NORTH); // W moves the player up
						  // (north)
		} else if (Joystick.getAxisPosition(0, Axis.POV_X) == -100) {
		    player.move(Direction.SOUTH); // S moves the player down
						  // (south)
		} else if (Joystick.getAxisPosition(0, Axis.POV_Y) == 100) {
		    player.move(Direction.EAST); // D moves the player right
						 // (east)
		} else if (Joystick.getAxisPosition(0, Axis.POV_Y) == -100) {
		    player.move(Direction.WEST); // A moves the player left
						 // (west)
		}
	    }
	}

    }

    /**
     * Updates at a fixed rate (20Hz).
     */
    public void handleLogic() {
	currentMap.updateAllEntities(); // Call each the update method for each
					// entity
    }

    /**
     * Updates as fast as possible. Draws all objects onto the screen.
     */
    public void handleDrawing() {
	// The window has automatic double buffering
	window.clear(); // Wipe everything from the window
	// Draw each object like layers, background to foreground
	camera.centerOn(player.getAnimatedSprite()); // Draw everything relative
						     // to player, centering it
	window.draw(currentMap); // Draw the map
	currentMap.drawAllEntities(window); // Draw each entity, sorted by
					    // y-value
	camera.centerOnDefault(); // Stop drawing relative to the player
	if (dialogueUI.isVisible()) {
	    window.draw(dialogueUI); // Draw the dialogue UI if it has
				     // visibility (active)
	}
	if (menu.isVisible()) {
	    window.draw(menu); // Draw the main menu if it is visible
	}
	window.draw(fpsUI); // Draw the FPS counter
	window.display(); // Show the window to the user
    }
}
