package me.kyledulce.pdfmerger;

import lombok.Getter;
import lombok.Setter;
import me.kyledulce.pdfmerger.config.AppConfig;
import me.kyledulce.pdfmerger.window.JFWindow;

import java.io.IOException;

public class PDFMerger {
    private static final String configFileLocation = "/Config.yml";

    @Setter
    @Getter
    private static JFWindow appWindowInstance;
    @Getter
    private static AppConfig appConfig;

    public static void main(String[] args) throws IOException {
        appConfig = AppConfig.instantiateConfiguration(configFileLocation);

        // Current window handler is JFWindow
        JFWindow.openWindow();
    }
}
