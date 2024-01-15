package Gui;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Stop;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class SidebarNavigationController implements Initializable {

    @FXML
    private AnchorPane pane1, pane2;
    @FXML
    private ImageView menu;
    @FXML
    private StackPane scenesStackPane;
    @FXML
    Button eventsButton, rapportButton;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //setupTransitions();
        menu.setOnMouseClicked(event -> showPane(true));
        pane1.setOnMouseClicked(event -> showPane(false));
        pageStilling();
       eventsButton.setVisible(false);
       // rapportButton.setVisible(false);
    }

    public void loadScene(String fxml) {
        showPane(false);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/" + fxml + ".fxml"));
            AnchorPane scene = loader.load();
            scenesStackPane.getChildren().setAll(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pageStilling() {
        loadScene("Stilling");
        eventsButton.setVisible(false);
    }

    public void pageKamp() {
        loadScene("Interface");
        eventsButton.setVisible(false);
    }

    public void pageKamprapport() {
        loadScene("Kamprapport");
        eventsButton.setVisible(true);
    }

    public void pageEvents(){ loadScene("Events");
        eventsButton.setVisible(false);
    }


    private void showPane(boolean show) {
            double fromValue, toValue;
            int x;
            pane1.setVisible(show);

            if (show) {
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
    }
