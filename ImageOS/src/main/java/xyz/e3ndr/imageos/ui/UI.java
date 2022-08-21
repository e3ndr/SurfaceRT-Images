package xyz.e3ndr.imageos.ui;

import java.awt.Dimension;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import lombok.SneakyThrows;
import xyz.e3ndr.consoleutil.ConsoleUtil;
import xyz.e3ndr.consoleutil.consolewindow.ConsoleWindow;
import xyz.e3ndr.fastloggingframework.FastLoggingFramework;
import xyz.e3ndr.fastloggingframework.logging.FastLogger;
import xyz.e3ndr.fastloggingframework.logging.LogLevel;

public class UI {
    public static ConsoleWindow window;

    public static final int MODAL_COLOR_R = 136;
    public static final int MODAL_COLOR_G = 0;
    public static final int MODAL_COLOR_B = 0;

    public static final int BACKGROUND_COLOR_R = 255;
    public static final int BACKGROUND_COLOR_G = 119;
    public static final int BACKGROUND_COLOR_B = 119;

    public static final int TEXT_COLOR_R = 255;
    public static final int TEXT_COLOR_G = 255;
    public static final int TEXT_COLOR_B = 255;

    private static UIPage currentPage;
    private static Dimension currentSize;

    public static void init(boolean dev) throws IOException, InterruptedException {
        if (dev) {
            window = ConsoleUtil.openAnotherConsoleWindow();
            FastLoggingFramework.setColorEnabled(false);
            FastLoggingFramework.setDefaultLevel(LogLevel.DEBUG);
        } else {
            ConsoleUtil.summonConsoleWindow();
            window = ConsoleUtil.getAttachedConsoleWindow();
            FastLoggingFramework.setDefaultLevel(LogLevel.NONE);
        }

        window
            .clearScreen()
            .cursorTo(0, 0)
            .setAutoFlushing(false);

        Thread resizeListener = new Thread(UI::resizeListener);
        resizeListener.setDaemon(true);
        resizeListener.start();
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

    public static void navigate(UIPage newPage) {
        currentPage = newPage;
        rerender();
    }

    @SneakyThrows
    public static void rerender() {
        if (currentSize == null) return;

        try {
            if (currentPage == null) {
                window.clearScreen();
            } else {
                paintBorderAndBackground();
                window
                    .set24BitColor(TEXT_COLOR_R, TEXT_COLOR_G, TEXT_COLOR_B)
                    .set24BitBackgroundColor(BACKGROUND_COLOR_R, BACKGROUND_COLOR_G, BACKGROUND_COLOR_B);
                currentPage.renderHandler.run();
            }

            window.flush();
        } catch (Throwable e) {
            FastLogger.logStatic(LogLevel.SEVERE, "UI Crashed! Error:\n%s", e);
        }
    }

    private static void paintBorderAndBackground() {
        int width = currentSize.width;
        int height = currentSize.height;

        window
            .cursorTo(0, 0)
            .set24BitBackgroundColor(BACKGROUND_COLOR_R, BACKGROUND_COLOR_G, BACKGROUND_COLOR_B);

        for (int y = 0; y < height; y++) {
            window.cursorTo(0, y);
            for (int x = 0; x < width; x++) {
                window.write(" ");
            }
        }

    }

    static void page_checkingForInternet() {
        window.write("Checking for internet...");
    }

}
