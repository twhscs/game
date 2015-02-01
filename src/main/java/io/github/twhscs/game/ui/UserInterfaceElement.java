package io.github.twhscs.game.ui;

import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.View;
import org.jsfml.system.Vector2f;

public abstract class UserInterfaceElement implements Drawable {
    protected final View UI_VIEW;
    protected VerticalAlignment vAlign;
    protected HorizontalAlignment hAlign;
    protected Vector2f alignPosition;

    public UserInterfaceElement(View UI_VIEW, VerticalAlignment vAlign, HorizontalAlignment hAlign) {
        this.UI_VIEW = UI_VIEW;
        this.vAlign = vAlign;
        this.hAlign = hAlign;
    }

    public abstract void align();
}
