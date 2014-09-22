package io.github.twhscs.game;

import java.io.IOException;
import java.nio.file.Paths;

import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;

public class MainUI implements Drawable {
  private Texture heartSpritesheetTexture = new Texture();
  private Sprite heartSprite = new Sprite();
  public MainUI() {
    try {
      heartSpritesheetTexture.loadFromFile(Paths.get("resources/Life.png"));
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }
  public IntRect getTextureCoords() {
    return new IntRect(((10 - (int)Player.health/10))*11, 0, 11, 11);
  }
  public void draw(RenderTarget target, RenderStates states) {
    heartSprite.setPosition(new Vector2f(0,0));
    heartSprite.setTextureRect(getTextureCoords());
    RenderStates newStates = new RenderStates(heartSpritesheetTexture);
    heartSprite.draw(target, newStates);
  }
}
