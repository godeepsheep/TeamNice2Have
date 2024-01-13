package Gui;

import Data.Datalayer;
import Logic.Team;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;


public class InterfaceController implements Initializable {

    private int penaltiesTeam1, penaltiesTeam2, Team1Goals, Team2Goals;
    private int team1ID, team2ID, matchID;

    int elapsedSeconds, elapsedMinutes = 0;  //elapsedMinutes: how long the match will be
    int targetTime = 60;
    //total amount of seconds the match has lasted
    int totalTime = 0;
    boolean TimerRunning, gameStart;
    private String imagePause = "images/pauseicon.png";
    private String imagePlay = "images/PlayIcon.png";



    @FXML ChoiceBox<Team> Team1Name, Team2Name;
    @FXML TextField Team1Score, Team2Score, timerTextField, Team1PenaltyTextField, Team2PenaltyTextField;
    @FXML ImageView PauseButton;


    Datalayer datalayer = new Datalayer();
    Timer _time;
    ArrayList<Team> totalTeams = datalayer.getTeams();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        GenerateTeamList(Team1Name);
        GenerateTeamList(Team2Name);

        Team1Name.setOnAction(event -> {team1ID = getTeamID(Team1Name, Team2Name, team1ID);
            System.out.println("team1ID:"+team1ID);

        });
        Team2Name.setOnAction(event -> {team2ID = getTeamID(Team2Name, Team1Name, team2ID);
            System.out.println("team2ID:"+team2ID);
        });
    }

    private int getTeamID(ChoiceBox<Team> box1, ChoiceBox<Team> box2, int teamID) {
        //finder valgte værdi for choice box 1
        Team team = box1.getSelectionModel().getSelectedItem();
        if(team==null) return teamID;
        teamID = team.getID();

        //sletter værdien i choice box 2
        Team selectedTeam = box2.getSelectionModel().getSelectedItem();
        GenerateTeamList(box2);
        box2.setValue(selectedTeam);
        box2.getItems().remove(getIndex(teamID));

        return teamID;
    }

    private int getIndex(int teamID) {
        for(int i=0; i<totalTeams.size(); i++)
            if(totalTeams.get(i).getID() == teamID)
                return i;

        return -1;
    }

    public void GenerateTeamList(ChoiceBox<Team> box) {
        box.getItems().clear();
        box.getItems().addAll(totalTeams);
    }


        /*
    public void AddNamesToChoiceBox() {

        Team1Name.setOnAction(event -> {
            team1ID = Team1Name.getSelectionModel().getSelectedIndex();
            System.out.println(team1ID);
            if (team2ID > -1) {
                Team1Name.getItems().remove(team2ID);
            }
        });
        Team2Name.setOnAction(event -> {
            team2ID = Team2Name.getSelectionModel().getSelectedIndex();
            System.out.println(team2ID);

            if (team1ID > -1) {
                Team2Name.getItems().remove(team1ID);
            }
        })
    }

    public void GenerateTeam1List() {
        Team1Name.getItems().clear();
        for (Team team : totalTeams) {
            Team1Name.getItems().add(team.getName());
        }
        if (team2ID > -1) {
            Team1Name.getItems().remove(team2ID);
        }
    }

    public void GenerateTeam2List() {
        Team2Name.getItems().clear();
        for (Team team : totalTeams) {
            Team2Name.getItems().add(team.getName());
        }
        if (team1ID > -1) {
            Team2Name.getItems().remove(team1ID);
        }
    }
*/

    public void GameStart() {
        matchID = datalayer.startMatch(team1ID, team2ID);
        setDisableBox(true);
    }

    public void GameEnd() {
        datalayer.endMatch(matchID);
        setDisableBox(false);
    }

    private void setDisableBox(boolean value) {
        Team1Name.setDisable(value);
        Team2Name.setDisable(value);
    }

    public void AddGoalTeam1() {
        Team1Goals = AddGoal(Team1Goals, Team1Score, team1ID);
        /*Team1Goals += 1;
        Team1Score.setText("" + Team1Goals);
        datalayer.setEvent(1, matchID, team1ID, CurrentGameTime());*/
    }

    public void AddGoalTeam2() {
        Team2Goals = AddGoal(Team2Goals, Team2Score, team2ID);
        /*
        Team2Goals += 1;
        Team2Score.setText("" + Team2Goals);*/
    }

    private int AddGoal(int Goals, TextField TeamScore, int teamID) {
        if(!TimerRunning) return Goals;

        Goals ++;
        TeamScore.setText("" + Goals);
        datalayer.setEvent(1, matchID, teamID, CurrentGameTime());

        return Goals;
    }


    public void RemoveGoalTeam1() {
        Team1Goals = RemoveGoal(Team1Goals, Team1Score, team1ID);
        /*if (Team1Goals > 0)
            Team1Goals -= 1;
        Team1Score.setText("" + Team1Goals);*/
    }

    public void RemoveGoalTeam2() {
        Team2Goals = RemoveGoal(Team2Goals, Team2Score, team2ID);
       /* if (Team2Goals > 0)
            Team2Goals -= 1;
        Team2Score.setText("" + Team2Goals);*/
    }

    public int RemoveGoal(int Goals, TextField TeamScore, int teamID) {
        if (Goals > 0) Goals --;
        TeamScore.setText("" + Goals);
        datalayer.deleteEvent(1, matchID, teamID);

        return Goals;
    }


    public void AddPenaltiesTeam1() {
        penaltiesTeam1 = AddPenalties(penaltiesTeam1, Team1PenaltyTextField, team1ID);
        /*penaltiesTeam1 += 1;
        Team1PenaltyTextField.setText("" + penaltiesTeam1);*/
    }

    public void AddPenaltiesTeam2() {
        penaltiesTeam2 = AddPenalties(penaltiesTeam2, Team2PenaltyTextField, team2ID);
        /*penaltiesTeam2 += 1;
        Team2PenaltyTextField.setText("" + penaltiesTeam2);*/
    }

    public int  AddPenalties(int penalties, TextField PenaltyText, int teamID) {
        if(!TimerRunning) return penalties;

        penalties ++;
        PenaltyText.setText("" + penalties);
        datalayer.setEvent(2, matchID, teamID, CurrentGameTime());

        return penalties;
    }

    public void RemovePenaltiesTeam1() {
        penaltiesTeam1 = RemovePenalties(penaltiesTeam1, Team1PenaltyTextField, team1ID);
        /*if (penaltiesTeam1 > 0)
            penaltiesTeam1 -= 1;
        Team1PenaltyTextField.setText("" + penaltiesTeam1);*/

    }

    public void RemovePenaltiesTeam2() {
        penaltiesTeam2 = RemovePenalties(penaltiesTeam2, Team2PenaltyTextField, team2ID);
        /*if (penaltiesTeam2 > 0)
            penaltiesTeam2 -= 1;
        Team2PenaltyTextField.setText("" + penaltiesTeam2);
        System.out.println(CurrentGameTime());*/
    }

    public int RemovePenalties(int penalties, TextField PenaltyText, int teamID) {
        if (penalties > 0) penalties --;
        PenaltyText.setText("" + penalties);
        datalayer.deleteEvent(2, matchID, teamID);

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
                        PauseButton.setImage(getImage(imagePause));
                        if (elapsedSeconds == 60) {
                            elapsedMinutes += 1;
                            elapsedSeconds = 0;
                        }
                        elapsedSeconds += 1;
                        totalTime += 1;
                        timerTextField.setText(CurrentGameTime());

                    } else {

                        //End game
                        _time.cancel();
                        _time.purge();
                        elapsedMinutes = 1;
                        elapsedSeconds = 0;
                        timerTextField.setText(CurrentGameTime());
                        PauseButton.setImage(getImage(imagePlay));
                        GameEnd();
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
        InputStream stream = getClass().getResourceAsStream(image);

        if (stream != null) {
            return new Image(stream);
        } else {
            System.err.println("Image not found: " + image);
            return null;
        }
    }

}





