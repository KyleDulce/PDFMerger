package me.kyledulce.pdfmerger.window;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import me.kyledulce.pdfmerger.PDFMerger;
import me.kyledulce.pdfmerger.config.AppConfig;
import me.kyledulce.pdfmerger.config.ConfigPath;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class JFWindow extends Application {
    private Map<Page, Parent> pageToSceneMap;
    private Stage stage;

    @Override
    public void start(Stage stage) {
        PDFMerger.setAppWindowInstance(this);
        this.stage = stage;
        loadAllPages();
        setStageProperties();

        stage.setScene(new Scene(pageToSceneMap.get(Page.HOME)));
        stage.show();
    }

    private void setStageProperties() {
        AppConfig config = PDFMerger.getAppConfig();
        stage.setTitle(
                config.getString(
                        ConfigPath.WINDOW_TITLE));
        stage.setMaximized(
                config.getBoolean(
                        ConfigPath.WINDOW_SHOULD_MAXIMISE));
    }

    private void loadAllPages() {
        pageToSceneMap = new HashMap<>();
        for(Page page : Page.values()) {
            try {
                URL resourceURL = PDFMerger.class
                        .getResource(String.format("/pages/%s.fxml", page.toString()));

                if(resourceURL == null) {
                    throw new IOException(String.format("Failed to create resource url for %s", page));
                }

                Parent pageRoot = FXMLLoader.load(resourceURL);
                pageToSceneMap.put(page, pageRoot);
            } catch (IOException e) {
                System.err.printf("Error Reading page: %s\n", page);
                e.printStackTrace();
            }
        }
    }

    public static void openWindow() {
        Application.launch();
    }
}
