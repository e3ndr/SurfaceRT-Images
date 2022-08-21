package xyz.e3ndr.imageos.ui;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UIPage {
    CHECKING_FOR_INTERNET(UI::page_checkingForInternet),

    ;

    Runnable renderHandler;

}
