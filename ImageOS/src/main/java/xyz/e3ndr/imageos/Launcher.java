package xyz.e3ndr.imageos;

import java.io.IOException;

import xyz.e3ndr.imageos.ui.UI;
import xyz.e3ndr.imageos.ui.UI.Page;

public class Launcher {

    public static void main(String[] args) throws IOException, InterruptedException {
        ClassLoader.getSystemClassLoader().setDefaultAssertionStatus(true);

        UI.init(false);

        UI.navigate(Page.CHECKING_FOR_INTERNET);
        if (!Util.hasInternetConnection()) {
            // TODO Drop the user into a "Sign into wifi or try again" page.
        }

        UI.flavors = Util.getCurrentFlavors();
        UI.navigate(Page.LIST_FLAVORS);

        Thread.sleep(Long.MAX_VALUE);
    }

}
