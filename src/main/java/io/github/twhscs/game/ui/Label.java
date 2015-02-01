package io.github.twhscs.game.ui;

import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;

public class Label extends UserInterfaceElement {

    protected final Text TEXT;
    protected final String LABEL;

    public Label(View UI_VIEW, VerticalAlignment vAlign, HorizontalAlignment hAlign, String LABEL, Font font, int fontSize, Color color) {
        super(UI_VIEW, vAlign, hAlign);
        this.LABEL = LABEL;
        TEXT = new Text(LABEL, font, fontSize);
        TEXT.setColor(color);
        align();
    }

    @Override
    public void align() {
        float x = 0;
        float y = 0;
        switch (vAlign) {
            case TOP:
                y = 0;
                break;
            case CENTER:
                y = (UI_VIEW.getSize().y - TEXT.getLocalBounds().top - TEXT.getLocalBounds().height) / 2.0f;
                break;
            case BOTTOM:
                y = UI_VIEW.getSize().y - TEXT.getLocalBounds().top - TEXT.getLocalBounds().height;
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
                x = (UI_VIEW.getSize().x - TEXT.getLocalBounds().left - TEXT.getLocalBounds().width) / 2.0f;
                break;
            case RIGHT:
                x = UI_VIEW.getSize().x - TEXT.getLocalBounds().left - TEXT.getLocalBounds().width;
                break;
            case CUSTOM:
                x = alignPosition.x;
                break;
        }
        alignPosition = new Vector2f(x, y);
        TEXT.setPosition(alignPosition);
    }

    @Override
    public void draw(RenderTarget renderTarget, RenderStates renderStates) {
        TEXT.draw(renderTarget, renderStates);
    }
}
