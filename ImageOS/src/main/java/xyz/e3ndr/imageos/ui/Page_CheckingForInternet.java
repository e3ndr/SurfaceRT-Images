package xyz.e3ndr.imageos.ui;

import static xyz.e3ndr.imageos.ui.UI.*;

import java.awt.Dimension;
import java.awt.Point;

import org.jetbrains.annotations.Nullable;

class Page_CheckingForInternet implements UIPage {

    @Override
    public @Nullable Dimension getModalSize() {
        return new Dimension(40, 5);
    }

    @Override
    public void paint(Point origin) {
        window.writeAt(origin.x, origin.y, "Checking for internet...");
    }

}
