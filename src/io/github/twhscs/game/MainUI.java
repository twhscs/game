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
    heartSprite = new Sprite(heartSpritesheetTexture);
  }
  public void draw(RenderTarget target, RenderStates states) {
    heartSprite.setPosition(new Vector2f(target.getView().getSize().x/2 - 11,10));
    heartSprite.setTextureRect(new IntRect(((int)Player.health)/10*11, 0, 11, 11));
    heartSprite.setScale(new Vector2f(4,4));
    heartSprite.draw(target, new RenderStates(heartSpritesheetTexture));
  }
}
