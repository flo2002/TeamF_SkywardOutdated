package fhv.ws22.se.skyward.view;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class NotificationController {
    private static NotificationController singleton;

    public static synchronized NotificationController getInstance() {
        if (singleton == null) {
            singleton = new NotificationController();
        }
        return singleton;
    }

    public Popup createPopup(final String message) {
        final Popup popup = new Popup();
        popup.setAutoFix(true);
        popup.setAutoHide(true);
        popup.setHideOnEscape(true);
        Label label = new Label(message);
        label.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                popup.hide();
            }
        });

        try {
            URL url = new File("src/main/resources/fhv/ws22/se/skyward/css/styles.css").toURI().toURL();
            label.getStylesheets().add(url.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        popup.getContent().add(label);
        return popup;
    }

    public void showSuccessNotification(String message, Stage stage) {
        final Popup popup = createPopup(message);
        popup.setOnShown(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent e) {
                popup.setX(stage.getX());
                popup.setY(stage.getY() + stage.getHeight() - popup.getHeight());
            }
        });
        popup.getContent().get(0).getStyleClass().add("success_popup");
        popup.show(stage);
    }

    public void showErrorNotification(String message, Stage stage) {
        final Popup popup = createPopup(message);
        popup.setOnShown(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent e) {
                popup.setX(stage.getX());
                popup.setY(stage.getY() + stage.getHeight() - popup.getHeight());
            }
        });
        popup.getContent().get(0).getStyleClass().add("error_popup");
        popup.show(stage);
    }
}
