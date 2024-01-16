package Gui;

import Data.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;


public class InterfaceController implements Initializable {

    private int penaltiesTeam1, penaltiesTeam2, Team1Goals, Team2Goals;
    private int team1ID, team2ID, matchID;
    private int elapsedSeconds = 58, elapsedMinutes = 0;  //how long the match has run
    private int targetTime = 60; //total amount of seconds the match will take
    private int totalTime = 58;
    private boolean TimerRunning, gameStart;
    Timer _time;

    private String imagePause = "images/pauseicon.png";
    private String imagePlay = "images/PlayIcon.png";

    @FXML private ChoiceBox<Team> Team1Name, Team2Name;
    @FXML private TextField Team1Score, Team2Score, Team1PenaltyTextField, Team2PenaltyTextField;
    @FXML private ImageView PauseButton;
    @FXML private Label timerLabelText;

    DBteam teamDB = new DBteam();
    DBmatch matchDB = new DBmatch();
    DBevent eventDB = new DBevent();

    ArrayList<Team> totalTeams = teamDB.getTeams();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        GenerateTeamList(Team1Name);
        GenerateTeamList(Team2Name);

        Team1Name.setOnAction(event -> {
            team1ID = getTeamID(Team1Name, Team2Name, team1ID);
            activateButton();
        });
        Team2Name.setOnAction(event -> {
            team2ID = getTeamID(Team2Name, Team1Name, team2ID);
            activateButton();
        });
    }

    private int getTeamID(ChoiceBox<Team> box1, ChoiceBox<Team> box2, int teamID) {
        //finder valgte værdi for choice box 1
        Team team = box1.getSelectionModel().getSelectedItem();
        if (team == null) return teamID;
        teamID = team.getID();

        //sletter værdien i choice box 2
        Team selectedTeam = box2.getSelectionModel().getSelectedItem();
        GenerateTeamList(box2);
        box2.setValue(selectedTeam);
        box2.getItems().remove(getIndex(teamID));

        return teamID;
    }

    private int getIndex(int teamID) {
        for (int i = 0; i < totalTeams.size(); i++)
            if (totalTeams.get(i).getID() == teamID)
                return i;

        return -1;
    }

    public void GenerateTeamList(ChoiceBox<Team> box) {
        box.getItems().clear();
        box.getItems().addAll(totalTeams);
    }


    public void GameStart() {
        ClearGame();
        matchID = matchDB.startMatch(team1ID, team2ID);
        setDisableBox(true);

    }

    public void GameEnd() {
        matchDB.endMatch(matchID);
        setDisableBox(false);
    }

    private void ClearGame() {
        elapsedMinutes = elapsedSeconds = totalTime = 0;
        Team2Goals = Team1Goals = 0;
        penaltiesTeam1 = penaltiesTeam2 = 0;
        Team1Score.setText("0");
        Team2Score.setText("0");
        Team1PenaltyTextField.setText("0");
        Team2PenaltyTextField.setText("0");
    }

    private void setDisableBox(boolean value) {
        Team1Name.setDisable(value);
        Team2Name.setDisable(value);
    }

    private void activateButton() {
        if (team1ID > 0 && team2ID > 0)
            PauseButton.getStyleClass().add("pauseButtonActive");
        else
            PauseButton.getStyleClass().remove("pauseButtonActive");
    }

    public void AddGoalTeam1() {
        Team1Goals = AddGoal(Team1Goals, Team1Score, team1ID);
    }

    public void AddGoalTeam2() {
        Team2Goals = AddGoal(Team2Goals, Team2Score, team2ID);
    }

    private int AddGoal(int Goals, TextField TeamScore, int teamID) {
        if (TimerRunning) {
            Goals++;
            TeamScore.setText("" + Goals);
            eventDB.setEvent(1, matchID, teamID, CurrentGameTime());
        }
        return Goals;
    }

    public void RemoveGoalTeam1() {
        Team1Goals = RemoveGoal(Team1Goals, Team1Score, team1ID);
    }

    public void RemoveGoalTeam2() {
        Team2Goals = RemoveGoal(Team2Goals, Team2Score, team2ID);
    }

    public int RemoveGoal(int Goals, TextField TeamScore, int teamID) {
        if (TimerRunning && Goals > 0) {
            Goals--;
            TeamScore.setText("" + Goals);
            eventDB.deleteEvent(1, matchID, teamID);
        }
        return Goals;
    }

    public void AddPenaltiesTeam1() {
        penaltiesTeam1 = AddPenalties(penaltiesTeam1, Team1PenaltyTextField, team1ID);
    }

    public void AddPenaltiesTeam2() {
        penaltiesTeam2 = AddPenalties(penaltiesTeam2, Team2PenaltyTextField, team2ID);
    }

    public int AddPenalties(int penalties, TextField PenaltyText, int teamID) {
        if (TimerRunning) {
            penalties++;
            PenaltyText.setText("" + penalties);
            eventDB.setEvent(2, matchID, teamID, CurrentGameTime());
        }
        return penalties;
    }

    public void RemovePenaltiesTeam1() {
        penaltiesTeam1 = RemovePenalties(penaltiesTeam1, Team1PenaltyTextField, team1ID);
    }

    public void RemovePenaltiesTeam2() {
        penaltiesTeam2 = RemovePenalties(penaltiesTeam2, Team2PenaltyTextField, team2ID);

    }

    public int RemovePenalties(int penalties, TextField PenaltyText, int teamID) {
        if (TimerRunning && penalties > 0) {
            penalties--;
            PenaltyText.setText("" + penalties);
            eventDB.deleteEvent(2, matchID, teamID);
        }
        return penalties;
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
        if (team1ID == 0 || team2ID == 0) return;

        if (!gameStart && team2ID > 0 && team1ID > 0) {
            gameStart = true;
            GameStart();
        }

        if (!TimerRunning) {
            Timer time = new Timer();
            _time = time;
            TimerRunning = true;

            time.schedule(new TimerTask() {
                @Override
                public void run() {

                    if (totalTime != targetTime) {
                        //start game
                        if (elapsedSeconds == 60) {
                            elapsedMinutes += 1;
                            elapsedSeconds = 0;
                        }
                        elapsedSeconds += 1;
                        totalTime += 1;
                        Platform.runLater(() -> timerLabelText.setText(CurrentGameTime()));
                        PauseButton.setImage(getImage(imagePause));

                    } else {

                        //End game
                        TimerRunning = false;
                        _time.cancel();
                        _time.purge();
                        if (elapsedSeconds == 60) {
                            elapsedMinutes += 1;
                            elapsedSeconds = 0;
                        }
                        Platform.runLater(() -> timerLabelText.setText(CurrentGameTime()));
                        PauseButton.setImage(getImage(imagePlay));
                        GameEnd();
                        gameStart = false;
                    }
                }
            }, 0L, 1000L);

        } else {
            //pause game
            TimerRunning = false;
            _time.cancel();
            _time.purge();
            PauseButton.setImage(getImage(imagePlay));
        }
    }

    private Image getImage(String image) {
        return new Image(getClass().getResourceAsStream(image));
    }

}





