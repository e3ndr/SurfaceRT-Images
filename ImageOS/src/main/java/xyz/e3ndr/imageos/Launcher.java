package xyz.e3ndr.imageos;

import java.io.IOException;

import xyz.e3ndr.consoleutil.ConsoleUtil;
import xyz.e3ndr.consoleutil.ansi.ConsoleAttribute;
import xyz.e3ndr.consoleutil.ansi.ConsoleColor;
import xyz.e3ndr.consoleutil.consolewindow.ConsoleWindow;

public class Launcher {

    public static void main(String[] args) throws IOException, InterruptedException {
        ConsoleUtil.summonConsoleWindow();

        ConsoleWindow window = ConsoleUtil.getAttachedConsoleWindow()
            .clearScreen()
            .cursorTo(0, 0)
            .setAutoFlushing(false);

        for (ConsoleColor color : ConsoleColor.values()) {
            if (!color.isLight()) {
                window.setBackgroundColor(color);
                window.write("  ");
            }
        }

        window.cursorTo(0, 1);

        for (ConsoleColor color : ConsoleColor.values()) {
            if (color.isLight()) {
                window.setBackgroundColor(color);
                window.write("  ");
            }
        }

        window.cursorTo(0, 3);

        window.setBackgroundColor(ConsoleColor.BLACK).setTextColor(ConsoleColor.WHITE);

        window.setAttributes(ConsoleAttribute.RESET, ConsoleAttribute.BOLD).write("BOLD").cursorRight(1);
        window.setAttributes(ConsoleAttribute.RESET, ConsoleAttribute.STRIKETHROUGH).write("STRIKETHROUGH").cursorRight(1);
        window.setAttributes(ConsoleAttribute.RESET, ConsoleAttribute.ITALIC).write("ITALIC").cursorRight(1);
        window.setAttributes(ConsoleAttribute.RESET, ConsoleAttribute.UNDERLINE).write("UNDERLINE").cursorRight(1);

        window.setAttributes(ConsoleAttribute.RESET, ConsoleAttribute.BLINKING_OFF);
        window.flush();

        while (true) {
            for (ConsoleColor color : ConsoleColor.values()) {
                window.cursorTo(4, 6).setBackgroundColor(color).write("    ").setBackgroundColor(ConsoleColor.BLACK);
                window.cursorTo(4, 7).setBackgroundColor(color).write("    ").setBackgroundColor(ConsoleColor.BLACK);
                window.flush();
                Thread.sleep(150);
            }
        }
    }

}
