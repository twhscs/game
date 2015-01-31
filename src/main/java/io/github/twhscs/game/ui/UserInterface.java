package io.github.twhscs.game.ui;

import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;

import java.util.HashMap;
import java.util.Map;

public final class UserInterface implements Drawable {
    private final Map<String, UserInterfaceElement> elements;

    public UserInterface() {
        elements = new HashMap<String, UserInterfaceElement>();
    }

    public void addElement(String ID, UserInterfaceElement element) {
        elements.put(ID, element);
    }

    public UserInterfaceElement getElement(String ID) {
        return elements.get(ID);
    }

    public void alignAll() {
        for (UserInterfaceElement element : elements.values()) {
            element.align();
        }
    }

    @Override
    public void draw(RenderTarget renderTarget, RenderStates renderStates) {
        for (UserInterfaceElement element : elements.values()) {
            element.draw(renderTarget, renderStates);
        }
    }
}
