package io.github.twhscs.game.ui;

import org.jsfml.graphics.Drawable;

public abstract class UserInterfaceElement implements Drawable {
    protected VerticalAlignment vAlign;
    protected HorizontalAlignment hAlign;

    public abstract void align();
}
