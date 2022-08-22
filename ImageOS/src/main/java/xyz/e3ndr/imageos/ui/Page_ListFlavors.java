package xyz.e3ndr.imageos.ui;

import static xyz.e3ndr.imageos.ui.UI.*;

import java.awt.Dimension;
import java.awt.Point;
import java.util.List;

import org.jetbrains.annotations.Nullable;

import xyz.e3ndr.consoleutil.ansi.ConsoleAttribute;
import xyz.e3ndr.consoleutil.input.InputKey;
import xyz.e3ndr.imageos.Util;
import xyz.e3ndr.imageos.api.ImageFlavor;
import xyz.e3ndr.imageos.ui.UI.Page;

class Page_ListFlavors implements UIPage {
    private static final int MAX_PER_SCROLL = 9;
    private static final int WIDTH = 40;

    private int scroll = 0;

    @Override
    public @Nullable Dimension getModalSize() {
        return new Dimension(WIDTH, MAX_PER_SCROLL + 1);
    }

    @Override
    public void paint(Point origin) {

        // Paint some additional space on the southern border.
        window
            .set24BitBackgroundColor(BORDER_COLOR_R, BORDER_COLOR_G, BORDER_COLOR_B)
            .writeAt(origin.x - 2, origin.y + MAX_PER_SCROLL + 1, Util.inflate(WIDTH + 4, ' '));

        // Put the keybinds in that space
        final String KEYBINDS_1 = "ENTR Select";

        window
            .writeAt(origin.x, origin.y + MAX_PER_SCROLL + 1, KEYBINDS_1);

        window
            .set24BitBackgroundColor(BACKGROUND_COLOR_R, BACKGROUND_COLOR_G, BACKGROUND_COLOR_B);

        List<ImageFlavor> display = this.getCurrentDisplay();

        for (int idx = 0; idx < display.size(); idx++) {
            boolean isSelected = this.scroll == idx;

            if (isSelected) {
                window.setAttributes(ConsoleAttribute.REVERSE);
            }

            window
                .writeAt(
                    origin.x, origin.y + idx,
                    String.format("%-" + WIDTH + "s", display.get(idx).getName())
                );

            if (isSelected) {
                window.setAttributes(ConsoleAttribute.REVERSE_OFF);
            }
        }

        if (display.size() == MAX_PER_SCROLL) {
            window
                .setAttributes(ConsoleAttribute.REVERSE)
                .writeAt(origin.x, origin.y + MAX_PER_SCROLL, "...")
                .setAttributes(ConsoleAttribute.REVERSE_OFF);
        }
    }

    private List<ImageFlavor> getCurrentDisplay() {
        if (UI.flavors.size() < MAX_PER_SCROLL) {
            return UI.flavors;
        }

        return null; // TODO
    }

    @Override
    public void onKey(InputKey key) {
        if (key == InputKey.ENTER) {
            Page_ListImages li = (Page_ListImages) UI.navigate(Page.LIST_IMAGES);
            li.flavor = UI.flavors.get(this.scroll);
            UI.rerender();
        }
    }

}
