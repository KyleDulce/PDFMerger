package me.kyledulce.pdfmerger.config;

public enum ConfigPath {
    WINDOW_TITLE("window.title"),
    WINDOW_SHOULD_MAXIMISE("window.maximise")
    ;
    private final String path;
    ConfigPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return path;
    }
}
