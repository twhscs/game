package io.github.twhscs.game.ui;

import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.View;

import java.util.HashMap;
import java.util.Map;

public final class UserInterface implements Drawable {
    private final Map<String, UserInterfaceElement> elements;
    private final View UI_VIEW;

    public UserInterface(View UI_VIEW) {
        elements = new HashMap<String, UserInterfaceElement>();
        this.UI_VIEW = UI_VIEW;
    }

    public void addElement(String ID, UserInterfaceElement element) {
        element.setView(UI_VIEW);
        element.initialize();
        elements.put(ID, element);
    }

    public UserInterfaceElement getElement(String ID) {
        return elements.get(ID);
    }

    public void alignAll() {
        for (UserInterfaceElement element : elements.values()) {
            //element.align();
        }
    }

    @Override
    public void draw(RenderTarget renderTarget, RenderStates renderStates) {
        for (UserInterfaceElement element : elements.values()) {
            element.draw(renderTarget, renderStates);
        }
    }
}
