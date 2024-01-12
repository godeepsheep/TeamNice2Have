package Gui;

import Data.Datalayer;
import Logic.Team;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;


public class InterfaceController implements Initializable{


    int elapsedSeconds = 0;
    int elapsedMinutes = 0;
    //how long the match will be
    int targetTime = 60;
    //total amount of seconds the match has lasted
    int totalTime = 0;
    boolean TimerRunning = false;
    boolean gameStart = false;


    Datalayer datalayer = new Datalayer();

    Timer _time;

    @FXML
    ChoiceBox Team1Name;
    @FXML
    ChoiceBox Team2Name;
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

    ArrayList<Team> totalTeams = datalayer.getTeams();

    public int penaltiesTeam2, Team1Goals, Team2Goals, penaltiesTeam1 = 0;
    public int team1ID, team2ID;
    public int Team1ID, Team2ID;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AddNamesToChoiceBox();
    }

    public void AddNamesToChoiceBox(){
        for(Team team : totalTeams){
            System.out.println(team.getName());
            Team1Name.getItems().add(team.getName());
            Team2Name.getItems().add(team.getName());
        }
        Team1Name.setOnAction(event -> {Team1ID = Team1Name.getSelectionModel().getSelectedIndex();
            System.out.println(Team1ID);});
        Team2Name.setOnAction(event -> {Team2ID = Team1Name.getSelectionModel().getSelectedIndex();
            System.out.println(Team2ID);});
    }

    public void FindTeamName(){


        }


    public void GameStart() {

        datalayer.startMatch(team1ID, team2ID);

    }


    public void AddGoalTeam1() {
        Team1Goals += 1;
        Team1Score.setText("" + Team1Goals);
        //datalayer.setEvent(1, ?, teamID, CurrentGameTime());
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
        System.out.println(CurrentGameTime());
    }


    public String CurrentGameTime() {
        String zeroString;
        if (elapsedSeconds < 10) {
            zeroString = "0";

        } else {
            zeroString = "";
        }
        return "0" + elapsedMinutes + ":" + zeroString + elapsedSeconds;
    }

    public void UpdateTimer() {
        if (!gameStart)
            gameStart = true;
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
                        timerTextField.setText(CurrentGameTime());
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





