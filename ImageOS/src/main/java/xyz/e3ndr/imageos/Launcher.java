package xyz.e3ndr.imageos;

import java.io.IOException;

import xyz.e3ndr.imageos.ui.UI;
import xyz.e3ndr.imageos.ui.UIPage;

public class Launcher {

    public static void main(String[] args) throws IOException, InterruptedException {
        UI.init(false);

        UI.navigate(UIPage.CHECKING_FOR_INTERNET);
        Thread.sleep(Long.MAX_VALUE);
    }

}
