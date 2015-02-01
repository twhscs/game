package io.github.twhscs.game.ui;

import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;

public class MenuItem extends UserInterfaceElement {
    private final String VALUE;
    private Menu parent;

    public MenuItem(String VALUE) {
        super(VerticalAlignment.CUSTOM, HorizontalAlignment.CENTER);
        this.VALUE = VALUE;
    }

    public void setParent(Menu menu) {
        parent = menu;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void draw(RenderTarget renderTarget, RenderStates renderStates) {

    }
}
