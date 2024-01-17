package Logic;

import Data.DBevent;
import Data.DBmatch;
import Data.Team;
import Gui.InterfaceController;
import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class Match {

 //Match
    private int matchID, team1ID, team2ID;
    private int penaltiesTeam1, penaltiesTeam2, Team1Goals, Team2Goals;
    private int elapsedSeconds, elapsedMinutes;  //how long the match has run
    private int totalTime, targetTime; //total amount of seconds the match will take
    private boolean TimerRunning, gameStart;

    private Timer _time;
    private final String imagePause = "images/pauseicon.png";
    private final String imagePlay = "images/PlayIcon.png";

 //dependencies from the interfaceController
    private ChoiceBox<Team> Team1Name, Team2Name;
    private TextField Team1Score, Team2Score, Team1PenaltyTextField, Team2PenaltyTextField;
    private ImageView PauseButton;
    private Label timerLabelText;
    private Class<? extends InterfaceController>  streamclass;


//get db connections
    DBmatch matchDB = new DBmatch();
    DBevent eventDB = new DBevent();


//set all core dependencies for the Match
    public Match(int elapsedSeconds, int elapsedMinutes, int targetTime, int totalTime) {
        this.elapsedSeconds = elapsedSeconds;
        this.elapsedMinutes = elapsedMinutes;
        this.targetTime = targetTime;
        this.totalTime = totalTime;
    }


//get all the fxml dependencies from the controller
    public void setChoiceBox(ChoiceBox<Team> Team1Name, ChoiceBox<Team> Team2Name) {
        this.Team1Name = Team1Name;
        this.Team2Name = Team2Name;
    }

    public void setTextField(TextField Team1Score, TextField Team2Score, TextField Team1PenaltyTextField, TextField Team2PenaltyTextField) {
        this.Team1Score = Team1Score;
        this.Team2Score = Team2Score;
        this.Team1PenaltyTextField = Team1PenaltyTextField;
        this.Team2PenaltyTextField = Team2PenaltyTextField;
    }

    public void setMisc(ImageView PauseButton, Label timerLabelText) {
        this.PauseButton = PauseButton;
        this.timerLabelText = timerLabelText;
    }

    public void setStream(Class<? extends InterfaceController> streamclass) {
        this.streamclass = streamclass;
    }


//get the teams ID from the gui choice box
    public void setTeamID(int team, int id) {
        if(team == 1)
            team1ID = id;
        else if(team == 2)
            team2ID = id;
    }


//Match operations
    public int start(int team1ID, int team2ID) {
        ClearGame();

        this.team2ID = team2ID;
        this.team1ID = team1ID;

        matchID = matchDB.startMatch(team1ID, team2ID);
        setDisableBox(true);

        Team1Score.setText("0");
        Team2Score.setText("0");
        Team1PenaltyTextField.setText("0");
        Team2PenaltyTextField.setText("0");

        return matchID;
    }

    private void ClearGame() {
        elapsedMinutes = elapsedSeconds = totalTime = 0;
        Team2Goals = Team1Goals = 0;
        penaltiesTeam1 = penaltiesTeam2 = 0;
    }

    public void end() {
        matchDB.endMatch(matchID);
        setDisableBox(false);
    }

    public void setDisableBox(boolean value) {
        Team1Name.setDisable(value);
        Team2Name.setDisable(value);
    }


//setting the goals
    public int goal(boolean addGoals, int Goals, TextField TeamScore, int teamID) {
        if (!TimerRunning) return Goals;

        if (addGoals) {    
            Goals++;
            eventDB.setEvent(1, matchID, teamID, CurrentGameTime());

        } else if (Goals > 0) {
            Goals--;
            eventDB.deleteEvent(1, matchID, teamID);
        }

        TeamScore.setText("" + Goals);
        return Goals;
    }

//setting the Penalties
    public int penalties(boolean addPenalties, int penalties, TextField PenaltyText, int teamID) {
        if (!TimerRunning) return penalties;

        if (addPenalties) {    
            penalties++;
            eventDB.setEvent(2, matchID, teamID, CurrentGameTime());

        } else if (penalties > 0) {
            penalties--;
            eventDB.deleteEvent(2, matchID, teamID);
        }

        PenaltyText.setText("" + penalties);
        return penalties;
    }


//timer operations
    public String CurrentGameTime() {
        String zeroString;
        if (elapsedSeconds < 10) {
            zeroString = "0";
        } else {
            zeroString = "";
        }
        return "0" + elapsedMinutes + ":" + zeroString + elapsedSeconds;
    }


    public void updateTime() {
        if (team1ID == 0 || team2ID == 0) return;

        if (!gameStart && team2ID > 0 && team1ID > 0) {
            gameStart = true;
            start(team1ID, team2ID); //GameStart();
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
                        end();
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
        return new Image(streamclass.getResourceAsStream(image));
    }
}