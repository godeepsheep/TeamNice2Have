package Gui;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;


public class InterfaceController {

    int elapsedSeconds = 0;
    int elapsedMinutes = 0;
    //how long the match will be
    int targetTime= 60;
    //total amount of seconds the match has lasted
    int totalTime=0;
    boolean TimerRunning = false;

    @FXML
    TextField Team1Name;
    @FXML
    TextField Team2Name;
    @FXML
    TextField Team1Score;
    @FXML
    TextField Team2Score;
    @FXML
    TextField timerTextField;
    @FXML
    Button Team1Goal;
    @FXML
    Button Team2Goal;
    @FXML
    Button Team1Penalty;
    @FXML
    Button Team2Penalty;
    @FXML
    ImageView PauseButton;

    public int Team1Goals = 0;
    public int Team2Goals = 0;
    public int penaltiesTeam1 = 0;
    public int penaltiesTeam2 = 0;

    public void AddGoalTeam1() {
        Team1Goals += 1;
    }

    public void AddGoalTeam2() {
        Team2Goals += 1;
    }

    public void AddPenaltiesTeam1() {
        penaltiesTeam1 += 1;
    }

    public void AddPenaltiesTeam2() {
        penaltiesTeam2 += 1;
    }


    Timer _time;

    public void UpdateTimer() {

        if (!TimerRunning) {
            Timer time = new Timer();
            _time = time;
            TimerRunning = true;
            time.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (totalTime != targetTime) {
                        //changes image on the button
                        Image pauseImage = new Image(getClass().getResourceAsStream("PlayIcon.png"));
                        PauseButton.setImage(pauseImage);

                        elapsedSeconds += 1;
                        totalTime +=1;
                        if (elapsedSeconds == 60) {
                            elapsedMinutes += 1;
                            elapsedSeconds = 0;
                        }
                        String zeroString;
                        if (elapsedSeconds < 10) {
                            zeroString = "0";
                        } else {
                            zeroString = "";
                        }
                        timerTextField.setText("0" + elapsedMinutes + ":" + zeroString + elapsedSeconds);
                    } else{
                        _time.cancel();
                        _time.purge();
                    }
                }
            }, 0L, 1000L);
        } else {
            TimerRunning = false;
            _time.cancel();
            _time.purge();
            Image pauseImage = new Image(getClass().getResourceAsStream("pauseicon.png"));
            PauseButton.setImage(pauseImage);
        }
    }


}





