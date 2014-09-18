import org.jsfml.graphics.Color;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;


public class main {
	
	public static void main(String[] args) {
		RenderWindow window = new RenderWindow();
		window.create(new VideoMode(640, 480), "Hello World");
		
		// limit framerate
		window.setFramerateLimit(30);
		
		// main loop
		while(window.isOpen()) {
			// fill window with red
			window.clear(Color.RED);
			
			// display what was rawn
			window.display();
			
			// handle events
			for(Event event : window.pollEvents()) {
				if(event.type == Event.Type.CLOSED) {
					// the user pressed the close button
					window.close();
				}
			}
		}

	}

}
