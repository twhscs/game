package io.github.twhscs.game.ui;

import org.jsfml.graphics.*;

public class Label extends UserInterfaceElement {

    protected final Text TEXT;
    protected final String LABEL;

    public Label(VerticalAlignment vAlign, HorizontalAlignment hAlign, String LABEL, Font font, int fontSize, Color color) {
        super(vAlign, hAlign);
        this.LABEL = LABEL;
        TEXT = new Text(LABEL, font, fontSize);
        TEXT.setColor(color);
    }

    @Override
    public void initialize() {
        align(TEXT.getLocalBounds().width + TEXT.getLocalBounds().left, TEXT.getLocalBounds().height + TEXT.getLocalBounds().top);
        TEXT.setPosition(alignPosition);
    }

    @Override
    public void draw(RenderTarget renderTarget, RenderStates renderStates) {
        TEXT.draw(renderTarget, renderStates);
    }
}
