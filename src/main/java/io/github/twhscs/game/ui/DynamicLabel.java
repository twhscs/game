package io.github.twhscs.game.ui;

import org.jsfml.graphics.*;

public class DynamicLabel<E> extends UserInterfaceElement {
    private final String LABEL;
    private final Text TEXT;
    private final View UI_VIEW;
    private E value;

    public DynamicLabel(String LABEL, E value, VerticalAlignment vAlign, HorizontalAlignment hAlign, Font font, int fontSize, Color color, View UI_VIEW) {
        this.LABEL = LABEL;
        this.value = value;
        this.vAlign = vAlign;
        this.hAlign = hAlign;
        this.TEXT = new Text(LABEL + " " + value.toString(), font, fontSize);
        this.UI_VIEW = UI_VIEW;
        TEXT.setColor(color);
        align();
    }

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
        }
        TEXT.setPosition(x, y);
    }

    public void updateValue(E value) {
        this.value = value;
        TEXT.setString(LABEL + " " + value.toString());
        align();
    }

    @Override
    public void draw(RenderTarget renderTarget, RenderStates renderStates) {
        TEXT.draw(renderTarget, renderStates);
    }
}
