package Gui;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class SidebarNavigationController implements Initializable{

        @FXML
        private AnchorPane pane1, pane2;

        @FXML
        private ImageView menu;
        @FXML
        Label StillingLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupTransitions();

        //Actions for cursorchange when over menu
        menu.setOnMouseEntered(event -> setCursorHand());
        menu.setOnMouseExited(event -> setCursorDefault());
        menu.setOnMousePressed(event -> setCursorDefault());
        menu.setOnMouseReleased(event -> setCursorHand());

        menu.setOnMouseClicked(event -> showPane1());

        pane1.setOnMouseEntered(event -> setCursorHand());
        pane1.setOnMouseExited(event -> setCursorDefault());
        pane1.setOnMouseClicked(event -> hidePane1());
    }

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

        //**Potentiel fix for doublet click**
        //pane.setPosition = startPosition;
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

    public void AddUnderline(){
        StillingLabel.setUnderline(true);
    }

}