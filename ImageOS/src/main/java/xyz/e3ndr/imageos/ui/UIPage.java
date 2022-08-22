package xyz.e3ndr.imageos.ui;

import java.awt.Dimension;
import java.awt.Point;

import org.jetbrains.annotations.Nullable;

import xyz.e3ndr.consoleutil.input.InputKey;
import xyz.e3ndr.consoleutil.input.KeyListener;

interface UIPage extends KeyListener {

    /**
     * @implNote If your modal dimension is null then your paint origin will also be
     *           null. The \@Nullable annotation was omitted to help calm IDEs.
     */
    public @Nullable Dimension getModalSize();

    /**
     * @implNote If your modal dimension is null then your paint origin will also be
     *           null. The \@Nullable annotation was omitted to help calm IDEs.
     */
    public void paint(Point origin);

    @Override
    default void onKey(char key, boolean alt, boolean control) {}

    @Override
    default void onKey(InputKey key) {}

}
