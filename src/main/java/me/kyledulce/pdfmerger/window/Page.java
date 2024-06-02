package me.kyledulce.pdfmerger.window;

public enum Page {
    HOME("home"),
    ;
    private final String nameStr;
    Page(String nameStr) {
        this.nameStr = nameStr;
    }

    @Override
    public String toString() {
        return nameStr;
    }
}
