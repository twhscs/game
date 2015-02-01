package io.github.twhscs.game.ui;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.Font;
import org.jsfml.system.Vector2f;

public class DynamicLabel<E> extends Label {

    private E value;

    public DynamicLabel(VerticalAlignment vAlign, HorizontalAlignment hAlign, String LABEL, Font font, int fontSize, Color color, E value) {
        super(vAlign, hAlign, LABEL, font, fontSize, color);
        this.value = value;
    }

    @Override
    public void initialize() {
        updateValue(value);
    }

    public void updateValue(E value) {
        this.value = value;
        if (value instanceof Vector2f) {
            Vector2f position = (Vector2f) value;
            TEXT.setString(LABEL + " (" + Math.round(position.x) + ", " + Math.round(position.y) + ")");
        } else {
            TEXT.setString(LABEL + " " + value.toString());
        }
        super.initialize();
    }
}
