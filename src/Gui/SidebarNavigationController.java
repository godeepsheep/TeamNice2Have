package Gui;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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

    boolean idleMenu = true;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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

        if(!idleMenu) return;
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

        fadeTrans.setOnFinished(event -> idleMenu = true);
        idleMenu = false;

        fadeTrans.play();
        translateTransition.play();
    }

}