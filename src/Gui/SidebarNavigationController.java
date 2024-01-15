package Gui;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SidebarNavigationController implements Initializable{

    @FXML private AnchorPane pane1, pane2;
    @FXML private ImageView menu;
    @FXML private StackPane scenesStackPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //setupTransitions();
        menu.setOnMouseClicked(event -> showPane(true));
        pane1.setOnMouseClicked(event -> showPane(false));
        pageStilling();
    }

    public void loadScene(String fxml) {
        showPane(false);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/"+fxml+".fxml"));
            AnchorPane scene = loader.load();
            scenesStackPane.getChildren().setAll(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pageStilling() {
        loadScene("Stilling");
    }

    public void pageKamp() {
        loadScene("Interface");
    }

    public void pageKamprapport() {
        loadScene("Kamprapport");
    }


    private void showPane(boolean show) {
        double fromValue, toValue;
        int x;
        double y = pane1.getLayoutX();
        System.out.println(y);
        pane1.setVisible(show);

        if(show) {
            fromValue = 0;
            toValue = 0.15;
            x = 600;
        } else {
            fromValue = 0.15;
            toValue = 0;
            x = -600;
        }

        FadeTransition fadeTrans = new FadeTransition(Duration.seconds(0.5), pane1);
        fadeTrans.setFromValue(fromValue);
        fadeTrans.setToValue(toValue);

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5), pane2);
        translateTransition.setByX(x);

        fadeTrans.play();
        translateTransition.play();
    }

/*
    private void setupTransitions() {
        pane1.setVisible(false);

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5), pane1);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5), pane2);
        translateTransition.setByX(-600);

        fadeTransition.play();
        translateTransition.play();
    }

    private void setCursorHand() {
        menu.getScene().setCursor(Cursor.HAND);
    }

    private void setCursorDefault() {
        menu.getScene().setCursor(Cursor.DEFAULT);
    }

    private void showPane1() {
        pane1.setVisible(true);

        FadeTransition fadeTransition1 = new FadeTransition(Duration.seconds(0.5), pane1);
        fadeTransition1.setFromValue(0);
        fadeTransition1.setToValue(0.15);

        TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(0.5), pane2);
        translateTransition1.setByX(+600);

        fadeTransition1.play();
        translateTransition1.play();
    }

    private void hidePane1() {
        FadeTransition fadeTransition1 = new FadeTransition(Duration.seconds(0.5), pane1);
        fadeTransition1.setFromValue(0.15);
        fadeTransition1.setToValue(0);

        fadeTransition1.setOnFinished(event1 -> pane1.setVisible(false));

        TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(0.5), pane2);
        translateTransition1.setByX(-600);

        fadeTransition1.play();
        translateTransition1.play();
    }

 */
}