package xyz.e3ndr.imageos.ui;

import static xyz.e3ndr.imageos.ui.UI.*;

import java.awt.Dimension;
import java.awt.Point;
import java.util.List;

import org.jetbrains.annotations.Nullable;

import xyz.e3ndr.consoleutil.ansi.ConsoleAttribute;
import xyz.e3ndr.consoleutil.input.InputKey;
import xyz.e3ndr.imageos.Util;
import xyz.e3ndr.imageos.api.Image;
import xyz.e3ndr.imageos.api.ImageFlavor;
import xyz.e3ndr.imageos.ui.UI.Page;

class Page_ListImages implements UIPage {
    private static final int MAX_PER_SCROLL = 9;
    private static final int WIDTH = 50;

    ImageFlavor flavor;

    private int scroll = 0;

    private int showing = 0; // version, date, size

    @Override
    public @Nullable Dimension getModalSize() {
        return new Dimension(WIDTH, MAX_PER_SCROLL + 1);
    }

    @Override
    public void paint(Point origin) {

        // Paint some additional space on the southern border.
        window
            .set24BitBackgroundColor(BORDER_COLOR_R, BORDER_COLOR_G, BORDER_COLOR_B)
            .writeAt(origin.x - 2, origin.y + MAX_PER_SCROLL + 1, Util.inflate(WIDTH + 4, ' '))
            .writeAt(origin.x - 2, origin.y + MAX_PER_SCROLL + 2, Util.inflate(WIDTH + 4, ' '));

        // Put the keybinds in that space
        final String KEYBINDS_1 = "BKSP Back to flavors  V Show Version  S Show Size";
        final String KEYBINDS_2 = "ENTR Install          D Show Date                ";

        window
            .writeAt(origin.x, origin.y + MAX_PER_SCROLL + 1, KEYBINDS_1)
            .writeAt(origin.x, origin.y + MAX_PER_SCROLL + 2, KEYBINDS_2);

        window
            .set24BitBackgroundColor(BACKGROUND_COLOR_R, BACKGROUND_COLOR_G, BACKGROUND_COLOR_B);

        if (this.flavor == null) return;

        List<Image> display = this.getCurrentDisplay();

        for (int idx = 0; idx < display.size(); idx++) {
            boolean isSelected = this.scroll == idx;

            if (isSelected) {
                window.setAttributes(ConsoleAttribute.REVERSE);
            }

            Image curr = display.get(idx);
            String name = curr.getName();
            String detail = " ";

            switch (this.showing) {
                case 0: {
                    detail += curr.getVersion();
                    break;
                }
                case 1: {
                    detail += curr.getReleaseDate();
                    break;
                }
                case 2: {
                    detail += curr.getFormattedSize();
                    break;
                }
            }

            window
                .writeAt(
                    origin.x, origin.y + idx,
                    String.format("%-" + WIDTH + "s", name)
                );

            int detailOffsetX = WIDTH - detail.length();

            window
                .setAttributes(ConsoleAttribute.DIM)
                .writeAt(
                    origin.x + detailOffsetX, origin.y + idx,
                    detail
                )
                .setAttributes(ConsoleAttribute.DIM_OFF);

            if (name.length() > detailOffsetX) {
                window.writeAt(
                    origin.x + detailOffsetX - 3, origin.y + idx,
                    "..."
                );
            }

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

    private List<Image> getCurrentDisplay() {
        if (this.flavor.getImages().size() < MAX_PER_SCROLL) {
            return this.flavor.getImages();
        }

        return null; // TODO
    }

    @Override
    public void onKey(char key, boolean alt, boolean control) {
        switch (key) {
            case 'v': {
                this.showing = 0;
                UI.rerender();
                break;
            }
            case 'd': {
                this.showing = 1;
                UI.rerender();
                break;
            }
            case 's': {
                this.showing = 2;
                UI.rerender();
                break;
            }
        }
    }

    @Override
    public void onKey(InputKey key) {
        if (key == InputKey.BACK_SPACE) {
            UI.navigate(Page.LIST_FLAVORS);
        }
    }

}
