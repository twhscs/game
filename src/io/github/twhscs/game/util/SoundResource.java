package io.github.twhscs.game.util;

import org.jsfml.audio.Sound;
import org.jsfml.audio.SoundBuffer;

import java.io.IOException;

/**
 * Sound resource loader and container.
 * @author Robert
 *
 */
public class SoundResource extends Resource {
  private final Sound sound = new Sound();
  
  private final SoundBuffer buffer = new SoundBuffer();
  
  private final String directorySubfolder = "sounds/";
  
  private final String fileFormat = ".wav";
  
  public SoundResource(String filename) {
    try {
      buffer.loadFromStream(getStream(directorySubfolder + filename + fileFormat));
    } catch (IOException e) {
      e.printStackTrace();
    }
    sound.setBuffer(buffer);
  }
  
  public Sound getSound() {
    return sound;
  }
}
