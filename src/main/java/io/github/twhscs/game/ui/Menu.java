package io.github.twhscs.game.ui;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.system.Vector2f;

import java.util.ArrayList;

public class Menu extends UserInterfaceElement {
    private final ArrayList<MenuItem> MENU_ITEMS;
    private final String TITLE;
    private final RectangleShape BACKGROUND;
    private boolean active;

    public Menu(VerticalAlignment vAlign, HorizontalAlignment hAlign, String TITLE) {
        super(vAlign, hAlign);
        MENU_ITEMS = new ArrayList<MenuItem>();
        this.TITLE = TITLE;
        BACKGROUND = new RectangleShape();

        active = false;
    }

    public void show() {
        active = true;
    }

    public void hide() {
        active = false;
    }

    public void addMenuItem(MenuItem menuItem) {
        menuItem.setParent(this);
        MENU_ITEMS.add(menuItem);
    }

    @Override
    public void initialize() {
        BACKGROUND.setSize(new Vector2f(.6f * uiView.getSize().x, uiView.getSize().y));
        BACKGROUND.setFillColor(Color.BLUE);
        BACKGROUND.setOutlineColor(Color.BLACK);
        align(BACKGROUND.getLocalBounds().width + BACKGROUND.getLocalBounds().left, BACKGROUND.getLocalBounds().height + BACKGROUND.getLocalBounds().top);
        BACKGROUND.setPosition(alignPosition);
    }

    @Override
    public void draw(RenderTarget renderTarget, RenderStates renderStates) {
        if (active) {
            BACKGROUND.draw(renderTarget, renderStates);
            for (MenuItem menuItem : MENU_ITEMS) {
                menuItem.draw(renderTarget, renderStates);
            }
        }
    }
}
