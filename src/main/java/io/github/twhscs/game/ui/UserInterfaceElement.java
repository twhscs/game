package io.github.twhscs.game.ui;

import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.View;
import org.jsfml.system.Vector2f;

public abstract class UserInterfaceElement implements Drawable {
    protected View uiView;
    protected VerticalAlignment vAlign;
    protected HorizontalAlignment hAlign;
    protected Vector2f alignPosition;

    public UserInterfaceElement(VerticalAlignment vAlign, HorizontalAlignment hAlign) {
        this.vAlign = vAlign;
        this.hAlign = hAlign;
    }

    @Override
    public String toString() {
        return "UserInterfaceElement{" +
                "uiView=" + uiView +
                ", vAlign=" + vAlign +
                ", hAlign=" + hAlign +
                ", alignPosition=" + alignPosition +
                '}';
    }

    public void setView(View uiView) {
        this.uiView = uiView;
    }

    public boolean viewLoaded() {
        return uiView != null;
    }

    public abstract void initialize();

    public void align(float width, float height) {
        float x = 0;
        float y = 0;
        switch (vAlign) {
            case TOP:
                y = 0;
                break;
            case CENTER:
                y = (uiView.getSize().y - height) / 2.0f;
                break;
            case BOTTOM:
                y = uiView.getSize().y - height;
                break;
            case CUSTOM:
                y = alignPosition.y;
                break;
        }
        switch (hAlign) {
            case LEFT:
                x = 0;
                break;
            case CENTER:
                x = (uiView.getSize().x - width) / 2.0f;
                break;
            case RIGHT:
                x = uiView.getSize().x - width;
                break;
            case CUSTOM:
                x = alignPosition.x;
                break;
        }
        alignPosition = new Vector2f(x, y);
    }
}
