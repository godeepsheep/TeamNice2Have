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
    int targetTime = 60;
    //total amount of seconds the match has lasted
    int totalTime = 0;
    boolean TimerRunning = false;

    Timer _time;

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
    TextField Team1PenaltyTextField;
    @FXML
    TextField Team2PenaltyTextField;
    @FXML
    ImageView PauseButton;

    public int Team1Goals = 0;
    public int Team2Goals = 0;
    public int penaltiesTeam1 = 0;
    public int penaltiesTeam2 = 0;


    public void AddGoalTeam1() {
        Team1Goals += 1;
        Team1Score.setText("" + Team1Goals);
    }

    public void AddGoalTeam2() {
        Team2Goals += 1;
        Team2Score.setText("" + Team2Goals);
    }

    public void RemoveGoalTeam1() {
        if (Team1Goals > 0)
            Team1Goals -= 1;
        Team1Score.setText("" + Team1Goals);
    }

    public void RemoveGoalTeam2() {
        if (Team2Goals > 0)
            Team2Goals -= 1;
        Team2Score.setText("" + Team2Goals);
    }


    public void AddPenaltiesTeam1() {
        penaltiesTeam1 += 1;
        Team1PenaltyTextField.setText("" + penaltiesTeam1);

    }

    public void AddPenaltiesTeam2() {
        penaltiesTeam2 += 1;
        Team2PenaltyTextField.setText("" + penaltiesTeam2);
    }

    public void RemovePenaltiesTeam1() {
        if (penaltiesTeam1 > 0)
            penaltiesTeam1 -= 1;
        Team1PenaltyTextField.setText("" + penaltiesTeam1);

    }

    public void RemovePenaltiesTeam2() {
        if (penaltiesTeam2 > 0)
            penaltiesTeam2 -= 1;
        Team2PenaltyTextField.setText("" + penaltiesTeam2);
        System.out.println("0" + elapsedMinutes + ":" + ZeroString() + elapsedSeconds);
    }



    public String ZeroString(){
        String zeroString;
        if (elapsedSeconds < 10) {
            zeroString = "0";

        } else {
            zeroString = "";
        }
        return zeroString;
    }

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
                        Image pauseImage = new Image(getClass().getResourceAsStream("pauseicon.png"));
                        PauseButton.setImage(pauseImage);
                        if (elapsedSeconds == 60) {
                            elapsedMinutes += 1;
                            elapsedSeconds = 0;
                        }


                        elapsedSeconds += 1;
                        totalTime += 1;
                        timerTextField.setText("0" + elapsedMinutes + ":" + ZeroString() + elapsedSeconds);
                    } else {
                        _time.cancel();
                        _time.purge();
                    }
                }
            }, 0L, 1000L);
        } else {
            TimerRunning = false;
            _time.cancel();
            _time.purge();
            Image pauseImage = new Image(getClass().getResourceAsStream("PlayIcon.png"));
            PauseButton.setImage(pauseImage);
        }
    }


}





