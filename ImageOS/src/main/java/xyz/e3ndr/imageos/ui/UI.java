package xyz.e3ndr.imageos.ui;

import java.awt.Dimension;
import java.awt.Point;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import xyz.e3ndr.consoleutil.ConsoleUtil;
import xyz.e3ndr.consoleutil.ansi.ConsoleColor;
import xyz.e3ndr.consoleutil.consolewindow.ConsoleWindow;
import xyz.e3ndr.consoleutil.input.KeyHook;
import xyz.e3ndr.fastloggingframework.FastLoggingFramework;
import xyz.e3ndr.fastloggingframework.logging.FastLogger;
import xyz.e3ndr.fastloggingframework.logging.LogLevel;
import xyz.e3ndr.imageos.Util;
import xyz.e3ndr.imageos.api.ImageFlavor;

public class UI {
    public static ConsoleWindow window;

    public static final int BORDER_COLOR_R = 136;
    public static final int BORDER_COLOR_G = 0;
    public static final int BORDER_COLOR_B = 0;

    public static final int BACKGROUND_COLOR_R = 255;
    public static final int BACKGROUND_COLOR_G = 119;
    public static final int BACKGROUND_COLOR_B = 119;

    public static final int TEXT_COLOR_R = 255;
    public static final int TEXT_COLOR_G = 255;
    public static final int TEXT_COLOR_B = 255;

    private static Page current;
    private static UIPage currentPage;

    private static Dimension currentSize;
    private static boolean isDrawing = false;

    public static List<ImageFlavor> flavors = Collections.emptyList();

    public static void init(boolean dev) throws IOException, InterruptedException {
        FastLoggingFramework.setColorEnabled(false);

        if (dev) {
            currentSize = new Dimension(120, 30);

            window = ConsoleUtil.openAnotherConsoleWindow()
                .setSize(currentSize.width, currentSize.height);

            FastLoggingFramework.setDefaultLevel(LogLevel.DEBUG);
        } else {
            ConsoleUtil.summonConsoleWindow();

            window = ConsoleUtil.getAttachedConsoleWindow()
                .setAutoFlushing(false);

            FastLoggingFramework.setDefaultLevel(LogLevel.NONE);

            Thread resizeListener = new Thread(UI::resizeListener);
            resizeListener.setDaemon(true);
            resizeListener.start();
        }

        KeyHook.CURRENT.setIgnoringInterrupt(true);

        window
            .clearScreen()
            .cursorTo(0, 0);
    }

    @SneakyThrows
    private static void resizeListener() {
        while (true) {
            Dimension size = ConsoleUtil.getSize();

            if (!size.equals(currentSize)) {
                currentSize = size;
                rerender();
            }

            TimeUnit.SECONDS.sleep(1);
        }
    }

    @SneakyThrows
    @SuppressWarnings("deprecation")
    public static UIPage navigate(Page newPage) {
        if (currentPage != null) {
            KeyHook.CURRENT.removeListener(currentPage);
        }

        current = newPage;
        currentPage = current.page.newInstance();
        KeyHook.CURRENT.addListener(currentPage);
        rerender();

        return currentPage;
    }

    @SneakyThrows
    static void rerender() {
        if (currentSize == null) return;
        if (isDrawing) return;

        try {
            isDrawing = true;
            window.setTitle(String.format("Page: %s, Size: %dx%d", current, currentSize.width, currentSize.height));

            if (current == null) {
                window.clearScreen();
            } else {
                Dimension modalSize = currentPage.getModalSize();
                Point origin = null;

                if (modalSize != null) {
                    origin = paintModal(modalSize.width, modalSize.height);
                }

                currentPage.paint(origin);
            }

            window
                .cursorTo(0, 0)
                .flush();

            isDrawing = false;
        } catch (Throwable e) {
            FastLogger.logStatic(LogLevel.SEVERE, "UI Crashed! Error:\n%s", e);
        }
    }

    private static Point paintModal(int mWidth, int mHeight) {
        final int BORDER_W = 2;
        final int BORDER_H = 1;

        mWidth += BORDER_W * 2;
        mHeight += BORDER_H * 2;

        int sWidth = currentSize.width;
        int sHeight = currentSize.height;

        int xOffset = sWidth / 2 - mWidth / 2;
        int yOffset = sHeight / 2 - mHeight / 2;

        window
            .setBackgroundColor(ConsoleColor.BLACK)
            .clearScreen();

        for (int y = 0; y < mHeight; y++) {
            window.cursorTo(xOffset, y + yOffset);

            if (y < BORDER_H ||
                y >= mHeight - BORDER_H) {
                // Paint the top and bottom border.
                window
                    .set24BitBackgroundColor(BORDER_COLOR_R, BORDER_COLOR_G, BORDER_COLOR_B)
                    .write(Util.inflate(mWidth, ' '));
            } else {
                window
                    // Paint the left border.
                    .set24BitBackgroundColor(BORDER_COLOR_R, BORDER_COLOR_G, BORDER_COLOR_B)
                    .write(Util.inflate(BORDER_W, ' '))
                    // Paint the body.
                    .set24BitBackgroundColor(BACKGROUND_COLOR_R, BACKGROUND_COLOR_G, BACKGROUND_COLOR_B)
                    .write(Util.inflate(mWidth - BORDER_W - BORDER_W, ' '))
                    // Paint the right border.
                    .set24BitBackgroundColor(BORDER_COLOR_R, BORDER_COLOR_G, BORDER_COLOR_B)
                    .write(Util.inflate(BORDER_W, ' '));
            }
        }

        window
            .set24BitColor(TEXT_COLOR_R, TEXT_COLOR_G, TEXT_COLOR_B)
            .set24BitBackgroundColor(BACKGROUND_COLOR_R, BACKGROUND_COLOR_G, BACKGROUND_COLOR_B);

        return new Point(xOffset + BORDER_W, yOffset + BORDER_H);
    }

    @AllArgsConstructor
    public static enum Page {
        CHECKING_FOR_INTERNET(Page_CheckingForInternet.class),
        LIST_FLAVORS(Page_ListFlavors.class),
        LIST_IMAGES(Page_ListImages.class),

        ;

        private Class<? extends UIPage> page;

    }

}
